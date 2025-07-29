package com.labjrb.rinha2025.domain.service;

import com.labjrb.rinha2025.domain.document.PaymentDocument;
import com.labjrb.rinha2025.domain.dto.CompletPaymentDTO;
import com.labjrb.rinha2025.domain.dto.in.PaymentDTO;
import com.labjrb.rinha2025.domain.dto.out.SummaryDTO;
import com.labjrb.rinha2025.domain.repository.PaymentRepository;
import com.labjrb.rinha2025.infraestructure.PaymentQueueFacade;
import com.labjrb.rinha2025.infraestructure.PaymentTemplateMongoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class PaymentService {

    private PaymentQueueFacade queue;
    private PaymentRepository paymentRepository;
    private PaymentTemplateMongoRepository paymentTemplateMongoRepository;

    public PaymentService(PaymentQueueFacade queueFacade, PaymentRepository paymentRepository, PaymentTemplateMongoRepository paymentTemplateMongoRepository){
        this.queue = queueFacade;
        this.paymentRepository = paymentRepository;
        this.paymentTemplateMongoRepository = paymentTemplateMongoRepository;
    }

    public void enqueuePayment(PaymentDTO paymentDTO){
        try {
            this.queue.add(new CompletPaymentDTO(paymentDTO.correlationId(), paymentDTO.amount(), LocalDateTime.now()));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public SummaryDTO paymentSummary(Date from, Date to){
        return this.paymentTemplateMongoRepository.paymentSummary(from, to);
    }

    public List<PaymentDocument> payments(LocalDateTime from, LocalDateTime to){
        return this.paymentRepository.findAll();
    }
}
