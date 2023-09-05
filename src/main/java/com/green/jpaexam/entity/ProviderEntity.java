package com.green.jpaexam.entity;

import com.green.jpaexam.config.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_provider")
@SuperBuilder
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ProviderEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED") //빅
    private Long id;

    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "providerEntity", cascade = CascadeType.PERSIST) //mappedBy를 적어주면 이상한 테이블 하나 더 안만들어지고
    // 캐스케이드=영속성전이!! 프로바이더에 값넣을 때 만약에 그 프로바이더에 해당하는 물품있을경우 물품까지 같이 인서트됨!!
    //그대로 연결해서 쓸 수 있어요, 그니까 거의 필수겠죠?
    //포린키(외래키)가 안들어가는 테이블에 mappedBy 를 적어주면 됩니다. 컬럼명아니고 객체
    @ToString.Exclude
    private List<ProductEntity> productEntityList = new ArrayList<>(); //리스트 쓰는거는 다 뉴 어레이리스트 꼭 해줘야함 !!
    //양방향일 때는 이걸 추가로 적어주고요, 하나의 프로바이더에 여러 프로덕트가 있으므로
    //리스트 로 적어줘야 합니다.
    //양방향이면 둘다 서로에게 접근할 수 있는 것
    //이거 적으면 얘도 프로덕트엔터티에 접근할 수 있어요

}
