package com.footballheadz.ownerservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.footballheadz.ownerservice.model.Owner;
import com.footballheadz.ownerservice.model.Team;
import com.footballheadz.ownerservice.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TeamControllerTest {

    @InjectMocks
    TeamController teamController;

    @MockBean
    TeamService teamService;

    @Autowired
    private MockMvc mockMvc;

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
        owner = new Owner(id,"Orlando", Date.valueOf(LocalDate.now()),true);
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
    void getTeamsShouldReturn200() throws Exception {
        // Arrange
        when(teamService.getTeams()).thenReturn(teamList);

        // Act Assert
        mockMvc.perform(get("/teams"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("\"name\":\"Orlando\"")))
                .andExpect(content().string(containsString("\"name\":\"Tesla\"")))
                .andExpect(content().string(containsString("\"name\":\"Black Panther\"")))
                .andReturn();

        verify(teamService,times(1)).getTeams();
    }

    @Test
    void getTeamsShouldReturn402() throws Exception {
        // Arrange
        teamList.clear();
        when(teamService.getTeams()).thenReturn(teamList);

        // Act Assert
        mockMvc.perform(get("/teams"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("\"message\":\"INCORRECT_REQUEST\"")))
                .andExpect(content().string(containsString("\"details\":[\"No Teams Found\"]")))
                .andReturn();

        verify(teamService,times(1)).getTeams();
    }

    @Test
    void postTeamsShouldReturn201() throws Exception {

        // Act Assert
        mockMvc.perform(
                post("/teams")
                        .content(asJsonString(team1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void postTeamsShouldReturnTeam() throws Exception {
        // Arrange
        Team team = new Team(0,"", Date.valueOf(LocalDate.now()),false, new Owner());
        // Act Assert
        mockMvc.perform(
                post("/teams")
                        .content(asJsonString(team))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("\"message\":\"BAD_REQUEST\"")))
                .andExpect(content().string(containsString("\"details\":[\"Invalid Request\"]")));
    }

    @Test
    void getTeamById() throws Exception {
        // Arrange
        when(teamService.getTeamById(id)).thenReturn(team1);

        // Act Assert a
        mockMvc.perform(get("/teams/{id}", team1.getId())
        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(team1.getId()));

        verify(teamService,times(1)).getTeamById(team1.getId());
    }

    @Test
    void deleteTeam() {
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}