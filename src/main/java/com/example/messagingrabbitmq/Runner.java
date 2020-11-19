package com.example.messagingrabbitmq;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

	private final RabbitTemplate rabbitTemplate;
	private final Receiver receiver;

	public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
		this.receiver = receiver;
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.println("Type message");
			String message = scanner.nextLine();
			System.out.println("Sending...");
			rabbitTemplate.convertAndSend(MessagingRabbitmqApplication.exchangeName, "#", message);
			System.out.println("OK");
			receiver.getLatch().await(1000, TimeUnit.MILLISECONDS);
		}
	}

}
