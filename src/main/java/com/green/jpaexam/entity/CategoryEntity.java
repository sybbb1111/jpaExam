package com.green.jpaexam.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@Table(name = "t_category")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @Column(unique = true)
    private String code;

    private String name;

    @OneToMany(mappedBy = "categoryEntity", cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "provider_id")
    @ToString.Exclude //양방향일때는 얘가 필수
    private List<ProductEntity> productEntityList;

}