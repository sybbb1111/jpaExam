package com.green.jpaexam.product;

import com.green.jpaexam.entity.ProductEntity;
import com.green.jpaexam.product.model.ProductRes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("select new com.green.jpaexam.product.model.ProductRes" +
            "(p.number, p.name, p.price, p.stock, d.description, c.name, v.name, p.createdAt)" +
            " from ProductEntity p join p.productDetailEntity d join p.categoryEntity c join p.providerEntity v" +
            " where p.name = :productName and p.price >= :price") //select p는 select *와 같은 의미
    List<ProductRes> selProductAll(String productName, int price, Pageable page);
    //포린키가 정확하게 걸려있으면 join p.~ 이렇게만 해도되고, 정확하게 포린키 안걸려있으면 뒤에 on ~해줘야함
    //이 때 포린키 걸려있을 때 on저 뒤에 on적으면 그 on은 and 문이 되고

    //@Query("select d from ProductDetailEntity d join CategoryEntity c on d.productnumber = c.id")
    //전혀 포린키가 안걸려있는 두 테이블 끼리 연결, 이 때 and는 on 문이 됨
    /*
    SELECT * FROM t_product_detail d
    LEFT JOIN t_category c
    ON d.product_number = C.id;
     */






}
