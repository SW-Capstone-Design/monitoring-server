package kr.co.monitoringserver.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String identity;

    private String password;

    private String name;

    private String phone;
}
