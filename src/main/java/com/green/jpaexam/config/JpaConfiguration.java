package com.green.jpaexam.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
public class JpaConfiguration {
    @PersistenceContext
    private EntityManager em;

    @Bean
    public JPAQueryFactory queryFactory() { return new JPAQueryFactory(em); }
    //지금 이 jpa 컨피규레이션을 만들고 위의 두 어노테이션을 붙여야 베이스엔티티 등을 사용할 수 있음

}
