package com.nub.mainService.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.None.class)
@Table(name = "users")
public class User {

    @Id
    @Column(updatable = false, nullable = false, unique = true)
    private String token;

    @Column(unique = true)
    private String nickname;

    public User() {
    }

    public User(String token, String nickname) {
        this.token = token;
        this.nickname = nickname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
