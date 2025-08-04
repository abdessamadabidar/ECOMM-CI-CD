package com.devops.shopino.controllers;

import com.devops.shopino.dto.response.ProductResponseDto;
import com.devops.shopino.services.StatisticsService;
import com.devops.shopino.utils.GeneralStatistics;
import com.devops.shopino.utils.MapResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Month;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/graphql")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class StatisticsGraphQLController {

    private final StatisticsService service;

    @SubscriptionMapping
    public Flux<GeneralStatistics> generalStatistics() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(interval -> service.collectStatistics());
    }

    @SubscriptionMapping
    public Flux<List<MapResponse<ProductResponseDto, Double>>> bestSellingProducts(
            @Argument int limit
    ) {
        return Flux.interval(Duration.ofSeconds(1))
                .map(interval -> service.fetchBestSellingProducts(limit));
    }

    @SubscriptionMapping
    public Flux<List<MapResponse<Month, Double>>> incomeVariationOfTheYear() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(interval -> service.fetchIncomeVariationOfTheYear());
    }
}
