package com.arimdor.sharedNotesBE.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "id")
public class Role implements Serializable {

    @Id
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, unique = true)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    public Role() {
    }

    public Role(String id, Timestamp name, Timestamp description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getName() {
        return name;
    }

    public void setName(Timestamp name) {
        this.name = name;
    }

    public Timestamp getDescription() {
        return description;
    }

    public void setDescription(Timestamp description) {
        this.description = description;
    }
}
