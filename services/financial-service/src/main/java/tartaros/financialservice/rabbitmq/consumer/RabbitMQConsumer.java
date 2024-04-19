package tartaros.financialservice.rabbitmq.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tartaros.financialservice.db.entity.ActivityTransaction;
import tartaros.financialservice.db.entity.Transaction;
import tartaros.financialservice.db.entity.TransactionWrapper;
import tartaros.financialservice.db.entity.WebshopTransaction;
import tartaros.financialservice.db.service.ActivityTransactionServiceImpl;
import tartaros.financialservice.db.service.TransactionServiceImpl;
import tartaros.financialservice.db.service.WebshopTransactionService;

@Service
public class RabbitMQConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

    @Autowired
    TransactionServiceImpl transactionService;

    @Autowired
    ActivityTransactionServiceImpl activityTransactionService;

    @Autowired
    WebshopTransactionService webshopTransactionService;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void consumeMessage(TransactionWrapper t) {
        transactionService.saveTransaction(t.getTransaction());

        if (t.getTransaction_type().get("type").asText().equals("activity")) {
            ObjectMapper mapper = new JsonMapper();
            try {
                ActivityTransaction activityTransaction = mapper.treeToValue(t.getTransaction_type(), ActivityTransaction.class);
                activityTransaction.setTransaction(t.getTransaction());
                activityTransactionService.saveActivityTransaction(activityTransaction);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        if (t.getTransaction_type().get("type").asText().equals("webshop")) {
            ObjectMapper mapper = new JsonMapper();
            try {
                WebshopTransaction webshopTransaction = mapper.treeToValue(t.getTransaction_type(), WebshopTransaction.class);
                webshopTransaction.setTransaction(t.getTransaction());
                webshopTransactionService.saveWebshopTransaction(webshopTransaction);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Activities");
        for (Transaction trans: transactionService.fetchTransactionList()) {
            System.out.println(trans.getTransactionId());
        }

        System.out.println("Activity transactions");
        for (ActivityTransaction actTrans: activityTransactionService.fetchActivityTransactionList()) {
            System.out.println(actTrans.getTransaction().getTransactionId());
        }
    }

}
