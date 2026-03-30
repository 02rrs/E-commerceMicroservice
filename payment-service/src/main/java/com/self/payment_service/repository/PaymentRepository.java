package com.self.payment_service.repository;

import com.self.payment_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {

    Optional<Payment> findByPaymentUuid(String paymentUuid);

    Optional<Payment> findByOrderUuid(String orderUuid);
}
