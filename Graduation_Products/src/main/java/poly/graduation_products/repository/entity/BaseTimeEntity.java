package poly.graduation_products.repository.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract  class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime CREATE_DATE;  //생성일자

    @LastModifiedDate
    private LocalDateTime MODIFIED_DATE; //수정일자
}
