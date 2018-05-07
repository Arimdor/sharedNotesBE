package com.nub.mainService.service.impl;

import com.nub.mainService.entity.Content;
import com.nub.mainService.entity.Note;
import com.nub.mainService.repository.ContentRepository;
import com.nub.mainService.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContentService implements CrudService<Content, String> {

    private final ContentRepository contentRepository;

    @Autowired
    public ContentService(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    @Override
    public Content createOrUpdate(Content content) {
        return contentRepository.save(content);
    }

    @Override
    public Optional<Content> find(String id) {
        return contentRepository.findById(id);
    }

    @Override
    public Iterable<Content> findAll() {
        return contentRepository.findAll();
    }

    @Override
    public boolean delete(String id) {
        contentRepository.deleteById(id);
        return true;
    }

    public Iterable<Content> findAllByNote(Note note) {
        return contentRepository.findByNote(note);
    }
}
