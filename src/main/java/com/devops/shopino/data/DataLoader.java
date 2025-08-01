package com.devops.shopino.data;

import com.devops.shopino.dto.request.CategoryRequest;
import com.devops.shopino.dto.request.CustomerRequest;
import com.devops.shopino.dto.request.OrderRequest;
import com.devops.shopino.dto.request.ProductRequest;
import com.devops.shopino.dto.response.CustomerResponseDto;
import com.devops.shopino.dto.response.ProductResponseDto;
import com.devops.shopino.entities.Address;
import com.devops.shopino.entities.Category;
import com.devops.shopino.services.interfaces.ICategoryService;
import com.devops.shopino.services.interfaces.ICustomerService;
import com.devops.shopino.services.interfaces.IOrderService;
import com.devops.shopino.services.interfaces.IProductService;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataLoader implements CommandLineRunner {

    @Value("${app.data.number-of-customers}")
    private short NUMBER_OF_CUSTOMERS;
    @Value("${app.data.number-of-products}")
    private short NUMBER_OF_PRODUCTS;
    @Value("${app.data.number-of-orders}")
    private short NUMBER_OF_ORDERS;

    private final ICustomerService customerService;
    private final IProductService productService;
    private final ICategoryService categoryService;
    private final IOrderService orderService;
    private final Faker faker;

    public DataLoader(ICustomerService customerService, IProductService productService, ICategoryService categoryService, IOrderService orderService) {
        this.customerService = customerService;
        this.productService = productService;
        this.categoryService = categoryService;
        this.orderService = orderService;
        this.faker = new Faker();
    }

    @Override
    public void run(String... args) throws Exception {

        /*
        *   Generate data for customer table
        */
        List<CustomerRequest> customerRequestList = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_CUSTOMERS; i++) {
            CustomerRequest request = CustomerRequest
                    .builder()
                    .firstName(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .phone(faker.phoneNumber().cellPhone())
                    .email(faker.internet().emailAddress())
                    .address(
                            Address
                                    .builder()
                                    .zipCode(Long.valueOf(faker.address().zipCode()))
                                    .houseNumber(Integer.valueOf(faker.address().buildingNumber()))
                                    .street(faker.address().streetAddress())
                                    .city(faker.address().cityName())
                                    .build()
                    )
                    .build();

            customerRequestList.add(request);
        }
        customerService.registerCustomers(customerRequestList);



        /*
         *   Generate data for category table
         */

        List<CategoryRequest> categoryRequestsList = new ArrayList<>();
        List<String> categoryNames = List.of(
                "Electronics & Gadgets",
                "Home Appliances",
                "Beauty & Personal Care",
                "Sports & Outdoors",
                "Fashion & Apparel",
                "Health & Wellness",
                "Automotive Accessories",
                "Books & Stationery",
                "Toys & Games",
                "Food & Beverages",
                "Furniture & Decor",
                "Pet Supplies",
                "Baby & Maternity",
                "Jewelry & Watches",
                "Tools & Hardware",
                "Garden & Outdoor Living",
                "Musical Instruments",
                "Office Supplies",
                "Travel & Luggage",
                "Fitness & Training Equipment"
        );

        categoryNames
                .forEach(cName -> {
                    CategoryRequest category = CategoryRequest
                            .builder()
                            .name(cName)
                            .description(faker.lorem().characters(20, 50))
                            .build();

                    categoryRequestsList.add(category);
                });

        categoryService.registerCategories(categoryRequestsList);



        /*
         *   Generate data for product table
         */

        // Load product categories
        List<Category> categories = categoryService.getAllCategories();
        List<ProductRequest> productRequestList = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_PRODUCTS; ++i) {
            ProductRequest request = ProductRequest
                    .builder()
                    .name(faker.commerce().productName())
                    .description(faker.lorem().sentence(10))
                    .imageUrl(
                            String.format(
                                    "https://prd.place/320?id=%s?padding=100",
                                    faker.random().nextInt(1, 45)
                            )
                    )
                    .price(Double.parseDouble(faker.commerce().price(20, 200)))
                    .availableQuantity(faker.random().nextInt(10, 99))
                    .categoryId(
                            categories
                                    .get(faker.random().nextInt(categories.size() - 1))
                                    .getId()
                    )
                    .build();
            productRequestList.add(request);

        }
        productService.registerProducts(productRequestList);


        /*
         *   Generate data for order table
         */

        // Load all customers and products
        List<CustomerResponseDto> customers = customerService.getAllCustomers();
        List<ProductResponseDto> products = productService.getAllProducts();

        for (int i = 0; i < NUMBER_OF_ORDERS; ++i) {
            CustomerResponseDto randomCustomer = customers
                    .get(faker.random().nextInt(customers.size() - 1));

            // building shop cart
            Map<UUID, Integer> cart = new HashMap<>();
            // each customer may have between 1 and 5 order items
            for (int j = 0; j < faker.random().nextInt(1, 5); ++j) {
                cart.put(
                        products.get(faker.random().nextInt(products.size() - 1)).getId(),
                        faker.random().nextInt(1, 5)
                );

            }

            OrderRequest request = OrderRequest
                    .builder()
                    .customerId(randomCustomer.getId())
                    .orderItems(cart)
                    .build();

            orderService.makeOrder(request);

        }

    }
}
