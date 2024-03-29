package tartaros.financialservice.db.entity;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter

public class TransactionWrapper implements Serializable {
    private Transaction transaction;
    private ObjectNode transaction_type;
}
