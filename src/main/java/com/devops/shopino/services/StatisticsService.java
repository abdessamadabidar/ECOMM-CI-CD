package com.devops.shopino.services;

import com.devops.shopino.dto.response.ProductResponseDto;
import com.devops.shopino.services.interfaces.ICustomerService;
import com.devops.shopino.services.interfaces.IOrderLineService;
import com.devops.shopino.services.interfaces.IOrderService;
import com.devops.shopino.services.interfaces.IProductService;
import com.devops.shopino.utils.GeneralStatistics;
import com.devops.shopino.utils.MapResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ICustomerService customerService;
    private final IProductService productService;
    private final IOrderService orderService;
    private final IOrderLineService orderLineService;

    public GeneralStatistics collectStatistics() {
        // TODO - perform other calculations to get statistics

        return GeneralStatistics
                .builder()
                .numberOfCustomers(customerService.countCustomers())
                .numberOfProducts(productService.countProducts())
                .numberOfOrders(orderService.countOrders())
                .totalIncome(orderService.computeTotalIncome())
                .build();
    }


    public List<MapResponse<ProductResponseDto, Double>> fetchBestSellingProducts(int limit) {

        return  orderLineService.getBestSellingProducts(limit)
                .entrySet()
                .stream().map(entry ->
                        MapResponse.<ProductResponseDto, Double>builder()
                                .key(entry.getKey())
                                .value(entry.getValue())
                                .build()
                )
                .sorted((o1, o2) -> Double.compare(o2.getValue(), o1.getValue()))
                .toList();
    }


    public List<MapResponse<Month, Double>> fetchIncomeVariationOfTheYear() {

        Map<Integer, Double> map = orderService.incomeVariationOfTheYear();
        List<MapResponse<Month, Double>> incomeVariation = new ArrayList<>();

        for (int i = 1; i < LocalDate.now().getMonthValue() + 1; ++i) {
            if (!map.containsKey(i)) {
                map.put(i, 0d);
            }

            incomeVariation
                    .add(
                            MapResponse.<Month, Double>builder()
                                    .key(Month.of(i))
                                    .value(map.get(i))
                                    .build()
                    );
        }

        return incomeVariation;
    }
}
