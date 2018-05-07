package com.nub.mainService.service.impl;

import com.nub.mainService.entity.Book;
import com.nub.mainService.entity.Note;
import com.nub.mainService.repository.NoteRepository;
import com.nub.mainService.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NoteService implements CrudService<Note, String> {

    private final NoteRepository noteRepository;
    private final BookService bookService;

    @Autowired
    public NoteService(NoteRepository noteService, BookService bookService) {
        this.noteRepository = noteService;
        this.bookService = bookService;
    }

    @Override
    public Note createOrUpdate(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public Optional<Note> find(String id) {
        return noteRepository.findById(id);
    }

    @Override
    public Iterable<Note> findAll() {
        return noteRepository.findAll();
    }

    @Override
    public boolean delete(String id) {
        noteRepository.deleteById(id);
        return true;
    }

    public Iterable<Note> findAllByNote(Book book) {
        return noteRepository.findByBook(book);
    }
}
