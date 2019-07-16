package com.EVBox.challenge.charging;

import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class ChargingSessionService {

    public List<ChargingSession> chargingSessions = Collections.synchronizedList(new LinkedList());

    public List<ChargingSession> getAllChargingSessions() {
        return chargingSessions;
    }

    public ChargingSession addChargingSession(ChargingSession chargingSessionRequest) {
        ChargingSession chargingSession = new ChargingSession(chargingSessionRequest.getStartedAt(), Status.STARTED);
        chargingSessions.add(chargingSession);

        Collections.sort(chargingSessions, Comparator.comparing(s -> s.getLastModified()));
        return chargingSession;
    }

    public ChargingSession suspendChargingSession(UUID id, ChargingSession chargingSessionRequest) {
        int chargingSessionIndex = findChargingSessionIndexByID(id);

        ChargingSession chargingSessionToUpdate = new ChargingSession();
        if (chargingSessionIndex != -1) {
            chargingSessionToUpdate = chargingSessions.get(chargingSessionIndex);
            chargingSessionToUpdate.setSuspendedAt(chargingSessionRequest.getSuspendedAt());
            chargingSessionToUpdate.setLastModified(chargingSessionRequest.getSuspendedAt());
            chargingSessionToUpdate.setStatus(Status.SUSPENDED);
            chargingSessions.set(chargingSessionIndex, chargingSessionToUpdate);
        }

        Collections.sort(chargingSessions, Comparator.comparing(s -> s.getLastModified()));
        return chargingSessionToUpdate;
    }

    public ChargingSession getChargingSession(UUID id) {
        return findChargingSessionByID(id);
    }

    public ChargingSummary getChargingSummary() {
        ChargingSummary chargingSummary = new ChargingSummary(0,0);

        Date now = new Date(System.currentTimeMillis());
        Date oneMinuteAgo = new Date(System.currentTimeMillis() - 60 * 1000);

        if (chargingSessions.isEmpty()) {
            return chargingSummary;
        }

        for (int i = chargingSessions.size(); i-- > 0; ) {
            if ((chargingSessions.get(i).getLastModified().before(now) || chargingSessions.get(i).getLastModified().equals(now))
                    && (chargingSessions.get(i).getLastModified().after(oneMinuteAgo) || chargingSessions.get(i).getLastModified().equals(now))) {
                if (chargingSessions.get(i).getStatus().equals(Status.SUSPENDED)) {
                    chargingSummary.setSuspendedCount(chargingSummary.getSuspendedCount() + 1);
                } else if (chargingSessions.get(i).getStatus().equals(Status.STARTED)) {
                    chargingSummary.setStartedCount(chargingSummary.getStartedCount() + 1);
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
        if (chargingSessions.isEmpty()) {
            return -1;
        }

        for (int i = chargingSessions.size(); i-- > 0; ) {
            if (chargingSessions.get(i).getId().equals(uuid)) {
                return i;
            }
        }

        return -1;
    }




}
