package com.self.order_service.repository;

import com.self.order_service.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    Order findByOrderUuid(String orderUuid);

    Page<Order> findByUserId(Long userId, Pageable pageable);
}
