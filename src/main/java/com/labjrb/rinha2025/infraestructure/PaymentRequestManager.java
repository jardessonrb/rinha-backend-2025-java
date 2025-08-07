package com.labjrb.rinha2025.infraestructure;

import com.labjrb.rinha2025.domain.document.PaymentDocument;
import com.labjrb.rinha2025.domain.dto.CompletPaymentDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class PaymentRequestManager {

    private final PaymentProcessorHttpClient processorDefault;
    private final PaymentProcessorHttpClient processorFallback;

    public PaymentRequestManager(@Qualifier("paymentProcessorDefault") PaymentProcessorHttpClient processorDefault,
                                 @Qualifier("paymentProcessorFallback") PaymentProcessorHttpClient processorFallback){

        this.processorDefault = processorDefault;
        this.processorFallback = processorFallback;
    }


    public String processPayment(CompletPaymentDTO paymentDocument){
        try {
            processorDefault.post("payments", processorDefault.toStringJson(paymentDocument));
            System.out.println("Processado pelo default");
            return "default";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro no default /payments");
        }

        try {
            processorFallback.post("payments", processorDefault.toStringJson(paymentDocument));
            System.out.println("Processado pelo fallback");
            return "fallback";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro no fallback /payments");
        }

        return "error";
    }


}
