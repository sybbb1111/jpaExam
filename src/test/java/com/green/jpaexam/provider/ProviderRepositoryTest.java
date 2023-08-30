package com.green.jpaexam.provider;

import com.green.jpaexam.entity.ProducerEntity;
import com.green.jpaexam.entity.ProductEntity;
import com.green.jpaexam.entity.ProviderEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@Slf4j
//@RequiredArgsConstructor //테스트 때는 이거 안먹힙니다
@DataJpaTest //jpa관련된 빈들만 로딩됨,, 안되면 다시 SpringBootTest로 돌리고 원인을 찾아보자ㅠ
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//슬라이스 테스트 할때는 위 두개를 써야함
//@SpringBootTest
public class ProviderRepositoryTest {
    @Autowired
    private ProviderRepository rep;

    @Test
    @Rollback(false)
    void cascadeTest() {        //영속성 전이
        ProviderEntity providerEntity = new ProviderEntity();
        providerEntity.setName("테스트 업체2");

        ProductEntity p1 = ProductEntity.builder().name("상품1").price(1000).stock(100).build();
        ProductEntity p2 = ProductEntity.builder().name("상품2").price(2000).stock(200).build();
        ProductEntity p3 = ProductEntity.builder().name("상품3").price(3000).stock(300).build();

        //프로덕트 多, 프로바이더 1, 다대일관계

        p1.setProviderEntity(providerEntity);
        p2.setProviderEntity(providerEntity);
        p3.setProviderEntity(providerEntity);
        //그래서 위 처럼하면 providerEntity에 3개의 물품이 들어가서 물품 리스트가 됨

        providerEntity.getProductEntityList().addAll(Lists.newArrayList(p1, p2, p3));
        //물품 p1,p2,p3세 개를 한 리스트에 담아서 ProductEntity list에 담아준다. 윗줄의 Lists라는 기능 없었으면 새로 리스트만들고 add 3번해야했겠죠?

        rep.save(providerEntity); //save로 providerEntity를 저장한다



    }
}
