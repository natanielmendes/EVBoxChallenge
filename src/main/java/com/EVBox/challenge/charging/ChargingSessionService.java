package com.EVBox.challenge.charging;

import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class ChargingSessionService {

    static LinkedList<ChargingSession> chargingSessions = new LinkedList<ChargingSession>();

    public LinkedList<ChargingSession> getAllChargingSessions() {
        return chargingSessions;
    }

    public ChargingSession getChargingSession(UUID id) {
        return findChargingSessionByID(id);
    }

    public ChargingSession addChargingSession(ChargingSession chargingSessionRequest) {
        ChargingSession chargingSession = new ChargingSession(chargingSessionRequest.getStartedAt(), Status.STARTED);
        chargingSessions.addLast(chargingSession);

        Collections.sort(chargingSessions, Comparator.comparing(s -> s.getLastModified()));
        return chargingSession;
    }

    public void suspendChargingSession(UUID id, ChargingSession chargingSessionRequest) {
        int chargingSessionIndex = findChargingSessionIndexByID(id);

        if (chargingSessionIndex != -1) {
            ChargingSession chargingSessionToUpdate = chargingSessions.get(chargingSessionIndex);
            chargingSessionToUpdate.setSuspendedAt(chargingSessionRequest.getSuspendedAt());
            chargingSessionToUpdate.setLastModified(chargingSessionRequest.getSuspendedAt());
            chargingSessionToUpdate.setStatus(Status.SUSPENDED);
            chargingSessions.set(chargingSessionIndex, chargingSessionToUpdate);
        }

        Collections.sort(chargingSessions, Comparator.comparing(s -> s.getLastModified()));
    }

    public ChargingSummary getChargingSummary() {
        ChargingSummary chargingSummary = new ChargingSummary(0,0);

        Date now = new Date(System.currentTimeMillis());
        Date oneMinuteAgo = new Date(System.currentTimeMillis() - 60 * 1000);

        if (chargingSessions.isEmpty()) {
            return chargingSummary;
        }

        for (int i = chargingSessions.size(); i-- > 0; ) {
            if (chargingSessions.get(i).getLastModified().before(now) && chargingSessions.get(i).getLastModified().after(oneMinuteAgo)) {
                if (chargingSessions.get(i).getStatus().equals(Status.STARTED)) {
                    chargingSummary.setStartedCount(chargingSummary.getStartedCount() + 1);
                } else if (chargingSessions.get(i).getStatus().equals(Status.SUSPENDED)) {
                    chargingSummary.setStartedCount(chargingSummary.getSuspendedCount() + 1);
                }
            }
        }

        return chargingSummary;
    }

    public ChargingSession findChargingSessionByID (UUID uuid) {
        ChargingSession chargingSession = null;


        if (chargingSessions.isEmpty()) {
            return chargingSession;
        }

        for (int i = chargingSessions.size(); i-- > 0; ) {
            if (chargingSessions.get(i).getId().equals(uuid)) {
                chargingSession = chargingSessions.get(i);
                break;
            }
        }

        return chargingSession;
    }

    public int findChargingSessionIndexByID (UUID uuid) {
        System.out.println("aqui3");
        if (chargingSessions.isEmpty()) {
            return -1;
        }

        for (int i = chargingSessions.size(); i-- > 0; ) {
            System.out.println(chargingSessions.get(i).getId());
            System.out.println(uuid);
            if (chargingSessions.get(i).getId().equals(uuid)) {
                System.out.println("eita");
                return i;
            }
        }

        return -1;
    }




}
