package com.arimdor.sharedNotesBE.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "id")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Role> roles;

    User() {
    }

    public User(String username, String password, List<Role> roles) {
        this.email = username;
        this.password = password;
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return email;
    }

    public void setUsername(String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}