package com.nub.mainService.repository;

import com.nub.mainService.entity.Content;
import com.nub.mainService.entity.Note;
import org.springframework.data.repository.CrudRepository;

public interface ContentRepository extends CrudRepository<Content, String> {
    Iterable<Content> findByNote(Note note);
}
