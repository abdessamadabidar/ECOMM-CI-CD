package com.devops.shopino.repositories;


import com.devops.shopino.entities.OrderLine;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IOrderLineRepository extends JpaRepository<OrderLine, UUID> {
    @Query(
            value = """
                    select ol.product_id, p.name as product_name, p.price, c.name as category_name, p.available_quantity, p.reference ,sum(p.price * ol.quantity) as amount
                    from order_line as ol
                    join product p on p.id = ol.product_id
                    join category c on p.category_id = c.id
                    group by ol.product_id, p.name, p.price, c.name, p.available_quantity, p.reference
                    order by amount desc
                    limit ?1;
                """,
            nativeQuery = true)
    List<Tuple> getBestSellingProducts(int limit);
}
