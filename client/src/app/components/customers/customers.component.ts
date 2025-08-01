import {Component, OnInit} from '@angular/core';
import {CustomersPage} from "../../types/types";
import {CustomersService} from "../../services/customers.service";
import {JsonPipe, NgForOf} from "@angular/common";
import {Router, RouterLink} from "@angular/router";


@Component({
  selector: 'app-customers',
  standalone: true,
    imports: [
        JsonPipe,
        NgForOf,
        RouterLink
    ],
  templateUrl: './customers.component.html',
  styleUrl: './customers.component.css'
})
export class CustomersComponent implements OnInit {
    public customersPage!: CustomersPage;
    public page: number = 0;
    public size: number = 10;


    constructor(
        private readonly customersService: CustomersService,
        private readonly route: Router
    ) {
    }

    ngOnInit(): void {
        this.getPaginatedCustomers()
    }

    private getPaginatedCustomers() {
        this.customersService
            .fetchPaginatedCustomers(
                this.page,
                this.size
            ).subscribe({
            next: ({data, loading}) => {
                this.customersPage = data['paginatedCustomers']
                console.log(this.customersPage)
            },
            error: err => {
                console.log(err)
            }
        })
    }

    public toNextPage() {
        if (this.customersPage.pageInfo.hasNext) {
            this.page += 1;

            // Fetch the next customers
            this.getPaginatedCustomers()
        }
    }

    public toPreviousPage() {
        if (this.customersPage.pageInfo.hasPrevious) {
            this.page -= 1;

            // Fetch the previous customers
            this.getPaginatedCustomers()
        }
    }

    public removeCustomer(id: string) {
        return this.customersService
            .deleteCustomer(id)
            .subscribe({
                next: ({data}) => {
                    this.getPaginatedCustomers()
                    console.log(data)
                },
                error: err => console.log(err)
            })
    }

}
