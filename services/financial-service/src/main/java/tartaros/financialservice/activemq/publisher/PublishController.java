package tartaros.financialservice.activemq.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tartaros.financialservice.activemq.model.SystemMessage;
import tartaros.financialservice.db.entity.Transaction;
import tartaros.financialservice.db.entity.TransactionWrapper;

@RestController
public class PublishController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @PostMapping("/publishMessage")
    public ResponseEntity<String> publishMessage(@RequestBody TransactionWrapper wrapper) {
        try {
//            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//            String json = ow.writeValueAsString(wrapper);
//            System.out.println(json);
            jmsTemplate.convertAndSend("memberships", wrapper);
            System.out.println("WAHOOOOOOOOOOOOOOOOOOOO");
            return new ResponseEntity<>("Sent", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
