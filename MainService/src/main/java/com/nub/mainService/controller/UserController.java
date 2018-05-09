package com.nub.mainService.controller;

import com.nub.mainService.entity.User;
import com.nub.mainService.model.ResponseModel;
import com.nub.mainService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("")
    public ResponseEntity<ResponseModel> createUser(@RequestParam(value = "token") String token,
                                                    @RequestParam(value = "nickname") String nickname) {
        User user = new User(token, nickname);
        userRepository.save(user);
        return ResponseEntity.ok().body(new ResponseModel<>(2, user, "Se encontro lo solicitado."));
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseModel> find(@PathVariable(value = "token") String token) {
        return ResponseEntity.ok().body(new ResponseModel<>(2, userRepository.findById(token).get(), "Se encontro lo solicitado."));
    }
    @GetMapping("")
    public ResponseEntity<ResponseModel> list() {
        return ResponseEntity.ok().body(new ResponseModel<>(2, userRepository.findAll(), "Se encontro lo solicitado."));
    }
}
