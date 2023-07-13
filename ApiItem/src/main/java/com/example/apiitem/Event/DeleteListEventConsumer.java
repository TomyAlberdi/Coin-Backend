package com.example.apiitem.Event;

import com.example.apiitem.Config.RabbitMQConfig;
import com.example.apiitem.Repository.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeleteListEventConsumer {
    
    private final ItemRepository itemRepository;
    
    @RabbitListener(queues = RabbitMQConfig.QUEUE_LIST_DELETED)
    public void listen(DeleteListEventConsumer.Data message) {
        System.out.println("Item of list with ID: " + message.list_id + " deleted.");
        itemRepository.deleteByUser(message.list_id);
    }
    
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Data {
        private Long list_id;
    }
    
}
