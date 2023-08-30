package com.green.jpaexam.config;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // 요 어노테이션 꼭! 오디팅이라는 스프링에서 제공하는 기능을 이용하려면 꼭 붙여야 함
@SuperBuilder
@NoArgsConstructor
public class BaseEntity {

   // @CreatedDate // @CreationTimestamp와 같은기능
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    //@LastModifiedDate //스프링에서 제공, @UpdateTimestamp와 같은기능
    private LocalDateTime updatedAt;

    public String getCreatedAtDatetime() {
     return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
    }


}
