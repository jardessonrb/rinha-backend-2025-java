package com.labjrb.rinha2025.config;

import com.labjrb.rinha2025.infraestructure.PaymentProcessorHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class HttpClientConfig {

    @Value("${payment.default.url}")
    private String baseUrlDefault;

    @Value("${payment.fallback.url}")
    private String baseUrlFallback;

    @Primary
    @Bean("paymentProcessorDefault")
    public PaymentProcessorHttpClient paymentProcessorDefault(){
        return new PaymentProcessorHttpClient(baseUrlDefault);
    }

    @Bean("paymentProcessorFallback")
    public PaymentProcessorHttpClient paymentProcessorFallback(){
        return new PaymentProcessorHttpClient(baseUrlFallback);
    }
}
