package com.EVBox.challenge.charging;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.UUID;

@JsonIgnoreProperties(value = { "lastModified" })
public class ChargingSession {

    private static final long serialVersionUID = -3009157732242241606L;

    private UUID id;

    private Date startedAt;
    private Date suspendedAt;
    private Date lastModified;
    private Status status;

    public ChargingSession() {

    }

    public ChargingSession(Date startedAt, Status status) {
        this.id = UUID.randomUUID();
        this.startedAt = startedAt;
        this.lastModified = startedAt;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public Date getSuspendedAt() {
        return suspendedAt;
    }

    public void setSuspendedAt(Date suspendedAt) {
        this.suspendedAt = suspendedAt;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
