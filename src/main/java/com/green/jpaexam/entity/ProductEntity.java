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

    @OneToOne(mappedBy = "productEntity", cascade = { CascadeType.PERSIST})
    @ToString.Exclude
    //캐스케이드REMOVE 하면 관련된 애가 다 삭제됨, orphanRemoval-true 도 비슷함
    //PERSIST 가 거의 왠만한역할을 다 하는거같은데,, 더알아보고 알려줄게요(도흠샘) ㅠ
    //영속성 전의는 양방향 일 때만 됩니다
    private ProductDetailEntity productDetailEntity;

    @ManyToOne
    @JoinColumn(name= "category_id")
    @ToString.Exclude
    private CategoryEntity categoryEntity;


    //프로덕트 서비스에서 saveProduct2 의 경우는 프로덕트엔터티와 디테일엔터티를 둘다 서로 연결하고 있는데, 이 메소드가 있으면 굳이
    //두개 다 에서 연결할 필요 없이, 프로덕트엔티티테이블에서 한꺼번에 연결이 가능 하다
    public void setProductDetailEntity(ProductDetailEntity productDetailEntity) {
        if(this.productDetailEntity != null) {
            this.productDetailEntity.setProductEntity(null);
        } //만약에 프로덕트디테일엔터티에 값이 있으면 그 값을 지우고(null) 아래를 실행한다
        this.productDetailEntity = productDetailEntity;
        this.productDetailEntity.setProductEntity(this);
    }







}
