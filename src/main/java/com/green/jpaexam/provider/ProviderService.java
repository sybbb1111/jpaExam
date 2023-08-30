package com.green.jpaexam.provider;

import com.green.jpaexam.entity.ProducerEntity;
import com.green.jpaexam.entity.ProviderEntity;
import com.green.jpaexam.provider.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor

@Transactional //매우 중요!!!!!!!! 학문적으로-여러 개의 작업을 한 작업으로 보는것. 은행이체 떠올리시구요, 그 중 하나라도 에러가 터지면 원상태로 돌리자 !!!!!!!롤백!!!!!!!
//간단한 메소드는 딱히 필요없지만, 사진업로드나 여러업무있을 때 하면 좋다

public class ProviderService {
    private final ProviderRepository rep;

    public ProviderInsVo save(ProviderReqDto dto){
        ProviderEntity entity = ProviderEntity.builder()
                .name(dto.getName())
                .build();

        rep.save(entity); // 요 엔터티안에는 인서트 되는 순간 id값들어가고 created,updated는 자바에서 들어간다

        return ProviderInsVo.builder()  //박스갈이 해주고요
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt().toString()) //여기서 entity
                .build();

    }

    public ProviderUpdVo update(ProviderUpdDto dto) {

        //jpa는 영속성이 있는 애를 일단 가져와야돼요, 왜냐면 save만 있지 update가 따로 없거든요
        //그래서 비영속인애를 save하면 인서트, 영속인애를 save 하면 업데이트가 됩니다
        //그러려면 일단 영속인애를 데이터베이스에서 가져와서 바꿔서 저장하면 되겠죠잉
        //그래서 엔터티 매니저한테 요청해서, 리파지토리한테 findById 이용하거나 getReperenceById 이용가능
        //getReperenceById이용하면 요 시점에는 실제로 셀렉트하지않고 (레이지)
        //findByid 하면 셀렉트(

        ProviderEntity entity = rep.findById(dto.getId()).get(); //아이디로 원래 엔터티 정보를 가져온다
        //아래는 원래 위 내용과 같음(옵셔널로 가져오게됨) 그래서 뒤에 .get()을 붙여준거
        // Optional<ProviderEntity> optEntity = rep.findById(dto.getId());

        entity.setName(dto.getName()); //그 정보 중 바꾸고싶은 애(이름)를 들어온대로 바꿔준다

        rep.save(entity); //그 엔터티를 저장한다

        return ProviderUpdVo.builder()  //박스갈이 한다
                .id(entity.getId())
                .name(entity.getName())
                .updatedAt(entity.getUpdatedAt().toString())
                .build();

    }

    public void delete(Long id){
        rep.deleteById(id);

    }

    public List<ProviderSelAll> selAllProvider() {

        List<ProviderEntity> entityList = rep.findAll(Sort.by(Sort.Direction.ASC,"name"));

        List<ProviderSelAll> selAllList = entityList.stream().map(
                item -> ProviderSelAll.builder()
                .id(item.getId())
                .name(item.getName())
                .build()
        ).toList();

        return selAllList;


    }





}
