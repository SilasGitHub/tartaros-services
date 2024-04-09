package tartaros.financialservice.rabbitmq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import tartaros.financialservice.db.entity.TransactionWrapper;

@Service
public class RabbitMQConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void consumeMessage(TransactionWrapper t) {
        LOGGER.info(String.format("Received message -> %s", t.getTransaction().getAmount()));
    }

}
