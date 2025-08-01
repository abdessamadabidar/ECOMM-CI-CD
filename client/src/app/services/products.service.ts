import { Injectable } from '@angular/core';
import {Apollo} from "apollo-angular";
import {PaginationQueryVariables, UniqueObjectByIdVariable} from "../types/types";
import {PRODUCTS_PAGE, SINGLE_PRODUCT_BY_ID} from "../graphql/queries";

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

    constructor(private readonly apollo: Apollo) { }

    fetchPaginatedProducts(page: number, size: number) {
        return this.apollo.watchQuery<any, PaginationQueryVariables>({
            query: PRODUCTS_PAGE,
            variables: {
                page: page,
                size: size
            },
            fetchPolicy: 'network-only'
        }).valueChanges
    }

    fetchSingleProduct(id: string) {
        return this.apollo.watchQuery<any, UniqueObjectByIdVariable>({
            query: SINGLE_PRODUCT_BY_ID,
            variables: {
                id: id
            },
            fetchPolicy: 'network-only'
        }).valueChanges
    }


}
