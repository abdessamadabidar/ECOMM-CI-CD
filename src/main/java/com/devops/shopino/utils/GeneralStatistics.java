package com.devops.shopino.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeneralStatistics {
    private Long numberOfCustomers;
    private Long numberOfProducts;
    private Long numberOfOrders;
    private Double totalIncome;
}
