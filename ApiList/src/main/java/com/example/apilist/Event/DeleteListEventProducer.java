package com.example.apilist.Event;

import com.example.apilist.Config.RabbitMQConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class DeleteListEventProducer {
    
    private final RabbitTemplate rabbitTemplate;
    
    public DeleteListEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    public void publishDeleteListEvent(DeleteListEventProducer.Data message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,RabbitMQConfig.TOPIC_LIST_DELETED,message);
    }
    
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Data {
        private Long list_id;
    }
    
}
