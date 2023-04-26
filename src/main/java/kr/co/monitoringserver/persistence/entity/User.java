package kr.co.monitoringserver.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import kr.co.monitoringserver.service.enums.RoleType;
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
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, length = 30, unique = true)
    private String identity;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 30)
    private String department;

    @Enumerated(EnumType.STRING)
    @Column(name="role_type")
    private RoleType roleType;

    @CreationTimestamp
    @Column(name="created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name="updated_at")
    private Timestamp updatedAt;

    @Column(nullable = false, length = 30)
    private String telephone;
}
