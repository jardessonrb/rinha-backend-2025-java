package com.labjrb.rinha2025.domain.dto.in;

import java.util.UUID;

public record PaymentDTO(UUID correlationId, Double amount) {}
