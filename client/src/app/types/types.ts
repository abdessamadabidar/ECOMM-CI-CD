export type PaginationQueryVariables =  {
    page: number
    size: number
}

export type UniqueObjectByIdVariable = {
    id: string
}

export type CustomersPage = {
    content: Customer[]
    pageInfo: PageInfo
}

export type ProductsPage = {
    content: Product[]
    pageInfo: PageInfo
}

export type OrdersPage ={
    content: Order[]
    pageInfo: PageInfo
}


type Customer =  {
  id: string
  firstName: string
  lastName: string
  email: string
  phone: string
  address: Address

}

type Address = {
  zipCode: number
  street: string
  houseNumber: number
  city: string
}


export type Product = {
    id: string
    name: string
    description: string
    reference: string
    imageUrl: string
    price: number
    availableQuantity: number
    category: Category;
}

type Category = {
    id: string
    name: string
}

export type Order = {
    id: string
    orderedAt: string
    reference: string
    totalAmount: number
    customer: Customer
    orderLines: [OrderLine]
}


type OrderLine = {
    id: string
    quantity: number
    product: Product
}


type PageInfo = {
    totalPages: number
    totalElements: number
    currentPage: number
    size: number
    hasPrevious: boolean
    hasNext: boolean
}


export type GeneralStatistics = {
    numberOfCustomers: number
    numberOfProducts: number
    numberOfOrders: number
    totalIncome: number
}


export type FetchBestSellingProductsVariables = {
    limit: number
}

export type BestSellingProductsMap = {
    key: Product
    value: number
}

export type MonthIncome = {
    key: string
    value: number
}
