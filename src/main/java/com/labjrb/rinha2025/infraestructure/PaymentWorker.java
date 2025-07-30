package com.labjrb.rinha2025.infraestructure;

import com.labjrb.rinha2025.domain.document.PaymentDocument;
import com.labjrb.rinha2025.domain.dto.CompletPaymentDTO;
import com.labjrb.rinha2025.domain.repository.PaymentRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component("PaymentWorker")
public class PaymentWorker {

    private final PaymentQueueFacade queue;
    private final PaymentRepository paymentRepository;

    public PaymentWorker(PaymentQueueFacade paymentQueueFacade, PaymentRepository paymentRepository){
        int numberWorkers = Runtime.getRuntime().availableProcessors();
        this.queue = paymentQueueFacade;
        ExecutorService executorService = Executors.newFixedThreadPool(numberWorkers);
        this.paymentRepository = paymentRepository;

        for (int i = 0; i < numberWorkers; i++) {
            executorService.submit(this::run);
        }
    }

    private void run(){
        while (true){
            try {
                CompletPaymentDTO payment = this.queue.take();
                PaymentDocument paymentDocument = new PaymentDocument("fallback", payment.getCorrelationId(), payment.getAmount(), payment.getReceptAt(), LocalDateTime.now());
                paymentRepository.save(paymentDocument);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
