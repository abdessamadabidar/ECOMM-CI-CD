package com.devops.shopino.services;

import com.devops.shopino.dto.request.OrderRequest;
import com.devops.shopino.dto.response.OrderResponseDto;
import com.devops.shopino.entities.Customer;
import com.devops.shopino.entities.Order;
import com.devops.shopino.entities.OrderLine;
import com.devops.shopino.entities.Product;
import com.devops.shopino.exceptions.OrderNotFoundException;
import com.devops.shopino.exceptions.ProductAvailableQuantityInsufficientException;
import com.devops.shopino.mappers.IOrderMapper;
import com.devops.shopino.repositories.IOrderRepository;
import com.devops.shopino.services.interfaces.ICustomerService;
import com.devops.shopino.services.interfaces.IOrderLineService;
import com.devops.shopino.services.interfaces.IOrderService;
import com.devops.shopino.services.interfaces.IProductService;
import com.devops.shopino.utils.StringGenerator;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements IOrderService {
    private final IOrderRepository orderRepository;
    private final ICustomerService customerService;
    private final IProductService productService;
    private final IOrderLineService orderLineService;
    private final IOrderMapper mapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public Page<OrderResponseDto> getPagedOrders(int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        Page<Order> orderPage = orderRepository.findAll(pr);
        List<OrderResponseDto> mappedOrdersList = mapper
                .ordersToOrderResponses(
                        orderPage.getContent()
                );

        return new PageImpl<>(mappedOrdersList, pr, orderPage.getTotalElements());
    }

    @Override
    public OrderResponseDto makeOrder(OrderRequest request) {

        // get the customer if exists, if not will throw an exception
        Customer customer = customerService.getCustomerById(request.getCustomerId());
        // init the products cart
        List<Product> products = Collections.emptyList();
        // init the total amount
        AtomicReference<Double> totalAmount = new AtomicReference<>(0D);
        // init the order items list
        List<OrderLine> orderLines = new ArrayList<>();

        request.getOrderItems()
                .forEach(
                        (productId, quantity) -> {
                            Product product = productService.getProductById(productId);
                            // check whether tha available quantity is sufficient
                            if (quantity > product.getAvailableQuantity()) {
                                LOGGER.error(
                                        """
                                                Insufficient product {} available quantity\s
                                                Available : {}\s
                                                Wanted : {}\s
                                        """,
                                        product.getName(),
                                        product.getAvailableQuantity(),
                                        quantity
                                );
                                throw new ProductAvailableQuantityInsufficientException(
                                        "Product's available quantity is insufficient"
                                );
                            }

                            // decrement the available quantity
                            product.setAvailableQuantity(
                                    product.getAvailableQuantity() - quantity
                                    );

                            // increment the total amount of the order
                            totalAmount.updateAndGet(v -> v + product.getPrice() * quantity);

                            // build the order item and save it
                            // the order is set after its saving
                            OrderLine orderLine = OrderLine
                                    .builder()
                                    .quantity(quantity)
                                    .product(product)
                                    .build();

                            orderLines.add(orderLine);
                            orderLineService.registerOrderLine(orderLine);

                        }
                );



        // build the order and save it
        Order order = Order
                .builder()
                .orderedAt(
                        LocalDateTime.now().minusDays(
                                new Random().nextInt(1, 365)
                        )
                )
                .reference(StringGenerator.generate(9, 5, 14))
                .totalAmount(totalAmount.get())
                .customer(customer)
                .orderLines(orderLines)
                .build();

        Order savedOrder = orderRepository.save(order);
        orderLines.forEach(ol -> ol.setOrder(savedOrder));
        customer.getOrders().add(savedOrder);


        return mapper.orderToOrderResponse(savedOrder);
    }

    @Override
    public OrderResponseDto searchOrderById(UUID id) {

        Order order = orderRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            LOGGER.error("Order with id {} not found!", id);
                            return new OrderNotFoundException(
                                    "Order not found!");
                        }
                );

        return mapper.orderToOrderResponse(order);
    }

    @Override
    public Long countOrders() {
        return orderRepository.count();
    }

    @Override
    public Double computeTotalIncome() {
        return orderRepository.calculateTotalIncome();
    }

    @Override
    public Map<Integer, Double> incomeVariationOfTheYear() {

        Map<Integer, Double> incomeVariationMap =  new HashMap<>();
                orderRepository.
                        queryIncomeVariationOfTheYear()
                        .forEach(tuple -> {
                            Integer month = tuple.get("month", Integer.class);
                            Double amount = tuple.get("amount", Double.class);
                            incomeVariationMap.put(month, amount);
                        });
        return incomeVariationMap;
    }
}
