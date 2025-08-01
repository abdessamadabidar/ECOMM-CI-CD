import { Injectable } from '@angular/core';
import {Apollo} from "apollo-angular";
import {PaginationQueryVariables, UniqueObjectByIdVariable} from "../types/types";
import {ORDERS_PAGE, SINGLE_ORDER_BY_ID} from "../graphql/queries";

@Injectable({
  providedIn: 'root'
})
export class OrdersService {

    constructor(private readonly apollo: Apollo) { }

    fetchPaginatedOrders(page: number, size: number) {
        return this.apollo.watchQuery<any, PaginationQueryVariables>({
            query: ORDERS_PAGE,
            variables: {
                page: page,
                size: size
            },
            fetchPolicy: 'network-only'
        }).valueChanges
    }

    fetchSingleOrder(id: string) {
        return this.apollo.watchQuery<any, UniqueObjectByIdVariable>({
            query: SINGLE_ORDER_BY_ID,
            variables: {
                id: id
            },
            fetchPolicy: 'network-only'
        }).valueChanges
    }

}
