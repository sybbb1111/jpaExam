package com.green.jpaexam.product;

import com.green.jpaexam.entity.*;
import com.green.jpaexam.product.model.ProductRes;
import com.green.jpaexam.product.model.ProductResQdsl;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QuerydslUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import static com.green.jpaexam.entity.QProductDetailEntity.productDetailEntity; //직접 임포트해서 쓰기
import static com.querydsl.jpa.JPAExpressions.select;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductQdsl {
    private final JPAQueryFactory jpaQueryFactory;

    private final QProductEntity p = QProductEntity.productEntity;
    private final QCategoryEntity c = QCategoryEntity.categoryEntity;
    private final QProviderEntity v = QProviderEntity.providerEntity;

    public List<ProductResQdsl> selProductAll(Pageable pageable, String search) {

        //검색어가 null일 경우에는 그냥 원래의 값이 주루룩 뜬다
        //검색어가 null이 아니면 입력한 단어가 포함된 값이 뜬다
        BooleanBuilder whereBuilder = new BooleanBuilder();
        if(search != null) {
            whereBuilder.and(p.name.contains(search))
                    .or(productDetailEntity.description.contains(search)); //contains의 결과타입 = BooleanExpression
        }


        //p.number.countDistinct().as("totalCnt"))
        JPQLQuery<ProductResQdsl> query = jpaQueryFactory.select(Projections.bean(ProductResQdsl.class,
                        p.number, p.name, p.price, p.stock, productDetailEntity.description, c.name.as("categoryNm")
                        , v.name.as("providerNm"), p.createdAt
                        , ExpressionUtils.as(JPAExpressions.select(p.number.count()).from(p), "totalCnt") //서브쿼리 사용, 서브쿼리에 알리아스 줄 때는 ExpressionUtils.as 써야 됨
                ))
                .from(p)
                .join(p.productDetailEntity, productDetailEntity)
                .join(p.categoryEntity, c)
                .join(p.providerEntity, v)

                .orderBy(p.number.desc())

                //.where(p.name.eq(search)) //eq -> like가 아님, 완벽히 똑같아야 됨
                //.where(whereBuilder)
                .where(p.number.goe(JPAExpressions.select(p.number.count()).from(p))) //프로덕트 넘버가 프로덕트의 총 개수보다 크거나 같을 때

                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())

                ;
        //left조인하고 싶으면 leftjoin적고 inner조인할거면 그냥 join 쓰세용
                //조인 안걸고 하나의 엔터티로만 셀렉트 프롬할거면 selectFrom 쓰셈

        return query.fetch();
    }

    private OrderSpecifier[] getAllOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier> orders = new ArrayList();
        if(!pageable.getSort().isEmpty()) {
            for(Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

                switch(order.getProperty().toLowerCase()) { //사용자가 혹시 대문자로입력해도 소문자로 되도록
                    case "number": orders.add(new OrderSpecifier(direction, p.number)); break;
                    case "product_name": orders.add(new OrderSpecifier(direction, p.name)); break;
                    case "price": orders.add(new OrderSpecifier(direction, p.price)); break;
                    //지금 이 부분이 더 많아질수록 세세하게 쏘트 가능
                }
            }
        }
        return orders.stream().toArray(OrderSpecifier[]::new);
    }
}
