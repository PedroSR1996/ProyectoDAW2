package com.cibertec.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	public static final String QUEUE_NAME = "reserva_queue";
	public static final String EXCHANGE_NAME = "reserva_exchange";
	public static final String ROUTING_KEY = "reserva_routing_key";

	@Bean
	public Queue reservaQueue() {
		return new Queue(QUEUE_NAME, true);
	}

	@Bean
	public DirectExchange reservaExchange() {
		return new DirectExchange(EXCHANGE_NAME);
	}

	@Bean
	public Binding reservaBinding(Queue reservaQueue, DirectExchange reservaExchange) {
		return BindingBuilder.bind(reservaQueue).to(reservaExchange).with(ROUTING_KEY);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}
}