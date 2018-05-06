package com.nub.mainService.service.impl;

import com.nub.mainService.entity.Book;
import com.nub.mainService.repository.BookRespository;
import com.nub.mainService.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService implements CrudService<Book, String> {

    private final BookRespository bookRespository;

    @Autowired
    public BookService(BookRespository bookRespository) {
        this.bookRespository = bookRespository;
    }

    @Override
    public Book createOrUpdate(Book book) {
        return bookRespository.save(book);
    }

    @Override
    public Book find(String id) {
        return bookRespository.findById(id).get();
    }

    @Override
    public Iterable<Book> findAll() {
        return bookRespository.findAll();
    }

    @Override
    public boolean delete(String id) {
        bookRespository.deleteById(id);
        return true;
    }
}
