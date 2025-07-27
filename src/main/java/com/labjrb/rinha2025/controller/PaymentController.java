package com.labjrb.rinha2025.controller;

import com.labjrb.rinha2025.domain.dto.in.PaymentDTO;
import com.labjrb.rinha2025.domain.dto.out.SummaryDTO;
import com.labjrb.rinha2025.domain.dto.out.SummaryDefaultDTO;
import com.labjrb.rinha2025.domain.dto.out.SummaryFallbackDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class PaymentController {

    @PostMapping("/payments")
    public ResponseEntity<Void> payment(@RequestBody PaymentDTO payment){

        return ResponseEntity.status(200).build();
    }

    @GetMapping("/payments-summary")
    public ResponseEntity<SummaryDTO> summary(@RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime from,
                                              @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime to){
        SummaryDTO summary = new SummaryDTO(
                new SummaryDefaultDTO(10, 10.0D),
                new SummaryFallbackDTO(10, 15.5)
        );

        return ResponseEntity.ok(summary);
    }
}
