import {Component, OnInit} from '@angular/core';
import {ProductsService} from "../../services/products.service";
import {Product} from "../../types/types";
import {ActivatedRoute, RouterLink} from "@angular/router";

@Component({
  selector: 'app-single-product',
  standalone: true,
    imports: [
        RouterLink
    ],
  templateUrl: './single-product.component.html',
  styleUrl: './single-product.component.css'
})
export class SingleProductComponent implements OnInit {
    public product!: Product;

    constructor(
        private readonly productsService: ProductsService,
        private readonly route: ActivatedRoute
    ) {
    }
    ngOnInit(): void {
        this.route.params.subscribe(params => {
            this.getSingleProduct(params['id']);
        })

    }


    private getSingleProduct(productId: string) {
        this.productsService
            .fetchSingleProduct(productId)
            .subscribe({
                next: ({data, loading}) => {
                    this.product = data['productById']
                    console.log(this.product);
                },
                error: err => console.log(err)
            })
    }

}
