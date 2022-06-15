package com.questionoverflow.qof.domain.user;

import com.questionoverflow.qof.common.auth.dto.JsonWebToken;
import com.questionoverflow.qof.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Column
    private String accessToken;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public Users(String name, String email, String picture, String accessToken ,Role role){
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.accessToken = accessToken;
        this.role = role;
    }

    public Users update(String name, String picture, JsonWebToken tokens){
        this.name = name;
        this.picture = picture;
        this.accessToken = tokens.getAccessToken();

        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}
