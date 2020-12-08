package com.dc297.ecommerce.apigateway.bulk;

import com.dc297.ecommerce.apigateway.dtos.OrderDto;
import com.dc297.ecommerce.apigateway.dtos.OrderStatusDto;
import org.apache.kafka.common.protocol.Message;
import org.apache.kafka.common.protocol.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class Sender {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

    @Autowired
    private KafkaTemplate<String, OrderDto> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, OrderStatusDto> kafkaTemplateStatus;

    @Value("${kafka.topic.order}")
    private String topic;

    @Value("${kafka.topic.orderstatus}")
    private String orderStatusTopic;

    public void send(OrderDto order){
        LOGGER.info("Sending message='{}' to topic='{}'", order, topic);
        kafkaTemplate.send(topic, topic, order);
    }

    public void sendOrderStatus(OrderStatusDto orderStatus){
        LOGGER.info("Sending message='{}' to topic='{}'", orderStatus, orderStatusTopic);
        kafkaTemplateStatus.send(orderStatusTopic, orderStatusTopic, orderStatus);
    }

}
