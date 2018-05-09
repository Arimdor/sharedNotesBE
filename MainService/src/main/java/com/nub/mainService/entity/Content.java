package com.nub.mainService.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "contents")
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "id")
public class Content implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, unique = true)
    private String id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "note_id", referencedColumnName = "id", nullable = false)
    private Note note;

    @Lob
    @Column(nullable = false, length = 512)
    private String content;

    @Column(nullable = false, updatable = false)
    private String create_by;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private Date updatedAt;

    public Content() {
    }

    public Content(Note note, String content, String create_by) {
        this.note = note;
        this.content = content;
        this.create_by = create_by;
    }

    public Content(String id, Note note, String content, String create_by, Date updatedAt, Date createdAt) {
        this.id = id;
        this.note = note;
        this.content = content;
        this.create_by = create_by;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
