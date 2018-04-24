package com.arimdor.sharedNotesBE.repository;

import com.arimdor.sharedNotesBE.entity.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRespository extends CrudRepository<Book, String> {
}
