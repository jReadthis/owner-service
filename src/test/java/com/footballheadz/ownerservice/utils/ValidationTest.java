package com.footballheadz.ownerservice.utils;

import com.footballheadz.ownerservice.model.Owner;
import com.footballheadz.ownerservice.model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidationTest {

    @InjectMocks
    Validation validation;

    Team team1, team2;
    Owner owner, owner2;

    @BeforeEach
    void setUp() {
        validation = new Validation();
        owner = new Owner("Orlando",Date.valueOf(LocalDate.now()),true);
        owner2 = new Owner(1,"Juan",Date.valueOf(LocalDate.now()),true);

        team1 = new Team("GimmyDaLoot", Date.valueOf(LocalDate.now()),true, owner);
        team2 = new Team(1,"Tesla", Date.valueOf(LocalDate.now()),false, owner2);
    }

    @Test
    void validateTeamValidTeam() {
        boolean isValid = validation.isValidTeam(team1);
        assertTrue(isValid);
    }

    @Test
    void validateTeamValidTeamWithId() {
        boolean isValid = validation.isValidTeam(team2);
        assertTrue(isValid);
    }

    @Test
    void isValidName() {
        String teamName = "GimmyDaLoot";
        boolean isValid = validation.isValidName(teamName);
        assertTrue(isValid);
    }

    @Test
    void isValidNameEmptyString() {
        String teamName = "";
        boolean isValid = validation.isValidName(teamName);
        assertFalse(isValid);
    }

    @Test
    void isValidNameLongString() {
        StringBuilder teamName = new StringBuilder("TeamName");
        for (int i = 0; i<50; i++){
            teamName.append("T");
        }
        boolean isValid = validation.isValidName(teamName.toString());
        assertFalse(isValid);
    }

    @Test
    void isValidIdLongValue() {
        long id = 19;
        boolean isValid = validation.isValidId(id);
        assertTrue(isValid);
    }

    @Test
    void isValidIdZeroisValid() {
        long id = 0;
        boolean isValid = validation.isValidId(id);
        assertTrue(isValid);
    }

    @Test
    void isValidIdNegativeNumberisNotValid() {
        long id = -5;
        boolean isValid = validation.isValidId(id);
        assertFalse(isValid);
    }

    @Test
    void isValidOwnerWithoutID() {
        boolean isValid = validation.isValidOwner(owner);
        assertTrue(isValid);
    }

    @Test
    void isValidOwnerWithID() {
        boolean isValid = validation.isValidOwner(owner2);
        assertTrue(isValid);
    }
}