package com.arimdor.sharedNotesBE.repository;

import com.arimdor.sharedNotesBE.entity.Note;
import org.springframework.data.repository.CrudRepository;

public interface NoteRepository extends CrudRepository<Note, String> {
}
