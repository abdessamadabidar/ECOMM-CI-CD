import {Component, OnInit} from '@angular/core';
import {
    BarController,
    BarElement,
    CategoryScale,
    Chart,
    ChartConfiguration,
    Legend,
    LinearScale,
    Tooltip
} from "chart.js";
import {BestSellingProductsMap} from "../../../types/types";
import {StatisticsService} from "../../../services/statistics.service";

@Component({
  selector: 'app-best-selling-products',
  standalone: true,
  imports: [],
  templateUrl: './best-selling-products.component.html',
  styleUrl: './best-selling-products.component.css'
})
export class BestSellingProductsComponent implements OnInit{
    public chart!: any;
    public bestSellingProductsStatistics!: BestSellingProductsMap[];
    public topLimit: number = 5
    private labels!: string[];
    private inputs!: number[];

    constructor(private readonly statisticsService: StatisticsService) {
    }
    ngOnInit(): void {
        this.init()

    }

    private init() {
        this.statisticsService
            .fetchBestSellingProducts(this.topLimit)
            .subscribe({
                next: ({data}) => {
                    this.bestSellingProductsStatistics = data['bestSellingProducts'];
                    this.labels = this.bestSellingProductsStatistics.map(map => map.key.name)

                    this.inputs = this.bestSellingProductsStatistics
                        .map(elem => Math.round(elem.value))

                    if (!this.chart) {
                        this.buildChart();
                    }
                    else this.updateChart();


                },
                error: err => console.log(err)
            })
    }


    private buildChart() : void {
        /*
         * Generate the bar chart
         */

        // Register necessary Chart.js components
        Chart.register(LinearScale, CategoryScale, BarController, BarElement, Tooltip, Legend);

        const chartConfig: ChartConfiguration<'bar'> = {
            type: 'bar',
            data: {
                labels: this.labels,
                datasets: [{
                    label: 'Best selling products',
                    data: this.inputs,
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(255, 159, 64, 0.2)',
                        'rgba(255, 205, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(153, 102, 255, 0.2)',
                        'rgba(201, 203, 207, 0.2)'
                    ],
                    borderColor: [
                        'rgb(255, 99, 132)',
                        'rgb(255, 159, 64)',
                        'rgb(255, 205, 86)',
                        'rgb(75, 192, 192)',
                        'rgb(54, 162, 235)',
                        'rgb(153, 102, 255)',
                        'rgb(201, 203, 207)'
                    ],
                    borderWidth: 2,
                    borderRadius: 5
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    },
                    x: {
                        ticks: {
                            maxRotation: 0,
                            minRotation: 0
                        }
                    }
                }
            }
        };

        this.chart = new Chart('canvas', chartConfig);
    }

    private updateChart() {
        this.chart.data.labels = this.labels;
        this.chart.data.datasets[0].data = this.inputs;
    }
}
