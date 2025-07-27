package com.labjrb.rinha2025.domain.service;

import com.labjrb.rinha2025.domain.dto.CompletPaymentDTO;
import com.labjrb.rinha2025.domain.dto.in.PaymentDTO;
import com.labjrb.rinha2025.infraestructure.PaymentQueueFacade;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private PaymentQueueFacade queue;

    public PaymentService(PaymentQueueFacade queueFacade){
        this.queue = queueFacade;
    }

    public void enqueuePayment(PaymentDTO paymentDTO){
        try {
            this.queue.add(new CompletPaymentDTO(paymentDTO.correlationId(), paymentDTO.amount(), LocalDateTime.now()));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
