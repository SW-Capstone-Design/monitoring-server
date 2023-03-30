package kr.co.monitoringserver.persistence.entity;

import jakarta.persistence.*;
import kr.co.monitoringserver.persistence.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_users")
@AttributeOverride(     // 하나의 Entity에서 같은 값 타입을 사용하면 Column 명이 중복되므로 Column 명 속성을 재정의
        name = "id",
        column = @Column(name = "users_id", length = 4))
public class User extends BaseEntity {

    private String identity;

    private String password;

    private String name;

    private String phone;

    private String department;
}
