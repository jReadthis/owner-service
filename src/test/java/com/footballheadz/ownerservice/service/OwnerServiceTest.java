package com.footballheadz.ownerservice.service;

import com.footballheadz.ownerservice.model.Owner;
import com.footballheadz.ownerservice.repository.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


class OwnerServiceTest {

    @InjectMocks
    OwnerService ownerService;

    @Mock
    OwnerRepository ownerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addOwnerAndCallService() {
        // Arrange
        Owner owner = new Owner(1,"Orlando", Date.valueOf(LocalDate.now()),true);

        // Act
        ownerService.add(owner);

        // Assert
        verify(ownerRepository, times(1)).save(owner);
    }

    @Test
    void deleteOwnerAndCallService() {
        // Arrange
        long id = 1234;

        // Act
        ownerService.delete(id);

        // Assert
        verify(ownerRepository, times(1)).deleteById(id);
    }

    @Test
    void getOwnersAndCallService() {
        //Arrange
        List<Owner> ownerList = new ArrayList<>();
        Owner owner1 = new Owner(1,"Orlando", Date.valueOf(LocalDate.now()),true);
        Owner owner2 = new Owner(2,"Juan", Date.valueOf(LocalDate.now()),false);
        Owner owner3 = new Owner(3,"John", Date.valueOf(LocalDate.now()),true);

        ownerList.add(owner1);
        ownerList.add(owner2);
        ownerList.add(owner3);
        when(ownerRepository.findAll()).thenReturn(ownerList);

        // Act
        ownerService.getOwners();

        // Assert
        assertEquals(3, ownerList.size());
        verify(ownerRepository, times(1)).findAll();
    }

    @Test
    void getOwnerByIdAndCallService() {
        // Arrange
        long id = 4321;
        Owner owner = new Owner(1,"Orlando", Date.valueOf(LocalDate.now()),true);
        when(ownerRepository.findById(id)).thenReturn(Optional.of(owner));

        // Act
        Owner actualOwner = ownerService.getOwnerById(id);

        // Assert
        assertEquals("Orlando", actualOwner.getName());
        assertEquals(Date.valueOf(LocalDate.now()), actualOwner.getActiveSince());
        assertTrue(owner.isActive());
        verify(ownerRepository, times(1)).findById(id);
    }
}