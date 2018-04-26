package com.arimdor.sharedNotesBE.controller;

import com.arimdor.sharedNotesBE.entity.Role;
import com.arimdor.sharedNotesBE.repository.RoleRepository;
import com.arimdor.sharedNotesBE.util.ResponseListModel;
import com.arimdor.sharedNotesBE.util.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
public class RoleController {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping("/roles")
    public ResponseListModel listarRoles() {
        List<Role> role = new ArrayList<>();
        roleRepository.findAll().forEach(role::add);
        return new ResponseListModel(role, "1");
    }
    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    public ResponseModel createRole(@RequestParam("name") String name, @RequestParam("description") String description){
        Role role = new Role();
        role.setName(name);
        role.setDescription(description);
        roleRepository.save(role);
        return new ResponseModel(role, "1");
    }

    @RequestMapping(value = "/roles/{id}", method = RequestMethod.PUT)
    public ResponseModel updateRole(@PathVariable String id, @RequestParam("name") String name, @RequestParam("description") String description){
        Role role;
        role = roleRepository.findById(id).get();
        role.setName(name);
        role.setDescription(description);
        roleRepository.save(role);
        return new ResponseModel(role, "1");
    }
    @RequestMapping(value = "/roles/{id}", method = RequestMethod.DELETE)
    public ResponseModel deleteRole(@PathVariable String id) {
        roleRepository.deleteById(id);
        return new ResponseModel(null, "1");
    }


}
