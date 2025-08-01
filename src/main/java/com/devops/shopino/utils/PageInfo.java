package com.devops.shopino.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageInfo {
    private int totalPages;
    private long totalElements;
    private int size;
    private int currentPage;
    private boolean hasPrevious;
    private boolean hasNext;
}
