import {Component, OnInit} from '@angular/core';
import {RouterOutlet} from "@angular/router";
import {StatisticsService} from "../../services/statistics.service";
import {GeneralStatistics} from "../../types/types";
import {CurrencyPipe, DecimalPipe} from "@angular/common";
import {BestSellingProductsComponent} from "../charts/best-selling-products/best-selling-products.component";
import {IncomeVariationComponent} from "../charts/income-variation/income-variation.component";

@Component({
  selector: 'app-admin-template',
  standalone: true,
    imports: [
        RouterOutlet,
        CurrencyPipe,
        DecimalPipe,
        BestSellingProductsComponent,
        IncomeVariationComponent
    ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
    public statistics!: GeneralStatistics;

    constructor(private readonly statisticsService: StatisticsService) {
    }
    ngOnInit(): void {
        this.init()
    }

    private init() {

        return this.statisticsService
            .collectStatistics()
            .subscribe({
                next: ({data}) => {
                    this.statistics = data['generalStatistics'];
                },
                error: err => console.log(err)
            })

    }




}
