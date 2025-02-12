package com.system.person_api.consumer;

import com.system.person_api.dto.MessageDTO;
import com.system.person_api.service.ReportCSVService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.io.IOException;

@Service
public class MessageConsumer {

    private final ReportCSVService reportCSVService;

    @Value("${spring.rabbitmq.queue.name}")
    private String queueName;

    public MessageConsumer(ReportCSVService reportCSVService) {
        this.reportCSVService = reportCSVService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.name}", ackMode = "MANUAL")
    public void receiveMessage(MessageDTO message, Channel channel, MessageProperties messageProperties) throws IOException {
        reportCSVService.generateAndSaveCSV(message.getId());
        channel.basicAck(messageProperties.getDeliveryTag(), false);
    }
}