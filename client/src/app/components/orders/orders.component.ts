import {Component, OnInit} from '@angular/core';
import {OrdersService} from "../../services/orders.service";
import {CustomersPage, OrdersPage} from "../../types/types";
import {DatePipe, DecimalPipe, formatNumber, NgForOf} from "@angular/common";
import {Router, RouterLink} from "@angular/router";

@Component({
  selector: 'app-orders',
  standalone: true,
    imports: [
        NgForOf,
        DatePipe,
        DecimalPipe,
        RouterLink
    ],
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.css'
})
export class OrdersComponent implements OnInit {
    public ordersPage!: OrdersPage;
    public page: number = 0;
    public size: number = 10;

    constructor(
        private readonly ordersService: OrdersService,
        private readonly router: Router
    ) {
    }

    ngOnInit(): void {
        this.getPaginatedOrders()
    }

    private getPaginatedOrders() {
        this.ordersService
            .fetchPaginatedOrders(
                this.page,
                this.size
            ).subscribe({
            next: ({data, loading}) => {
                this.ordersPage = data['paginatedOrders']
                console.log(this.ordersPage)
            },
            error: err => {
                console.log(err)
            }
        })
    }

    public toNextPage() {
        if (this.ordersPage.pageInfo.hasNext) {
            this.page += 1;

            // Fetch the next orders
            this.getPaginatedOrders()
        }
    }

    public toPreviousPage() {
        if (this.ordersPage.pageInfo.hasPrevious) {
            this.page -= 1;

            // Fetch the previous orders
            this.getPaginatedOrders()
        }
    }


    public viewSingleOrder(orderId: string) {
        this.router.navigate([`/admin/orders/${orderId}`])
    }

    protected readonly formatNumber = formatNumber;
    protected readonly navigator = navigator;
}
