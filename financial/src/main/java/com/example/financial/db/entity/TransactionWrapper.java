package com.example.financial.db.entity;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionWrapper {
    private Transaction transaction;
    private ObjectNode transaction_type;
}
