package com.footballheadz.ownerservice.service;


import com.footballheadz.ownerservice.model.Owner;
import com.footballheadz.ownerservice.model.Team;
import com.footballheadz.ownerservice.repository.OwnerRepository;
import com.footballheadz.ownerservice.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
public class TeamService {
    
    @Autowired
    TeamRepository teamRepository;

    @Autowired
    OwnerRepository ownerRepository;


    public Team createTeam(Team team) {
        //new ResponseEntity<Team>(team, HttpStatus.CREATED);
            return teamRepository.save(team);
    }

    public void delete(long id) {
        if (teamRepository.existsById(id)){
            teamRepository.deleteById(id);
        }
    }

    public List<Team> getTeams() {
        return teamRepository.findAll();
    }

    public Team getTeamById(long id) {
        return teamRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Couldn't find a Team with id: " + id));
    }

    public void updateTeam(Team team) {
        if (teamRepository.existsById(team.getId())){
            teamRepository.save(team);
        }
    }
    public List<Team> getTeamsByOwner(Owner owner) {
        List<Team> teams = new ArrayList<>(1);
        if (ownerRepository.existsById(owner.getId())){
            teams = filterListOfTeamsByOwner(owner);
        }
        return teams;
    }
    private List<Team> filterListOfTeamsByOwner(Owner owner) {
        return getTeams().stream().filter(team -> team.getOwner().getId() == owner.getId())
                .collect(Collectors.toList());
    }

}
