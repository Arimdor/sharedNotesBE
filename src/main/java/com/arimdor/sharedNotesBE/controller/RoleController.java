package com.arimdor.sharedNotesBE.controller;

import com.arimdor.sharedNotesBE.entity.Role;
import com.arimdor.sharedNotesBE.repository.RoleRepository;
import com.arimdor.sharedNotesBE.util.ResponseListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
public class RoleController {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleController(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @GetMapping("/roles")
    public ResponseListModel listarRoles(){
        List<Role> role = new ArrayList<>();
        roleRepository.findAll().forEach(role::add);
        ResponseListModel responseListModel = new ResponseListModel(role, "1");
        return role;
    }
}
