package org.demo.rpc.Controller;

import org.demo.rpc.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
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
    Queue queue;

    Scanner sc = new Scanner(System.in);

    ArrayList<Order> orders = new ArrayList<>();

    @PostMapping("/new")
    public String newOrder(
           @RequestBody Order order
    ) {
        try {
            order.setCode(Integer.valueOf(orders.size()+1).toString());
            orders.add(order);
            jmsMessagingTemplate.convertAndSend(this.queue, order.getCode());
            return "创建成功";
        }catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("all")
    public ArrayList<Order> getAll() {
        return orders;
    }


    //queue模式的消费者
    @JmsListener(destination="${spring.activemq.queue-name}", containerFactory="queueListener")
    public void readActiveQueue(String message) {
        int integer = Integer.parseInt(message);
        Order order = orders.get(integer-1);
        System.out.println("新订单" + order.toString());
        System.out.println(message);
        System.out.println("请选择，1，发货\n\t其它.缺货");
        int i = sc.nextInt();
        if (i==1) {
            order.setStatus("发货");
        }else {
            order.setStatus("缺货");
        }
        System.out.println("处理完成");

    }


}
