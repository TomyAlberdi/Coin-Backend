package com.example.apilist.Event;

import com.example.apilist.Config.RabbitMQConfig;
import com.example.apilist.Repository.ListRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeleteUserEventConsumer {

    private final ListRepository listRepository;
    private final DeleteListEventProducer deleteListEventProducer;
    
    @RabbitListener(queues = RabbitMQConfig.QUEUE_USER_DELETED)
    public void listen(DeleteUserEventConsumer.Data message) {
        System.out.println("List of user with ID: " + message.user_id + " deleted.");
        listRepository.deleteByUser(message.user_id);
        deleteListEventProducer.publishDeleteListEvent(new DeleteListEventProducer.Data(message.user_id));
    }
    
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Data {
        private Long user_id;
    }

}
