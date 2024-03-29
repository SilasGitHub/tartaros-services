package tartaros.financialservice.activemq.consumer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import tartaros.financialservice.db.entity.ActivityTransaction;
import tartaros.financialservice.db.entity.TransactionWrapper;
import tartaros.financialservice.db.entity.WebshopTransaction;
import tartaros.financialservice.db.repository.ActivityTransactionRepository;
import tartaros.financialservice.db.repository.TransactionRepository;
import tartaros.financialservice.db.repository.WebshopTransactionRepository;

import java.io.IOException;

@Component
public class MessageConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

    @Autowired private ActivityTransactionRepository activityTransactionRepository;
    @Autowired private TransactionRepository transactionRepository;
    @Autowired private WebshopTransactionRepository webshopTransactionRepository;

    @JmsListener(destination="${queue.transactions}")
    public void messageListener(String content) {
        LOGGER.info("Message received, {}", content);

        TransactionWrapper wrapper = null;
        ObjectMapper mapper = new JsonMapper();
        try {
            JsonNode rootNode = mapper.readTree(content);
            wrapper = mapper.treeToValue(rootNode, TransactionWrapper.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (wrapper != null) {
            transactionRepository.save(wrapper.getTransaction());
            if (wrapper.getTransaction_type().get("type").asText().equals("webshop")) {
                WebshopTransaction w = null;
                try {
                    w = mapper.treeToValue(wrapper.getTransaction_type(), WebshopTransaction.class);
                    webshopTransactionRepository.save(w);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                LOGGER.info("Webshop transaction received: {}", w);
            } else if (wrapper.getTransaction_type().get("type").asText().equals("activity")) {
                ActivityTransaction a = null;
                try {
                    a = mapper.treeToValue(wrapper.getTransaction_type(), ActivityTransaction.class);
                    activityTransactionRepository.save(a);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                LOGGER.info("Activity transaction received: {}", a);
            }
        }

    }
}
