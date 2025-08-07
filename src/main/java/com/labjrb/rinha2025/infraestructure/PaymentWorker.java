package com.labjrb.rinha2025.infraestructure;

import com.labjrb.rinha2025.domain.document.PaymentDocument;
import com.labjrb.rinha2025.domain.dto.CompletPaymentDTO;
import com.labjrb.rinha2025.domain.repository.PaymentRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

@Component("PaymentWorker")
public class PaymentWorker {

    private final PaymentQueueFacade queue;
    private final PaymentRepository paymentRepository;
    private final PaymentRequestManager paymentRequestManager;
    private final AtomicBoolean isDefault = new AtomicBoolean(true);
    private final AtomicLong isNextTimePermitedRequest = new AtomicLong(System.currentTimeMillis());

    public PaymentWorker(PaymentQueueFacade paymentQueueFacade, PaymentRepository paymentRepository, PaymentRequestManager paymentRequestManager){
        int numberWorkers = Runtime.getRuntime().availableProcessors();
        this.queue = paymentQueueFacade;
        this.paymentRepository = paymentRepository;
        this.paymentRequestManager = paymentRequestManager;
        ExecutorService executorService = Executors.newFixedThreadPool(numberWorkers);

        for (int i = 0; i < numberWorkers; i++) {
            executorService.submit(this::run);
        }
    }

    private void run(){
        while (true){
            try {
                CompletPaymentDTO payment = this.queue.take();
                System.out.println("Processando pagamento v2");
                String retorno = this.paymentRequestManager.processPayment(payment);
                if(!retorno.equals("error")){
                    PaymentDocument paymentDocument = new PaymentDocument(retorno, payment.getCorrelationId(), payment.getAmount(), payment.getReceptAt(), LocalDateTime.now());
                    paymentRepository.save(paymentDocument);
                }
                System.out.println("Pagamento processado");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
