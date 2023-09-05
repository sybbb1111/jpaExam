package com.green.jpaexam.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
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

    @Builder.Default //리스트로줄거면 꼭 붙이세요 어레이리스트의 주소값 가져올수잇음?
    @OneToMany(mappedBy = "categoryEntity", cascade = CascadeType.PERSIST)
    //mappedBy 주인이 아닌 엔터티에서 양방향으로 걸 때 꼭 들어감(그래야 다른 새로운 테이블이 안만들어짐)
    //카테고리1, 프로덕트 다 관계 이면 '다'쪽이 주인이겠죠, mappedBy주면 주인쪽에만 새롭게 생성되고 다른테이블 안만들어지게 할 수 있음
//    @JoinColumn(name = "provider_id")
    @ToString.Exclude //양방향일때는 얘가 필수
    private List<ProductEntity> productEntityList = new ArrayList();

}