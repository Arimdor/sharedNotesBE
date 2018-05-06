package com.nub.mainService.repository;

import com.nub.mainService.entity.Book;
import com.nub.mainService.entity.Note;
import org.springframework.data.repository.CrudRepository;

public interface NoteRepository extends CrudRepository<Note, String> {
    Iterable<Note> findByBook(Book book);
}
