package com.footballheadz.ownerservice.controller;


import com.footballheadz.ownerservice.model.Owner;
import com.footballheadz.ownerservice.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    @Autowired
    OwnerService ownerService;

    @GetMapping
    public List<Owner> getOwners() {
        return ownerService.getOwners();
    }
    @PostMapping
    public void postOwners(@RequestBody Owner owner) {
        ownerService.add(owner);
    }
    @GetMapping("/{id}")
    public Owner getById(@PathVariable(required = true) long id) {
        return ownerService.getOwnerById(id);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(required = true) long id) {
        ownerService.delete(id);
    }
    @PutMapping("/{id}")
    public void update(@PathVariable(required = true) long id, @RequestBody Owner owner) {
        Owner retrievedOwner = ownerService.getOwnerById(id);
        if (retrievedOwner != null){
            System.out.println(owner.getId());
            retrievedOwner.setId(owner.getId());
        }
    }
}
