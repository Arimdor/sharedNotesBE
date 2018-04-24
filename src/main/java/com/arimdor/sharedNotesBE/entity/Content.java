package com.arimdor.sharedNotesBE.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table
public class Content implements Serializable {

    @Id
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, unique = true)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id", referencedColumnName = "id", nullable = false)
    private Note note;

    @Column(nullable = false, updatable = false)
    private String create_by;

    @Column(nullable = false)
    private String content;

    @Column
    @UpdateTimestamp
    private Timestamp updated_at;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp created_at;

    public Content() {
    }

    public Content(String id, Note note, String create_by, String content, Timestamp updated_at, Timestamp created_at) {
        this.id = id;
        this.note = note;
        this.create_by = create_by;
        this.content = content;
        this.updated_at = updated_at;
        this.created_at = created_at;
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

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
