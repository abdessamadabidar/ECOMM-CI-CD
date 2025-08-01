import {Component, OnInit} from '@angular/core';
import {
    BarController,
    BarElement,
    CategoryScale,
    Chart,
    ChartConfiguration, Legend,
    LinearScale,
    LineController, LineElement, PointElement, Tooltip
} from "chart.js";
import {StatisticsService} from "../../../services/statistics.service";
import {MonthIncome} from "../../../types/types";

@Component({
  selector: 'app-income-variation',
  standalone: true,
  imports: [],
  templateUrl: './income-variation.component.html',
  styleUrl: './income-variation.component.css'
})
export class IncomeVariationComponent implements OnInit {
    public chart!: any;
    private labels: string[] = [];
    private inputs: number[] = []


    public incomeVariationOfTheYear!: MonthIncome[];
    constructor(private readonly statisticsService: StatisticsService) {
    }
    ngOnInit(): void {

        this.init();


    }



    private init() {

        this.statisticsService
            .queryIncomeVariationOfTheYear()
            .subscribe({
                next: ({data}) => {
                    this.incomeVariationOfTheYear = data['incomeVariationOfTheYear']
                    this.labels = this.incomeVariationOfTheYear.map(income => income.key.toLowerCase())
                    this.inputs = this.incomeVariationOfTheYear.map(income => Math.floor(income.value))
                    if (!this.chart) {
                        this.buildChart();
                    }
                    else this.updateChart();
                },
                error: err => console.log(err)
            })


    }


    private buildChart() {
        // Register necessary Chart.js components
        Chart.register(LinearScale, CategoryScale, LineController, LineElement, PointElement, Tooltip, Legend);


        const chartConfig: ChartConfiguration<'line'> = {
            type: 'line',
            data: {
                labels: this.labels,
                datasets: [{
                    label: 'Income variation of the current year',
                    data: this.inputs,
                    fill: true,
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgb(75, 192, 192)',
                    tension: 0.1,
                    pointStyle: 'circle',
                    pointRadius: 10,
                    pointHoverRadius: 15,

                }]
            },

        }
        this.chart = new Chart('canvas2', chartConfig);
    }


    private updateChart() {
        this.chart.data.labels = this.labels;
        this.chart.data.datasets[0].data = this.inputs;
    }
}
