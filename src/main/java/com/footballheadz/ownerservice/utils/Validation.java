package com.footballheadz.ownerservice.utils;

import com.footballheadz.ownerservice.model.Owner;
import com.footballheadz.ownerservice.model.Team;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class Validation {

    public boolean isValidTeam(Team team) {
        if (!isValidId(team.getId()) || !isValidName(team.getName())
            || !isValidOwner(team.getOwner())){
            return false;
        }
        return true;
    }

    public boolean isValidName(String name) {
        name.trim();
        if (StringUtils.isEmpty(name) || name.length() > 50) {
            return false;
        }
        return true;
    }

    public boolean isValidId(long id) {
        if (id < 0) return false;
        return true;
    }

    public boolean isValidOwner(Owner owner) {
        if (!isValidId(owner.getId()) || !isValidName(owner.getName())){
            return false;
        }
        return true;
    }
}
