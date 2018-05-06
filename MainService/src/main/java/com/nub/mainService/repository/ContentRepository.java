package com.nub.mainService.repository;

import com.nub.mainService.entity.Content;
import org.springframework.data.repository.CrudRepository;

public interface ContentRepository extends CrudRepository<Content, String> {
    Iterable<Content> findByNote(String noteID);
}
