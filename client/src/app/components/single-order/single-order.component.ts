import {Component, OnInit} from '@angular/core';
import {OrdersService} from "../../services/orders.service";
import {ActivatedRoute, RouterLink} from "@angular/router";
import {Order} from "../../types/types";
import {DatePipe, DecimalPipe, NgForOf, NgOptimizedImage} from "@angular/common";

@Component({
  selector: 'app-single-order',
  standalone: true,
    imports: [
        RouterLink,
        DatePipe,
        DecimalPipe,
        NgForOf,
        NgOptimizedImage
    ],
  templateUrl: './single-order.component.html',
  styleUrl: './single-order.component.css'
})
export class SingleOrderComponent implements OnInit {

    public order!: Order;

    constructor(
        private readonly ordersService: OrdersService,
        private readonly route: ActivatedRoute
    ) {
    }

    ngOnInit(): void {
        this.route.params.subscribe(params => {
            this.getSingleOrder(params['id'])
        })
    }

    private getSingleOrder(orderId: string) {
        this.ordersService
            .fetchSingleOrder(orderId)
            .subscribe({
                next: ({data, loading}) => {
                    this.order = data['orderById']
                    console.log(this.order);
                },
                error: err => console.log(err)
            })
    }


    protected readonly print = print;
}
