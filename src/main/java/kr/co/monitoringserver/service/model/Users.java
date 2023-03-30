package kr.co.monitoringserver.service.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long users_id;
    @Column(nullable = false, length = 30, unique = true)
    private String identity;
    @Column(nullable = false, length = 100)
    private String password;
    @Column(nullable = false, length = 30)
    private String name;
    @Column(nullable = false, length = 30)
    private String department;
    @Enumerated(EnumType.STRING)
    private RoleType role_type;
    @CreationTimestamp
    private Timestamp created_at;
    @UpdateTimestamp
    private Timestamp updated_at;
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "10~11자리의 숫자만 입력가능합니다")
    private String phone;
}
