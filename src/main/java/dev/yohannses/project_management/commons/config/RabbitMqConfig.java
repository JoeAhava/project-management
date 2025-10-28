package dev.yohannses.project_management.commons.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static dev.yohannses.project_management.commons.Constants.PROPOSAL_Q;

@Configuration
public class RabbitMqConfig {

    @Bean
    Queue proposalsQueue() {
        return QueueBuilder.durable(PROPOSAL_Q).build();
    }

    @Bean
    Exchange proposalsExchange() {
        return ExchangeBuilder.directExchange(PROPOSAL_Q).build();
    }

    @Bean
    Binding proposalsBinding() {
        return BindingBuilder.bind(proposalsQueue()).to(proposalsExchange()).with(PROPOSAL_Q).noargs();
    }

}
