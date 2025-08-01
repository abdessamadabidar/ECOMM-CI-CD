package com.devops.shopino.repositories;

import com.devops.shopino.entities.Order;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IOrderRepository extends JpaRepository<Order, UUID> {
    @Query("select SUM (o.totalAmount) as totalIncome from Order o")
    Double calculateTotalIncome();

    @Query(value = """
            select EXTRACT(MONTH FROM ordered_at)::INTEGER AS month , sum(total_amount) AS amount
            from orders
            where date_part('year', ordered_at) = date_part('year', current_date)
            group by month
            order by month;
        """,
    nativeQuery = true)
    List<Tuple> queryIncomeVariationOfTheYear();
}
