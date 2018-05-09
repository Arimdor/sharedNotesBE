package com.nub.mainService.controller;

import com.nub.mainService.MainServiceApplication;
import com.nub.mainService.component.RequestTimeInterceptor;
import com.nub.mainService.entity.Book;
import com.nub.mainService.model.ResponseModel;
import com.nub.mainService.repository.UserRepository;
import com.nub.mainService.service.impl.BookService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RestController
@RequestMapping("api/books")
public class BookController {

    private final BookService bookService;
    private static final Log LOG = LogFactory.getLog(RequestTimeInterceptor.class);
    private final UserRepository userRepository;
    private final MainServiceApplication.PubsubOutboundGateway messagingGateway;

    @Autowired
    public BookController(BookService bookService, MainServiceApplication.PubsubOutboundGateway messagingGateway, UserRepository userRepository) {
        this.bookService = bookService;
        this.messagingGateway = messagingGateway;
        this.userRepository = userRepository;
    }

    // 2: ok; 1: en proceso, 0: error
    @GetMapping("")
    public ResponseEntity<ResponseModel> list() {
        try {
            return ResponseEntity.ok().body(new ResponseModel<>(2, bookService.findAll(), "Se encontro lo solicitado."));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseModel<>(0, null, "Ha ocurrido un error, " + ex.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel> find(@PathVariable("id") String id) {
        try {
            Book book = bookService.find(id).get();
            return ResponseEntity.ok().body(new ResponseModel<>(2, book, "Se encontro lo solicitado."));
        } catch (NoSuchElementException noSuchElementException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel<>(0, null, "No se realizó la operación, no existe un elemento con el ID solicitado."));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseModel<>(0, null, "Ha ocurrido un error, " + ex.getMessage()));
        }
    }

    @PostMapping("")
    public ResponseEntity<ResponseModel> create(@RequestParam("title") String title, @RequestParam("createdBy") String createdBy) {
        try {
            LOG.debug("create book");
            Book book = bookService.createOrUpdate(new Book(title, createdBy, null));
            userRepository.findAll().forEach(user -> {
                LOG.debug(user.getToken());
                messagingGateway.sendToPubsub(user.getToken());
            });
            return ResponseEntity.ok().body(new ResponseModel<>(2, book, "Se creo lo solicitado."));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseModel<>(0, null, "Ha ocurrido un error, " + ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseModel> update(@PathVariable("id") String id, @RequestParam("title") String title) {
        try {
            Book book = bookService.find(id).get();
            book.setTitle(title);
            bookService.createOrUpdate(book);
            return ResponseEntity.ok().body(new ResponseModel<>(2, book, "Se actualizo lo solicitado."));
        } catch (NoSuchElementException noSuchElementException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel<>(0, null, "No se realizó la operación, no existe un elemento con el ID solicitado."));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseModel<>(0, null, "Ha ocurrido un error, " + ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModel> delete(@PathVariable("id") String id) {
        try {
            if (bookService.delete(id)) {
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
