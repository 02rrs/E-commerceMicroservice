package com.self.payment_service.mapper;

import com.self.payment_service.dto.PaymentRequestDTO;
import com.self.payment_service.dto.PaymentResponseDTO;
import com.self.payment_service.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(source = "orderIdentifier", target = "orderUuid")
    @Mapping(source = "paymentMode", target = "paymentMethod")
    Payment toEntity(PaymentRequestDTO requestDTO);


    @Mapping(source = "paymentUuid", target = "paymentIdentifier")
    @Mapping(source = "orderUuid", target = "orderIdentifier")
    @Mapping(source = "amount", target = "paymentAmount")
    @Mapping(source = "paymentMethod", target = "paymentMode")
    @Mapping(source = "status", target = "paymentStatus")
    @Mapping(source = "paymentDate", target = "paymentTimestamp")
    PaymentResponseDTO toResponseDTO(Payment payment);
}
