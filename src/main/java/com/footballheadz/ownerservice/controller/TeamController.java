package com.footballheadz.ownerservice.controller;

import com.footballheadz.ownerservice.exception.InvalidRequestException;
import com.footballheadz.ownerservice.exception.RecordNotFoundException;
import com.footballheadz.ownerservice.model.Team;
import com.footballheadz.ownerservice.service.TeamService;
import com.footballheadz.ownerservice.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    TeamService teamService;
    @Autowired
    private Validation validation;

    @GetMapping
    public List<Team> getTeams() {
        List<Team> teams = teamService.getTeams();
        if (CollectionUtils.isEmpty(teams)) throw new RecordNotFoundException("No Teams Found");
        return teams;
    }

    @PostMapping
    public ResponseEntity<Team> postTeams(@RequestBody Team team) {
        if (!validation.isValidTeam(team)) throw new InvalidRequestException("Invalid Request");
        return new ResponseEntity<>(teamService.createTeam(team), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Team getById(@PathVariable(required=true) long id) {
        return teamService.getTeamById(id);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(required = true) long id) {
        teamService.delete(id);
    }
}
