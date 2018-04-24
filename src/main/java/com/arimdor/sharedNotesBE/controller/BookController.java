package com.arimdor.sharedNotesBE.controller;

import com.arimdor.sharedNotesBE.repository.BookRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.ok().body(bookRespository.findAll());
    }

}
