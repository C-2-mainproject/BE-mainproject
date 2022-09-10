package com.wolves.mainproject.domain.user;

import com.wolves.mainproject.domain.common.Timestamped;
import com.wolves.mainproject.role.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import reactor.util.annotation.Nullable;

import javax.persistence.*;
import java.io.Serializable;

@ToString
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Entity
public class User extends Timestamped implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 30, unique = true)
    private String nickname;

    @Column(nullable = false, name = "profile_image")
    private String profileImage;

    @Column(nullable = false, name = "age_group")
    private int ageGroup;

    @Column(nullable = false, length = 2)
    private String gender;

    @Column(length = 20)
    private String provider;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RoleType role;
}
