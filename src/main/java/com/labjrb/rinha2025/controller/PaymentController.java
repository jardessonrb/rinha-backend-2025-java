package com.labjrb.rinha2025.controller;

import com.labjrb.rinha2025.domain.document.PaymentDocument;
import com.labjrb.rinha2025.domain.dto.in.PaymentDTO;
import com.labjrb.rinha2025.domain.dto.out.SummaryDTO;
import com.labjrb.rinha2025.domain.dto.out.SummaryDefaultDTO;
import com.labjrb.rinha2025.domain.dto.out.SummaryFallbackDTO;
import com.labjrb.rinha2025.domain.service.PaymentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping("/payments")
    public ResponseEntity<Void> payment(@RequestBody PaymentDTO payment){
        paymentService.enqueuePayment(payment);
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/payments-summary")
    public ResponseEntity<SummaryDTO> summary(@RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date from,
                                              @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date to){
        SummaryDTO summary = new SummaryDTO(
                new SummaryDefaultDTO(10, 10.0D),
                new SummaryFallbackDTO(10, 15.5)
        );

        return ResponseEntity.ok(paymentService.paymentSummary(from, to));
    }

    @GetMapping("/payments")
    public ResponseEntity<List<PaymentDocument>> payments(@RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime from,
                                                          @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime to){


        return ResponseEntity.ok(this.paymentService.payments(from, to));
    }
}
