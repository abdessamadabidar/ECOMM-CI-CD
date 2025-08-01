import { Routes } from '@angular/router';
import {DashboardComponent} from "./components/dashboard/dashboard.component";
import {CustomersComponent} from "./components/customers/customers.component";
import {ProductsComponent} from "./components/products/products.component";
import {OrdersComponent} from "./components/orders/orders.component";
import {SingleProductComponent} from "./components/single-product/single-product.component";
import {SingleOrderComponent} from "./components/single-order/single-order.component";

export const routes: Routes = [
    {
        path: "",
        component: DashboardComponent
    },
  {
    path: "admin",
    children: [
      {
        path: "dashboard",
        component: DashboardComponent
      },
      {
        path: "customers",
        component: CustomersComponent
      },
      {
        path: "products",
          children: [
              {
                  path: '',
                  component: ProductsComponent,
              },
              {
                  path: ":id",
                  component: SingleProductComponent
              }
          ]
      },
      {
        path: "orders",
        children: [
            {
                path: '',
                component: OrdersComponent
            },
            {
                path: ':id',
                component: SingleOrderComponent
            }
        ]
      }
    ]
  }
];
