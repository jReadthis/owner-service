package com.footballheadz.ownerservice.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="TEAM")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id", updatable = false, nullable = false)
    private long id;

    private String name;

    private Date activeSince;

    private boolean activeStatus;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    public Team() {
    }

    public Team(long id, String name, Date activeSince, boolean activeStatus, Owner owner) {
        this.id = id;
        this.name = name;
        this.activeSince = activeSince;
        this.activeStatus = activeStatus;
        this.owner = owner;
    }

    public Team(String name, Date activeSince, boolean activeStatus, Owner owner) {
        this.name = name;
        this.activeSince = activeSince;
        this.activeStatus = activeStatus;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getActiveSince() {
        return activeSince;
    }

    public void setActiveSince(Date activeSince) {
        this.activeSince = activeSince;
    }

    public boolean isActive() {
        return activeStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}

