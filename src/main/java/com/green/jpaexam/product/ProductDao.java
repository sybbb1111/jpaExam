package com.green.jpaexam.product;

import com.green.jpaexam.entity.ProductEntity;
import com.green.jpaexam.product.model.ProductRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductDao { //다오의 장점 - jpa하다가 마이바티스 넘어가도 이거를 매퍼처럼 그대로 쓰면 됨(확실히 느슨하게 연결되어서 유연성이 있음)
    ProductRes saveProduct(ProductEntity p);
    Page<ProductRes> getProductAll(Pageable page);
    ProductRes getProduct(Long number);
    ProductRes updProduct(ProductEntity p);
    void delProduct(Long number);


}
