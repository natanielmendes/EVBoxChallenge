package com.EVBox.challenge.charging;

public enum Status {
    STARTED("STARTED"), SUSPENDED("SUSPENDED");

    Status(String statusName) {
        this.statusName = statusName;
    }

    private final String statusName;

    public String getStatusName() {
        return statusName;
    }
}
