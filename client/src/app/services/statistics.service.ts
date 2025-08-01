import { Injectable } from '@angular/core';
import {Apollo} from "apollo-angular";
import {BEST_SELLING_PRODUCTS, INCOME_VARIATION_OF_THE_YEAR, STATISTICS} from "../graphql/queries";
import {FetchBestSellingProductsVariables} from "../types/types";

@Injectable({
  providedIn: 'root'
})
export class StatisticsService {

    constructor(private readonly apollo: Apollo) { }

    public collectStatistics() {
        return this.apollo.subscribe<any>({
            query: STATISTICS,
        })
    }

    public fetchBestSellingProducts(limit: number) {
        return this.apollo.subscribe<any, FetchBestSellingProductsVariables>({
            query: BEST_SELLING_PRODUCTS,
            variables: {
                limit: limit
            },
        })
    }

    public queryIncomeVariationOfTheYear() {
        return this.apollo.subscribe<any>({
            query: INCOME_VARIATION_OF_THE_YEAR,
        })
    }
}
