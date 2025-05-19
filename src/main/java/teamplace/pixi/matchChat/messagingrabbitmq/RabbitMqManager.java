package teamplace.pixi.matchChat.messagingrabbitmq;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
@RequiredArgsConstructor
public class RabbitMqManager {
    private final AmqpAdmin amqpAdmin;
    private final DirectExchange directExchange;
    private final ConnectionFactory connectionFactory;
    private final ChatMessageReceiver receiver;

    private final Map<String, SimpleMessageListenerContainer> containers = new ConcurrentHashMap<>();

    public void registerUserQueue(String userId) {
        String queueName = "chat.user." + userId;
        System.out.println("Registering queue for userId: " + userId);
        // 1. 큐 생성
        Queue queue = new Queue(queueName, true);
        amqpAdmin.declareQueue(queue);

        // 2. 바인딩 생성
        Binding binding = BindingBuilder.bind(queue).to(directExchange).with(queueName);
        amqpAdmin.declareBinding(binding);

        // 3. 리스너 등록
        if (!containers.containsKey(queueName)) {
            MessageListenerAdapter adapter = new MessageListenerAdapter(receiver, "receiveMessage");
            adapter.setMessageConverter(new Jackson2JsonMessageConverter());

            SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
            container.setConnectionFactory(connectionFactory);
            container.setQueueNames(queueName);
            container.setMessageListener(adapter);
            container.start();

            containers.put(queueName, container);
        }
    }

}
