package com.green.jpaexam.provider;

import com.green.jpaexam.entity.ProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository <ProviderEntity, Long> { // 기본적으로 JpaRepository 상속받고 <연결할 엔터티, pk값의 타입> 적어주세요, 이렇게만해도 기본적인 CRUD 만들어줌
    //기본 curd 이외 필요한 게 있으면 밑에다가 추가해서 쓰면됩니다



}
