package com.labjrb.rinha2025.infraestructure;

import com.labjrb.rinha2025.domain.dto.out.SummaryDTO;
import com.labjrb.rinha2025.domain.dto.out.SummaryDefaultDTO;
import com.labjrb.rinha2025.domain.dto.out.SummaryFallbackDTO;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

record SummaryModel(String id, Integer totalRequests, Double totalAmount) {}

@Component
public class PaymentTemplateMongoRepository {

    private final MongoTemplate mongoTemplate;

    public PaymentTemplateMongoRepository(MongoTemplate  mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    public SummaryDTO paymentSummary(Date from, Date to){
        var aggregateMatch = Aggregation
                .match(Criteria.where("processedAt").gte(from).lte(to));

        var aggregateGroup = Aggregation
                .group("processor")
                .sum("amount").as("totalAmount")
                .count().as("totalRequests");

        var aggregateComplet = Aggregation.newAggregation(aggregateMatch, aggregateGroup);

        AggregationResults<SummaryModel> payments = mongoTemplate.aggregate(aggregateComplet, "payments", SummaryModel.class);
        Map<String, SummaryModel> collectMap      = payments.getMappedResults().stream().collect(Collectors.toMap(SummaryModel::id, Function.identity()));
        return new SummaryDTO(
                collectMap.containsKey("default") ? new SummaryDefaultDTO(collectMap.get("default").totalRequests(), collectMap.get("default").totalAmount()) : null,
                collectMap.containsKey("fallback") ? new SummaryFallbackDTO(collectMap.get("fallback").totalRequests(), collectMap.get("fallback").totalAmount()) : null
        );
    }
}
