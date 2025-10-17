package com.iush.ca.transversal.infrastructure.adapters;

import com.iush.ca.transversal.domain.models.responses.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginationUtils {
    public static Pageable buildPageable(int page, int size,String sortField) {
        return PageRequest.of(
                Math.max(page - 1, 0),
                size,
                Sort.by(Sort.Order.asc(sortField)));
    }

    public static <T> PaginationResponse buildPaginationResponse(Page<T> page, int currentPage, int size, String url) {
        int totalPages = page.getTotalPages();
        int safeCurrentPage = Math.max(currentPage, 0);
        int previousPage = safeCurrentPage > 0 ? safeCurrentPage - 1 : 0;
        int nextPage = safeCurrentPage < totalPages - 1 ? safeCurrentPage + 1 : totalPages - 1;
        String urlPattern = "%s?page=%d&size=%d";
        String previousUrl = String.format(urlPattern, url, previousPage, size);
        String currentUrl = String.format(urlPattern, url, safeCurrentPage, size);
        String nextPageUrl = String.format(urlPattern, url, nextPage, size);
        String lastPageUrl = String.format(urlPattern, url, totalPages - 1, size);
        return new PaginationResponse(
                safeCurrentPage,
                size,
                nextPage,
                totalPages - 1,
                page.getTotalElements(),
                previousUrl,
                currentUrl,
                nextPageUrl,
                lastPageUrl
        );
    }
}
