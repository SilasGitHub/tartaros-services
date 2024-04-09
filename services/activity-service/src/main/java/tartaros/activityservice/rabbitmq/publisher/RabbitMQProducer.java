package tartaros.activityservice.rabbitmq.publisher;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tartaros.activityservice.transaction.TransactionWrapper;


@Service
public class RabbitMQProducer {

    Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);
    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendTransaction(TransactionWrapper t) {
        LOGGER.info(String.format("Message sent -> %s", t.toString()));
        rabbitTemplate.convertAndSend(exchange, routingKey, t);
    }
}
