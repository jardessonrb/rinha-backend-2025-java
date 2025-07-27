package com.labjrb.rinha2025.infraestructure;

import com.labjrb.rinha2025.domain.dto.CompletPaymentDTO;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component("PaymentWorker")
public class PaymentWorker {

    private final PaymentQueueFacade queue;
    private final ExecutorService executorService;

    public PaymentWorker(PaymentQueueFacade paymentQueueFacade){
        int numberWorkers = Runtime.getRuntime().availableProcessors();
        this.queue = paymentQueueFacade;
        this.executorService = Executors.newFixedThreadPool(numberWorkers);

        for (int i = 0; i < numberWorkers; i++) {
            this.executorService.submit(this::run);
        }
    }

    private void run(){
        while (true){
            try {
                CompletPaymentDTO payment = this.queue.take();
                System.out.println("procesando pagamento");
                Thread.sleep(3000);
                System.out.println("pagamento finalizado");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
