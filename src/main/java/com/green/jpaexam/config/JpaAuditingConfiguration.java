package com.green.jpaexam.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfiguration {
    //지금 이 jpa 컨피규레이션을 만들고 위의 두 어노테이션을 붙여야 베이스엔티티 등을 사용할 수 있음

}
