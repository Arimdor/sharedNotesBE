package com.arimdor.sharedNotesBE.controller;

import com.arimdor.sharedNotesBE.entity.Book;
import com.arimdor.sharedNotesBE.repository.BookRespository;
import com.arimdor.sharedNotesBE.util.ResponseListModel;
import com.arimdor.sharedNotesBE.util.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
public class BookController {

    private final BookRespository bookRespository;

    @Autowired
    public BookController(BookRespository bookRespository) {
        this.bookRespository = bookRespository;
    }

    @GetMapping("/books")
    public ResponseEntity<?> listBooks() {
        List<Book> books = new ArrayList<>();
        bookRespository.findAll().forEach(books::add);
        return ResponseEntity.ok().body(new ResponseListModel(books, "OK"));
    }

    @PostMapping("/books")
    public ResponseEntity<?> createBook(@RequestParam("title") String title) {
        Book book = bookRespository.save(new Book(title, null));
        return ResponseEntity.ok().body(new ResponseModel(book, "Se creo el book correctamente"));
    }

}
