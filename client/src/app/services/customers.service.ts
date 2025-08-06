import { Injectable } from '@angular/core';
import {Apollo} from "apollo-angular";
import {CustomersPage, PaginationQueryVariables, UniqueObjectByIdVariable} from "../types/types";
import {CUSTOMERS_PAGE, REMOVE_CUSTOMER} from "../graphql/queries";
import {catchError, tap, throwError} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class CustomersService {


  constructor(private readonly apollo: Apollo) {
  }

    fetchPaginatedCustomers(page: number, size: number) {


        return this.apollo.watchQuery<any, PaginationQueryVariables>({
            query: CUSTOMERS_PAGE,
            variables: {
                page: page,
                size: size
            },
            fetchPolicy: 'network-only'
        }).valueChanges.pipe(
            tap(result => {
                console.log('GraphQL Result:', result);
                console.log('Data:', result.data);
                console.log('Loading:', result.loading);
                console.log('Errors:', result.errors);
            }),
            catchError(error => {
                console.error('GraphQL Error:', error);
                return throwError(error);
            })
        );
    }



    deleteCustomer(id: string) {
      return this.apollo
          .mutate<any, UniqueObjectByIdVariable>({
              mutation: REMOVE_CUSTOMER,
              variables: {
                  id: id
              }
          })
  }

}
