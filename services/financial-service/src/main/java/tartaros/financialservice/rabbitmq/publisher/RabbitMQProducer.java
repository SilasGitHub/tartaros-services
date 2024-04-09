package tartaros.financialservice.rabbitmq.publisher;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tartaros.financialservice.db.entity.Transaction;


@Service
public class RabbitMQProducer {

    Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);
    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
//        this.rabbitTemplate = rabbitTemplate;
//    }

    public void sendMessage(Transaction t) {
        LOGGER.info(String.format("Message sent -> %s", t.toString()));
        System.out.println(exchange);
        System.out.println(routingKey);
        rabbitTemplate.convertAndSend(exchange, routingKey, t);
    }
}
