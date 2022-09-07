package com.wolves.mainproject.domain.user;

import com.wolves.mainproject.domain.common.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Entity
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false, name = "profile_image")
    private String profileImage;

    @Column(nullable = false, name = "age_group")
    private int ageGroup;

    @Column(nullable = false, length = 2)
    private String gender;

    @Column(nullable = false, length = 20)
    private String provider;

    @Column(nullable = false, length = 20)
    private String role;
}
