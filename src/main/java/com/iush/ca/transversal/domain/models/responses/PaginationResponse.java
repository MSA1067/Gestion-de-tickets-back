package com.iush.ca.transversal.domain.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaginationResponse(
        @JsonProperty("current_page")
        int currentPage,
        int size,
        @JsonProperty("next_page")
        int nextPage,
        @JsonProperty("last_page")
        int lastPage,
        @JsonProperty("total_elements")
        long totalElements,
        String previousPageUrl,
        String currentPageUrl,
        String nextPageUrl,
        String lastPageUrl) {}

