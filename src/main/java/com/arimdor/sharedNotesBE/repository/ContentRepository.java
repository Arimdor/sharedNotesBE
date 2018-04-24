package com.arimdor.sharedNotesBE.repository;

import com.arimdor.sharedNotesBE.entity.Content;
import org.springframework.data.repository.CrudRepository;

public interface ContentRepository extends CrudRepository<Content, String> {
}
