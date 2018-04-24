package com.arimdor.sharedNotesBE.controller;

import com.arimdor.sharedNotesBE.entity.Book;
import com.arimdor.sharedNotesBE.repository.BookRespository;
import com.arimdor.sharedNotesBE.util.ResponseListModel;
import com.arimdor.sharedNotesBE.util.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api")
public class BookController {

    private final BookRespository bookRespository;

    @Autowired
    public BookController(BookRespository bookRespository) {
        this.bookRespository = bookRespository;
    }

    @GetMapping("/books")
    public ResponseEntity<ResponseListModel> listBooks() {
        try {
            List<Book> books = new ArrayList<>();
            bookRespository.findAll().forEach(books::add);
            return ResponseEntity.ok().body(new ResponseListModel(books, "Se encontro lo solicitado."));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseListModel(null, "Ha ocurrido un error, " + ex.getMessage()));
        }
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<ResponseModel> findBook(@PathVariable("id") String id) {
        try {
            Book book = bookRespository.findById(id).get();
            return ResponseEntity.ok().body(new ResponseModel(book, "Se encontro lo solicitado."));
        } catch (NoSuchElementException noSuchElementException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel(null, "No se realizó la operación, no existe un elemento con el ID solicitado."));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseModel(null, "Ha ocurrido un error, " + ex.getMessage()));
        }
    }

    @PostMapping("/books")
    public ResponseEntity<ResponseModel> createBook(@RequestParam("title") String title, @RequestParam("createdBy") String createdBy) {
        try {
            Book book = bookRespository.save(new Book(title, createdBy, null));
            return ResponseEntity.ok().body(new ResponseModel(book, "Se creo lo solicitado."));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseModel(null, "Ha ocurrido un error, " + ex.getMessage()));
        }
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<ResponseModel> updateBook(@PathVariable("id") String id, @RequestParam("title") String title) {
        try {
            Book book = bookRespository.findById(id).get();
            book.setTitle(title);
            bookRespository.save(book);
            return ResponseEntity.ok().body(new ResponseModel(book, "Se actualizo lo solicitado."));
        } catch (NoSuchElementException noSuchElementException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel(null, "No se realizó la operación, no existe un elemento con el ID solicitado."));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseModel(null, "Ha ocurrido un error, " + ex.getMessage()));
        }
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<ResponseModel> deleteeBook(@PathVariable("id") String id) {
        try {
            bookRespository.deleteById(id);
            return ResponseEntity.ok().body(new ResponseModel(null, "Se elimino lo solicitado."));
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel(null, "No se realizó la operación, no existe un elemento con el ID solicitado."));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseModel(null, "Ha ocurrido un error, " + ex.getMessage()));
        }
    }
}
