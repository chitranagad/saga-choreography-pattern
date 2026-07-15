package com.example.dto;

import lombok.Data;

@Data
public class OrderRequestDto {

    private Integer userId;
    private Integer productId;
    private Integer amount;
    private Integer orderId;
}
