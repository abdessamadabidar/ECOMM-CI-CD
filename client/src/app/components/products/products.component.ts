import {Component, OnInit} from '@angular/core';
import {ProductsService} from "../../services/products.service";
import {ProductsPage} from "../../types/types";
import {JsonPipe, NgForOf} from "@angular/common";
import {Pagination} from "../../utils/Pagination";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-products',
  standalone: true,
    imports: [
        NgForOf,
        JsonPipe,
        RouterLink
    ],
  templateUrl: './products.component.html',
  styleUrl: './products.component.css'
})
export class ProductsComponent implements OnInit {
    public productsPage!: ProductsPage;
    public page: number = 0;
    public size: number = 10;
    public pagination!: Pagination;
    public firstCasePageIndex: number = 1;
    public secondCasePageIndex: number = 2;
    public thirdCasePageIndex: number = 3;

    constructor(private readonly productsService: ProductsService) {

    }

    ngOnInit(): void {
        this.getPaginatedProducts()
    }

    private getPaginatedProducts() {
        this.productsService
            .fetchPaginatedProducts(
                this.page,
                this.size
            ).subscribe({
            next: ({data, loading}) => {
                this.productsPage = data['paginatedProducts']
                this.pagination = new Pagination(this.productsPage?.pageInfo?.totalPages || 0)

                this.firstCasePageIndex = this.pagination?.getCurrentLine(this.productsPage!.pageInfo!.currentPage)! * 3 - 2;
                this.secondCasePageIndex = this.pagination?.getCurrentLine(this.productsPage!.pageInfo!.currentPage)! * 3 - 1;
                this.thirdCasePageIndex = this.pagination?.getCurrentLine(this.productsPage!.pageInfo!.currentPage)! * 3;
            },
            error: err => {
                console.log(err)
            }
        })
    }


    public toNextPage() {
        if (this.productsPage.pageInfo.hasNext) {
            this.page += 1;

            // Fetch the next customers
            this.getPaginatedProducts()
        }
    }

    public toPreviousPage() {
        if (this.productsPage.pageInfo.hasPrevious) {
            this.page -= 1;

            // Fetch the previous products
            this.getPaginatedProducts()
        }
    }

    public updatePageIndex(page: number) {
        if(page <= this.productsPage.pageInfo.totalPages - 1) {
            this.page = page;

            // Fetch the previous products
            this.getPaginatedProducts()
        }
    }


}
