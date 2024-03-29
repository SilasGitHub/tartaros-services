package com.example.financial.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.UUID;

@Entity
@Data
@Table(name="Transaction")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Transaction {
    @Id
    @GeneratedValue
    private Long transactionId;
    private Long memberId;
    private Double amount;
    private String description;
    private boolean paid = false;

}

