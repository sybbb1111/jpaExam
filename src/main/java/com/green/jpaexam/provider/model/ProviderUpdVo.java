package com.green.jpaexam.provider.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.context.annotation.Primary;

@Getter
@Builder
public class ProviderUpdVo {
    private Long id;
    private String name;
    private String updatedAt;
}
