package com.EVBox.challenge.charging;

public class ChargingSummary {

    private static final long serialVersionUID = -3009157732242241606L;

    private int startedCount;
    private int suspendedCount;

    public ChargingSummary() {

    }

    public ChargingSummary(int startedCount, int suspendedCount) {
        this.startedCount = startedCount;
        this.suspendedCount = suspendedCount;
    }

    public int getStartedCount() {
        return startedCount;
    }

    public void setStartedCount(int startedCount) {
        this.startedCount = startedCount;
    }

    public int getSuspendedCount() {
        return suspendedCount;
    }

    public void setSuspendedCount(int suspendedCount) {
        this.suspendedCount = suspendedCount;
    }
}
