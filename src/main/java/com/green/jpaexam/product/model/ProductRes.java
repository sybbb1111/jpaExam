package com.green.jpaexam.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor //얘네들 다 담을 수 있는 생성자 만들어지죠잉(물론 순서 중요)
public class ProductRes {
    private Long number;
    private String name;
    private int price;
    private int stock;

    private String description;
    private String categoryNm;
    private String providerNm;

    private LocalDateTime createdAt;
}
