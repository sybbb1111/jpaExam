package com.green.jpaexam.product;

import com.green.jpaexam.category.CategoryRepository;
import com.green.jpaexam.detail.ProductDetailRepository;
import com.green.jpaexam.entity.CategoryEntity;
import com.green.jpaexam.entity.ProductDetailEntity;
import com.green.jpaexam.entity.ProviderEntity;
import com.green.jpaexam.product.model.ProductDto;
import com.green.jpaexam.entity.ProductEntity;
import com.green.jpaexam.product.model.ProductRes;
import com.green.jpaexam.product.model.ProductUpdDto;
import com.green.jpaexam.provider.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDao dao;
    private final ProductRepository productRep;
    private final ProductDetailRepository productDetailRep;
    private final CategoryRepository categoryRep;
    private final ProviderRepository providerRep;

    public ProductRes saveProduct2(ProductDto dto){

        //서로서로 연결해서
        CategoryEntity categoryEntity = categoryRep.findById(dto.getCategoryId()).get();
        ProviderEntity providerEntity = providerRep.findById(dto.getProviderId()).get();

        ProductDetailEntity productDetailEntity = ProductDetailEntity.builder()
                .description(dto.getDescription())
                .build();


        ProductEntity productEntity = ProductEntity.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .stock(dto.getStock())

                .categoryEntity(categoryEntity)
                .providerEntity(providerEntity)

                .build();

        productEntity.setProductDetailEntity(productDetailEntity);
        //productDetailEntity.setProductEntity(productEntity); 프로덕트엔티티에 맨 마지막 setProductEtailEntity
        //라는 메소드를 추가했기 때문에 프로덕트엔티티에서 한꺼번에 연결 가능하게 되어서, 서로 연결할 필요 없어서 이 줄은 없어도 된다


        productRep.save(productEntity);

        return null;//이걸로 인서트 두개 다 되는지 확인해봅시다
    }



    public ProductRes saveProduct1(ProductDto dto){
        CategoryEntity categoryEntity = categoryRep.findById(dto.getCategoryId()).get();
        ProviderEntity providerEntity = providerRep.findById(dto.getProviderId()).get();
        //find 하는 순간 영속성으로 관리가 됨


        ProductEntity productEntity = ProductEntity.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .stock(dto.getStock())

                .providerEntity(providerEntity)
                .categoryEntity(categoryEntity)
                .build();

        productRep.save(productEntity); //저장하는 순간 영속상태가 된다

        ProductDetailEntity detailEntity = ProductDetailEntity.builder()
                .productEntity(productEntity)
                .description(dto.getDescription())
                .build();

        productDetailRep.save(detailEntity);

        return ProductRes.builder()
                .number(productEntity.getNumber())
                .name(productEntity.getName())
                .price(productEntity.getPrice())
                .stock(productEntity.getStock())

                .providerNm(productEntity.getProviderEntity().getName())
                .categoryNm(productEntity.getCategoryEntity().getName())

                .description(detailEntity.getDescription())

                .createdAt(productEntity.getCreatedAt().toString())

                .build();


    }

    public Page<ProductRes> getProductAll(Pageable page) {
        return dao.getProductAll(page);
    }

    public ProductRes getProduct(Long number) {
        return dao.getProduct(number);
    }

    public ProductRes updProduct(ProductUpdDto dto){
        ProductEntity entity = ProductEntity.builder()
                .number(dto.getNumber())
                .name(dto.getName())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .build();

        entity.setCreatedAt(LocalDateTime.now());
        return dao.updProduct(entity);
    }

    public void delProduct(Long number){
        dao.delProduct(number);

    }
}
