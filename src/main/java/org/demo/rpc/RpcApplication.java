package org.demo.rpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class RpcApplication {

	public static void main(String[] args) {
		SpringApplication.run(RpcApplication.class, args);
	}

}
