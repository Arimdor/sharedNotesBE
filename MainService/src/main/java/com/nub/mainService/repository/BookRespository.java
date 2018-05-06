package com.nub.mainService.repository;

import com.nub.mainService.entity.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRespository extends CrudRepository<Book, String> {
}
