package org.demo.rpc.Controller;

import org.demo.rpc.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Queue;
import java.util.ArrayList;
import java.util.Scanner;

@RestController
public class OrderController {

    @Autowired
    JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    @Qualifier("queue1")
    Queue queue1;

    @Autowired
    @Qualifier("queue2")
    Queue queue2;

    Scanner sc = new Scanner(System.in);

    ArrayList<Order> orders = new ArrayList<>();

    @PostMapping("/new")
    public String newOrder(
            @RequestBody Order order) {
        try {
            order.setCode(Integer.valueOf(orders.size() + 1).toString());
            orders.add(order);
            jmsMessagingTemplate.convertAndSend(this.queue1, order.getCode());
            return "创建成功";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @GetMapping("all")
    public ArrayList<Order> getAll() {
        return orders;
    }

    // queue模式的消费者
    @JmsListener(destination = "${spring.activemq.queue-name}", containerFactory = "queueListener")
    public void readActiveQueue(String message) {
        int integer = Integer.parseInt(message);
        Order order = orders.get(integer - 1);
        System.out.println("新订单" + order.toString());
        System.out.println(message);
        System.out.println("请选择，1，发货\n\t其它.缺货");
        int i = sc.nextInt();
        jmsMessagingTemplate.convertAndSend(queue2, integer * 10 + i);
    }

    @JmsListener(destination = "active.orderreturn", containerFactory = "queueListener")
    protected void getProcess(String message) {
        int integer = Integer.parseInt(message);
        Order order = orders.get(integer / 10 - 1);
        int i = integer % 10;
        if (i == 1) {
            order.setStatus("发货");
        } else {
            order.setStatus("缺货");
        }
        System.out.println("处理完成");
    }

}
