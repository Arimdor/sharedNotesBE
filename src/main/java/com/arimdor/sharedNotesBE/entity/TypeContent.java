package com.arimdor.sharedNotesBE.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "Type_Content")
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "id")
public class TypeContent {
    @Id
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, unique = true)
    private String id;

    @Column(nullable = false)
    private Timestamp name;

    @Column
    private Timestamp description;

    public TypeContent() {
    }

    public TypeContent(String id, Timestamp name, Timestamp description) {
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
