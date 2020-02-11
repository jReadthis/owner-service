package com.footballheadz.ownerservice.service;

import com.footballheadz.ownerservice.model.Owner;
import com.footballheadz.ownerservice.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class OwnerService {

    @Autowired
    OwnerRepository ownerRepository;

    public void add(Owner owner) {
        ownerRepository.save(owner);
    }
    public void delete(long id) {
        ownerRepository.deleteById(id);
    }
    public List<Owner> getOwners() {
        return (List<Owner>) ownerRepository.findAll();
    }
    public Owner getOwnerById(long id) {
        Optional<Owner> optionalOwner = ownerRepository.findById(id);
        return optionalOwner.orElseThrow(() -> new NoSuchElementException("Couldn't find a Owner with id: " + id));
    }
}
