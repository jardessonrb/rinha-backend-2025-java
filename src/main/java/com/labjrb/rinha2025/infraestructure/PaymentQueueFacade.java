package com.labjrb.rinha2025.infraestructure;

import com.labjrb.rinha2025.domain.dto.CompletPaymentDTO;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component("PaymentQueueFacade")
public class PaymentQueueFacade {

    private final BlockingQueue<CompletPaymentDTO> payments = new LinkedBlockingQueue<>();

    public void add(CompletPaymentDTO payment) throws InterruptedException {
        this.payments.add(payment);
    }

    public CompletPaymentDTO take() throws InterruptedException {
        return this.payments.take();
    }


}
