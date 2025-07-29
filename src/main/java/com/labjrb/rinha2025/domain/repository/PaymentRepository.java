package com.labjrb.rinha2025.domain.repository;

import com.labjrb.rinha2025.domain.document.PaymentDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends MongoRepository<PaymentDocument, String> {
}
