package com.nub.mainService.controller;

import com.google.cloud.storage.*;
import com.nub.mainService.entity.Content;
import com.nub.mainService.model.ResponseModel;
import com.nub.mainService.service.impl.ContentService;
import com.nub.mainService.service.impl.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@MultipartConfig
@RestController
@RequestMapping("api/contents")
public class ContentController {

    private final ContentService contentService;
    private final NoteService noteService;
    private final Storage storage;

    @Autowired
    public ContentController(ContentService contentService, NoteService noteService) {
        this.contentService = contentService;
        this.noteService = noteService;
        storage = StorageOptions.getDefaultInstance().getService();
    }

    @GetMapping("")
    public ResponseEntity<ResponseModel> listByNote(@RequestParam(value = "noteID") String noteID) {
        try {
            return ResponseEntity.ok().body(new ResponseModel<>(2, contentService.findAllByNote(noteService.find(noteID).get()), "Se encontro lo solicitado."));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseModel<>(0, null, "Ha ocurrido un error, " + ex.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel> findByNote(@PathVariable("id") String id) {
        try {
            Content content = contentService.find(id).get();
            return ResponseEntity.ok().body(new ResponseModel<>(2, content, "Se encontro lo solicitado."));
        } catch (NoSuchElementException noSuchElementException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel<>(0, null, "No se realizó la operación, no existe un elemento con el ID solicitado."));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseModel<>(0, null, "Ha ocurrido un error, " + ex.getMessage()));
        }
    }

    @PostMapping("")
    public ResponseEntity<ResponseModel> createContentText(@RequestParam("id") String id,
                                                           @RequestParam("note_id") String noteID,
                                                           @RequestParam("content") String value,
                                                           @RequestParam("createdBy") String createdBy) {
        try {
            Content content = contentService.createOrUpdate(new Content(id, noteService.find(noteID).get(), value, createdBy));
            return ResponseEntity.ok().body(new ResponseModel<>(2, content, "Se creo lo solicitado."));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseModel<>(0, null, "Ha ocurrido un error, " + ex.getMessage()));
        }
    }

    @PostMapping("multimedia")
    public ResponseEntity<ResponseModel> createContentImage(@RequestParam("id") String id,
                                                            @RequestParam("note_id") String noteID,
                                                            @RequestParam("image") MultipartFile image,
                                                            @RequestParam("createdBy") String createdBy) {
        try {
            if (contentService.find(id).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseModel<>(0, null, "Ya existe un elemento con el mismo id"));
            } else {
                String fileName = image.getOriginalFilename();
                List<Acl> acls = new ArrayList<>();
                acls.add(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
                Blob blob = storage.create(BlobInfo.newBuilder("sharednotes_photos", fileName).setContentType(image.getContentType()).setAcl(acls).build(), image.getBytes());

                Content content = contentService.createOrUpdate(new Content(id, noteService.find(noteID).get(), blob.getMediaLink(), createdBy));
                return ResponseEntity.ok().body(new ResponseModel<>(2, content, "Se creo lo solicitado."));
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseModel<>(0, null, "Ha ocurrido un error, " + ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseModel> update(@PathVariable("id") String id, @RequestParam("title") String title) {
        try {
            Content content = contentService.find(id).get();
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
