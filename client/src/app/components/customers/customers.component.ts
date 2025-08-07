import {Component, OnInit} from '@angular/core';
import {CustomersPage} from "../../types/types";
import {CustomersService} from "../../services/customers.service";
import {JsonPipe, NgForOf} from "@angular/common";
import {Router, RouterLink} from "@angular/router";

const backend_host = process.env["BACKEND_HOST"] || 'ecommbacksvc'
const backend_port = process.env["BACKEND_PORT"] || '8080'
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
    public customersPage: CustomersPage | null = null; // Initialize as null
    public page: number = 0;
    public size: number = 10;
    public isLoading: boolean = false; // Add loading state



    constructor(
        private readonly customersService: CustomersService,
        private readonly route: Router
    ) {
    }

    ngOnInit(): void {
        this.getPaginatedCustomers()
        console.log("====================== DEBUG ===================")
        console.log('Backend Host:', process.env["BACKEND_HOST"]);
        console.log('Backend Port:', process.env["BACKEND_PORT"]);
    }

    private getPaginatedCustomers() {
        this.isLoading = true;

        this.customersService
            .fetchPaginatedCustomers(this.page, this.size)
            .subscribe({
                next: (result) => {
                    console.log('Full result:', result); // Debug the entire result

                    if (result.loading) {
                        console.log('Still loading...');
                        return;
                    }

                    if (result.data) {
                        console.log('Raw data:', result.data);
                        console.log('Paginated customers:', result.data.paginatedCustomers);

                        this.customersPage = result.data.paginatedCustomers;
                        this.isLoading = false;
                    } else {
                        console.log('No data in result');
                    }

                    if (result.errors) {
                        console.error('GraphQL errors:', result.errors);
                    }
                },
                error: err => {
                    this.isLoading = false;
                    console.error('Subscription error:', err);

                    // Check for specific error types
                    if (err.networkError) {
                        console.error('Network error:', err.networkError);
                    }
                    if (err.graphQLErrors) {
                        console.error('GraphQL errors:', err.graphQLErrors);
                    }
                }
            });
    }


    public toNextPage() {
        if (!(this.customersPage) || this.customersPage.pageInfo.hasNext) {
            this.page += 1;

            // Fetch the next customers
            this.getPaginatedCustomers()
        }
    }

    public toPreviousPage() {
        if (!(this.customersPage) || this.customersPage.pageInfo.hasPrevious) {
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
