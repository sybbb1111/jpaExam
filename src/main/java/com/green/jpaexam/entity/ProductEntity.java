package com.green.jpaexam.entity;

import com.green.jpaexam.config.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Table(name = "t_product")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ProductEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long number;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer stock; //재고


    @ManyToOne
    @JoinColumn(name = "provider_id") //외래키를 걸 컬럼명을 적습니다.
    //원래 프로바이더 테이블의 id를 가져올건데, 꼭id라고 안해도되고 다르게 적어도 됩니당
    //프로바이더 테이블의 주소값이 저장될 컬럼명 입니다
    @ToString.Exclude //얘는 문자열로 찍을 때 좀 빼주이소(얘는 찍지말아주세요)
    //이 객체가 투스트링으로 호출이 될 때 얘를 찍지말아주세요(제외시킴)
    //단방향일때는 제외 안시켜도된다, 단 양방향일 때는 무한루프가 돌수있으니 양방향에선 꼭 붙이고
    //지금은 사실 제외안시켜도 상관은 없는데 이게 필요한 순간이 있어요
    //양방향일 때는 저 투스트링.익스클루드 안적으면 무한루프돕니다. 양방향일 때는 꼭 적으세요!
    private ProviderEntity providerEntity;
    //지금 우리의 느낌으로는 private Long providerId 라고 적어야할 것 같지만,
    //jpa는 객체지향적으로 처리를 하기 때문에 얘가 포함된 객체 기준으로 가져옵니다.

//    @OneToOne(mappedBy = "productEntity")
//    private ProductDetailEntity productDetailEntity;

    @ManyToOne
    @JoinColumn(name= "category_id")
    @ToString.Exclude
    private CategoryEntity categoryEntity;







}
