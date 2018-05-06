package com.nub.mainService.controller;

import com.nub.mainService.entity.Content;
import com.nub.mainService.model.ResponseModel;
import com.nub.mainService.service.impl.ContentService;
import com.nub.mainService.service.impl.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/contents")
public class ContentController {

    private final ContentService contentService;
    private final NoteService noteService;

    @Autowired
    public ContentController(ContentService contentService, NoteService noteService) {
        this.contentService = contentService;
        this.noteService = noteService;
    }

    @GetMapping("")
    public ResponseEntity<ResponseModel> listByNote() {
        try {
            return ResponseEntity.ok().body(new ResponseModel<>(2, contentService.findAll(), "Se encontro lo solicitado."));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseModel<>(0, null, "Ha ocurrido un error, " + ex.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel> findByNote(@PathVariable("id") String id) {
        try {
            Content content = contentService.find(id);
            return ResponseEntity.ok().body(new ResponseModel<>(2, content, "Se encontro lo solicitado."));
        } catch (NoSuchElementException noSuchElementException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel<>(0, null, "No se realizó la operación, no existe un elemento con el ID solicitado."));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseModel<>(0, null, "Ha ocurrido un error, " + ex.getMessage()));
        }
    }

    @PostMapping("")
    public ResponseEntity<ResponseModel> create(@RequestParam("id") String id,
                                                @RequestParam("note_id") String noteID,
                                                @RequestParam("content") String value,
                                                @RequestParam("createdBy") String createdBy) {
        try {
            Content content = contentService.createOrUpdate(new Content(id, noteService.find(noteID), value, createdBy));
            return ResponseEntity.ok().body(new ResponseModel<>(2, content, "Se creo lo solicitado."));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseModel<>(0, null, "Ha ocurrido un error, " + ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseModel> update(@PathVariable("id") String id, @RequestParam("title") String title) {
        try {
            Content content = contentService.find(id);
            content.setContent(title);
            contentService.createOrUpdate(content);
            return ResponseEntity.ok().body(new ResponseModel<>(2, content, "Se actualizo lo solicitado."));
        } catch (NoSuchElementException noSuchElementException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel<>(0, null, "No se realizó la operación, no existe un elemento con el ID solicitado."));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseModel<>(0, null, "Ha ocurrido un error, " + ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModel> delete(@PathVariable("id") String id) {
        try {
            if (contentService.delete(id)) {
                return ResponseEntity.ok().body(new ResponseModel<>(2, null, "Se elimino lo solicitado."));
            } else {
                throw new Exception("No se pudo eliminar lo solicitado.");
            }
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel<>(0, null, "No se realizó la operación, no existe un elemento con el ID solicitado."));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseModel<>(0, null, "Ha ocurrido un error, " + ex.getMessage()));
        }
    }
}
