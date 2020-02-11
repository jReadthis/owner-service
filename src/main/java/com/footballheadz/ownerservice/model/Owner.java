package com.footballheadz.ownerservice.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="OWNER")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id", updatable = false, nullable = false)
    private long id;

    private String name;

    private Date activeSince;

    private boolean activeStatus;

    public Owner() {
    }

    public Owner(long id, String name, Date activeSince, boolean activeStatus) {
        this.id = id;
        this.name = name;
        this.activeSince = activeSince;
        this.activeStatus = activeStatus;
    }
    public Owner(String name, Date activeSince, boolean activeStatus) {
        this.name = name;
        this.activeSince = activeSince;
        this.activeStatus = activeStatus;
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
}
