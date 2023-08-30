package com.green.jpaexam.entity;

import com.green.jpaexam.config.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Table(name = "t_product_detail")
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ProductDetailEntity extends BaseEntity {
    @Id
    private Long number;

    @OneToOne
    @MapsId
    @JoinColumn(updatable = false, nullable = false, name = "product_number", columnDefinition = "BIGINT UNSIGNED")
    private ProductEntity productEntity;

    private String description;




}
