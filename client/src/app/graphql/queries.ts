import {gql} from "apollo-angular";




export const CUSTOMERS_PAGE = gql`
    query fetchCustomers($page: Int!, $size: Int!) {
        paginatedCustomers(page: $page, size: $size) {
            content {
                id
                firstName
                lastName
                email
                phone
                address {
                    zipCode
                    street
                    houseNumber
                    city
                }
            }
            pageInfo {
                totalElements
                totalElements
                size
                currentPage
                hasPrevious
                hasNext
            }
        }
    }
`

export const PRODUCTS_PAGE = gql`
    query fetchProducts($page: Int!, $size: Int!) {
        paginatedProducts(page: $page, size: $size) {
            content {
                id
                name
                reference
                description
                imageUrl
                price
                availableQuantity
                category {
                    id
                    name
                }
            }
            pageInfo {
                totalElements
                totalPages
                size
                currentPage
                hasPrevious
                hasNext
            }
        }
    }

`


export const ORDERS_PAGE = gql`
    query fetchOrders($page: Int!, $size: Int!) {
        paginatedOrders(page: $page, size: $size) {
            content {
                id
                orderedAt
                reference
                totalAmount
                customer {
                    id
                    firstName
                    lastName
                }
                orderLines {
                    id
                    quantity
                    product {
                        id
                    }
                }
            }
            pageInfo {
                totalElements
                totalPages
                size
                currentPage
                hasPrevious
                hasNext
            }

        }
    }

`

export const SINGLE_PRODUCT_BY_ID = gql`

    query fetchSingleProduct($id: ID) {
        productById(id: $id) {
            id
            name
            reference
            description
            imageUrl
            price
            availableQuantity
            category {
                id
                name
            }
        }
    }
`

export const SINGLE_ORDER_BY_ID = gql`

    query fetchSingleOrder($id: ID) {
        orderById(id: $id) {
            id
            orderedAt
            reference
            totalAmount
            customer {
                id
                firstName
                lastName
                email
                phone
            }
            orderLines {
                id
                quantity
                product {
                    id
                    name
                    imageUrl
                    price
                    reference
                    category {
                        id, name
                    }
                }
            }
        }
    }
`

export const REMOVE_CUSTOMER = gql`

    mutation removeCustomer($id: ID) {
        deleteCustomer(id: $id)
    }
`

export const STATISTICS = gql`
    subscription {
        generalStatistics {
            numberOfCustomers
            numberOfProducts
            numberOfOrders
            totalIncome
        }
    }

`


export const BEST_SELLING_PRODUCTS = gql`
    subscription($limit: Int!) {
        bestSellingProducts(limit: $limit) {
            key {
                name
                price
                availableQuantity
                reference
                category {
                    name
                }
            }
            value
        }
    }

`

export const INCOME_VARIATION_OF_THE_YEAR = gql`
    subscription {
        incomeVariationOfTheYear {
            key
            value
        }
    }

`
