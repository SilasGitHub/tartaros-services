package tartaros.financialservice.activemq.consumer;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import tartaros.financialservice.activemq.model.SystemMessage;
import tartaros.financialservice.db.entity.ActivityTransaction;
import tartaros.financialservice.db.entity.Transaction;
import tartaros.financialservice.db.entity.TransactionWrapper;
import tartaros.financialservice.db.entity.WebshopTransaction;

import java.io.IOException;

@Component
public class MessageConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

    @JmsListener(destination="${queue.memberships}")
    public void messageListener(TransactionWrapper transaction) {
        System.out.println(transaction.getTransaction().getAmount());
        LOGGER.info("Message received, {}", transaction.getTransaction().getMemberId());
        System.out.println(transaction.getTransaction_type());
        System.out.println("Wahoo");
//        ObjectMapper objectMapper = new JsonMapper();
//        JsonFactory factory = objectMapper.getJsonFactory(); // since 2.1 use mapper.getFactory() instead
//        JsonParser jp = null;
//        try {
//            jp = factory.createJsonParser("{\"k1\":\"v1\"}");
//            JsonNode actualObj = objectMapper.readTree(jp);
//            TransactionWrapper wrapper = objectMapper.treeToValue(actualObj, TransactionWrapper.class);
//            System.out.println(wrapper.getTransaction_type());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        LOGGER.info("Message received, {}", systemMessage);

    }
}
