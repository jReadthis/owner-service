package com.footballheadz.ownerservice.service;

import com.footballheadz.ownerservice.model.Owner;
import com.footballheadz.ownerservice.model.Team;
import com.footballheadz.ownerservice.repository.OwnerRepository;
import com.footballheadz.ownerservice.repository.TeamRepository;
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

class TeamServiceTest {

    @InjectMocks
    TeamService teamService;

    @Mock
    TeamRepository teamRepository;

    @Mock
    OwnerRepository ownerRepository;

    private long id;

    private Team team1;
    private Team team2;
    private Team team3;
    private Owner owner;
    private Owner owner2;
    private List<Team> teamList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        id = 1234;
        owner = new Owner(id,"Orlando",Date.valueOf(LocalDate.now()),true);
        owner2 = new Owner(12,"Juan",Date.valueOf(LocalDate.now()),true);
        teamList = new ArrayList<>();
        team1 = new Team(id,"GimmyDaLoot", Date.valueOf(LocalDate.now()),true, owner);
        team2 = new Team(2,"Tesla", Date.valueOf(LocalDate.now()),false, owner2);
        team3 = new Team(3,"Black Panther", Date.valueOf(LocalDate.now()),true, owner);

        teamList.add(team1);
        teamList.add(team2);
        teamList.add(team3);
    }

    @Test
    void addTeamAndCallServiceWhenTeamDoesNotExist() {
        // Arrange
        when(teamRepository.existsById(id)).thenReturn(false);

        // Act
        teamService.createTeam(team1);

        // Assert
        verify(teamRepository, times(1)).save(team1);
    }

    @Test
    void testAddTeamAndDoNotCallServiceWhenTeamDoesExist() {
        // Arrange
        when(teamRepository.existsById(id)).thenReturn(true);

        // Act
        teamService.createTeam(team1);

        // Assert
        verify(teamRepository, times(0)).save(team1);
    }

    @Test
    void deleteTeamAndCallService() {
        // Arrange
        when(teamRepository.existsById(id)).thenReturn(true);

        // Act
        teamService.delete(id);

        // Assert
        verify(teamRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteTeamWhenTeamDoesNotExist() {
        // Arrange
        when(teamRepository.existsById(id)).thenReturn(false);

        // Act
        teamService.delete(id);

        // Assert
        verify(teamRepository, times(0)).deleteById(id);
    }

    @Test
    void getTeamsAndCallService() {
        //Arrange
        when(teamRepository.findAll()).thenReturn(teamList);

        // Act
        List<Team> actualTeamList = teamService.getTeams();

        // Assert
        assertEquals(3, actualTeamList.size());
        verify(teamRepository, times(1)).findAll();
    }

    @Test
    void getTeamByIdAndCallService() {
        // Arrange
        when(teamRepository.findById(id)).thenReturn(Optional.of(team1));

        // Act
        Team actualTeam = teamService.getTeamById(id);

        // Assert
        assertEquals("GimmyDaLoot", actualTeam.getName());
        assertEquals(Date.valueOf(LocalDate.now()), actualTeam.getActiveSince());
        assertTrue(actualTeam.isActive());
        verify(teamRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateTeamWhenTeamDoesNotExist(){
        // Arrange
        long id = 1234;
        when(teamRepository.existsById(id)).thenReturn(false);

        // Act
        teamService.updateTeam(team1);

        // Assert
        verify(teamRepository, times(0)).save(team1);
    }

    @Test
    void testUpdateTeamWhenTeamDoesExist(){
        // Arrange
        when(teamRepository.existsById(id)).thenReturn(true);

        // Act
        teamService.updateTeam(team1);

        // Assert
        verify(teamRepository, times(1)).save(team1);
    }

    @Test
    void testGetTeamsByOwnerWithExistingOwner(){
        // Arrange
        when(ownerRepository.existsById(id)).thenReturn(true);
        when(teamRepository.findAll()).thenReturn(teamList);

        // Act
        List<Team> actualTeams = teamService.getTeamsByOwner(owner);

        // Assert
        assertEquals(2,actualTeams.size());
        assertEquals("Orlando", actualTeams.get(0).getOwner().getName());
    }

    @Test
    void testGetTeamsByOwnerWithNonExistingOwner(){
        // Arrange
        when(ownerRepository.existsById(id)).thenReturn(false);

        // Act
        List<Team> actualTeams = teamService.getTeamsByOwner(owner);

        // Assert
        assertEquals(0, actualTeams.size());
    }
}