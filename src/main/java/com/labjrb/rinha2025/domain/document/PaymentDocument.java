package com.labjrb.rinha2025.domain.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "payments")
public class PaymentDocument {

    @Id
    private String id;
    private String processor;
    private UUID correlationId;
    private Double amount;
    private LocalDateTime receivedAt;
    private LocalDateTime processedAt;

    public PaymentDocument(String id, UUID correlationId, Double amount, LocalDateTime receivedAt) {
        this.id = id;
        this.correlationId = correlationId;
        this.amount = amount;
        this.receivedAt = receivedAt;
    }

    public PaymentDocument(String id, String processor, UUID correlationId, Double amount, LocalDateTime receivedAt, LocalDateTime processedAt) {
        this.id = id;
        this.processor = processor;
        this.correlationId = correlationId;
        this.amount = amount;
        this.receivedAt = receivedAt;
        this.processedAt = processedAt;
    }

    public PaymentDocument(String processor, UUID correlationId, Double amount, LocalDateTime receivedAt, LocalDateTime processedAt) {
        this.processor = processor;
        this.correlationId = correlationId;
        this.amount = amount;
        this.receivedAt = receivedAt;
        this.processedAt = processedAt;
    }

    public void completed(String processor, LocalDateTime processedAt){
        this.processedAt = processedAt;
        this.processor = processor;
    }

    public PaymentDocument(){}

    public String getId() {
        return id;
    }

    public String getProcessor() {
        return processor;
    }

    public UUID getCorrelationId() {
        return correlationId;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }
}
