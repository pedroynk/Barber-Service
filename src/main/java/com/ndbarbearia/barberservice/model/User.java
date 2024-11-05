package com.ndbarbearia.barberservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "usr_name", nullable = false)
    private String name;

    @Column(name = "usr_email", nullable = false, unique = true)
    private String email;

    @Column(name = "usr_password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "usr_profile", nullable = false)
    private Profile profile;

    @Column(name = "usr_active", nullable = false)
    private Boolean active;

    @Column(name = "usr_profile_pic_path")
    private String profilePicture;
}
