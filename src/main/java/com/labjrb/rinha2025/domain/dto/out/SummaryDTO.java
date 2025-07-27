package com.labjrb.rinha2025.domain.dto.out;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SummaryDTO(
        @JsonProperty("default")
        SummaryDefaultDTO defaultDTO,
        SummaryFallbackDTO fallback
) { }
