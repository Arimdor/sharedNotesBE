package com.nub.mainService.controller;

import com.nub.mainService.entity.Note;
import com.nub.mainService.model.ResponseModel;
import com.nub.mainService.service.impl.BookService;
import com.nub.mainService.service.impl.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/notes")
public class NoteController {

    private final NoteService noteService;
    private final BookService bookService;

    @Autowired
    public NoteController(NoteService noteService, BookService bookService) {
        this.noteService = noteService;
        this.bookService = bookService;
    }

    // 2: ok; 1: en proceso, 0: error
    @GetMapping("")
    public ResponseEntity<ResponseModel> listByBookID(@RequestParam(value = "bookID") String bookID) {
        try {
            return ResponseEntity.ok().body(new ResponseModel<>(2, noteService.findAllByNote(bookService.find(bookID).get()), "Se encontro lo solicitado gg."));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel<>(0, null, "No existe un elemento con el ID solicitado."));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseModel<>(0, null, "Ha ocurrido un error, " + ex.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel> find(@PathVariable("id") String id) {
        try {
            Note note = noteService.find(id).get();
            return ResponseEntity.ok().body(new ResponseModel<>(2, note, "Se encontro lo solicitado."));
        } catch (NoSuchElementException noSuchElementException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel<>(0, null, "No se realizó la operación, no existe un elemento con el ID solicitado."));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseModel<>(0, null, "Ha ocurrido un error, " + ex.getMessage()));
        }
    }

    @PostMapping("")
    public ResponseEntity<ResponseModel> create(@RequestParam("title") String title,
                                                @RequestParam("book_id") String bookID,
                                                @RequestParam("createdBy") String createdBy) {
        try {
            Note note = noteService.createOrUpdate(new Note(title, bookService.find(bookID).get(), null, createdBy));
            return ResponseEntity.ok().body(new ResponseModel<>(2, note, "Se creo lo solicitado."));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseModel<>(0, null, "Ha ocurrido un error, " + ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseModel> update(@PathVariable("id") String id, @RequestParam("title") String title) {
        try {
            Note note = noteService.find(id).get();
            note.setTitle(title);
            noteService.createOrUpdate(note);
            return ResponseEntity.ok().body(new ResponseModel<>(2, note, "Se actualizo lo solicitado."));
        } catch (NoSuchElementException noSuchElementException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel<>(0, null, "No existe un elemento con el ID solicitado."));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseModel<>(0, null, "Ha ocurrido un error, " + ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModel> delete(@PathVariable("id") String id) {
        try {
            if (noteService.delete(id)) {
                return ResponseEntity.ok().body(new ResponseModel<>(2, null, "Se elimino lo solicitado."));
            } else {
                throw new Exception("No se pudo eliminar lo solicitado.");
            }
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel<>(0, null, "No existe un elemento con el ID solicitado."));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseModel<>(0, null, "Ha ocurrido un error, " + ex.getMessage()));
        }
    }

}
