package com.labjrb.rinha2025.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class CompletPaymentDTO {

    private UUID correlationId;
    private Double amount;
    private LocalDateTime receptAt;

    public CompletPaymentDTO(UUID correlationId, Double amount, LocalDateTime receptAt) {
        this.correlationId = correlationId;
        this.amount = amount;
        this.receptAt = receptAt;
    }

    public UUID getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(UUID correlationId) {
        this.correlationId = correlationId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getReceptAt() {
        return receptAt;
    }

    public void setReceptAt(LocalDateTime receptAt) {
        this.receptAt = receptAt;
    }

}
