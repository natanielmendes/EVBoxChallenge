package com.EVBox.challenge.charging;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.LinkedList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChargingSessionTest {

    @Autowired
    private ChargingSessionService chargingSessionService;

    @Test
    public void saveChargingSession() throws Exception {
        ChargingSession chargingSession = new ChargingSession();
        chargingSession.setStartedAt(new Date());

        ChargingSession createdChargingSession = chargingSessionService.addChargingSession(chargingSession);
        assertThat(createdChargingSession.getStartedAt(), equalTo(chargingSession.getStartedAt()));
        assertThat(createdChargingSession.getLastModified(), equalTo(chargingSession.getStartedAt()));
        assertThat(createdChargingSession.getStatus(), equalTo(Status.STARTED));
        assertThat(createdChargingSession.getSuspendedAt(), equalTo(null));
    }

    @Test
    public void getChargingSessionById() throws Exception {
        ChargingSession chargingSession = new ChargingSession();
        chargingSession.setStartedAt(new Date());
        ChargingSession createdChargingSession = chargingSessionService.addChargingSession(chargingSession);

        ChargingSession retrievedChargingSession = chargingSessionService.getChargingSession(createdChargingSession.getId());

        assertThat(retrievedChargingSession.getStartedAt(), equalTo(createdChargingSession.getStartedAt()));
        assertThat(retrievedChargingSession.getLastModified(), equalTo(createdChargingSession.getLastModified()));
        assertThat(retrievedChargingSession.getStatus(), equalTo(createdChargingSession.getStatus()));
        assertThat(retrievedChargingSession.getSuspendedAt(), equalTo(createdChargingSession.getSuspendedAt()));
        assertThat(retrievedChargingSession.getId(), equalTo(createdChargingSession.getId()));
    }

    @Test
    public void suspendChargingSession() throws Exception {
        // Create charging session
        ChargingSession chargingSession = new ChargingSession();
        chargingSession.setStartedAt(new Date(System.currentTimeMillis() - 60 * 1000)); // one minute ago
        ChargingSession createdChargingSession = chargingSessionService.addChargingSession(chargingSession);

        // Suspending charging session created above
        ChargingSession chargingSessionSuspendRequest = new ChargingSession();
        chargingSessionSuspendRequest.setSuspendedAt(new Date());
        ChargingSession retrievedChargingSession = chargingSessionService.suspendChargingSession(createdChargingSession.getId(), chargingSessionSuspendRequest);

        assertThat(retrievedChargingSession.getStartedAt(), equalTo(createdChargingSession.getStartedAt()));
        assertNotEquals(retrievedChargingSession.getLastModified(), createdChargingSession.getStartedAt());
        assertThat(retrievedChargingSession.getLastModified(), equalTo(retrievedChargingSession.getSuspendedAt()));
        assertThat(retrievedChargingSession.getStatus(), equalTo(Status.SUSPENDED));
        assertThat(retrievedChargingSession.getSuspendedAt(), equalTo(createdChargingSession.getSuspendedAt()));
        assertThat(retrievedChargingSession.getId(), equalTo(createdChargingSession.getId()));
    }

    @Test
    public void getChargingSessionSummary() throws Exception {
        chargingSessionService.chargingSessions = new LinkedList<>();

        // Create charging session 30 seconds ago
        ChargingSession chargingSession30SecondsAgo = new ChargingSession();
        chargingSession30SecondsAgo.setStartedAt(new Date(System.currentTimeMillis() - 30 * 1000)); // 30 seconds ago
        ChargingSession createdChargingSession30SecondsAgo = chargingSessionService.addChargingSession(chargingSession30SecondsAgo);

        // Create charging session 20 seconds ago
        ChargingSession chargingSession20SecondsAgo = new ChargingSession();
        chargingSession20SecondsAgo.setStartedAt(new Date(System.currentTimeMillis() - 20 * 1000)); // 20 seconds ago
        ChargingSession createdChargingSession20SecondsAgo = chargingSessionService.addChargingSession(chargingSession20SecondsAgo);

        // Create charging session 2 minutes ago
        ChargingSession chargingSession2MinutesAgo = new ChargingSession();
        chargingSession2MinutesAgo.setStartedAt(new Date(System.currentTimeMillis() - 120 * 1000)); // 2 minutes ago
        ChargingSession createdChargingSession2MinutesAgo = chargingSessionService.addChargingSession(chargingSession2MinutesAgo);


        ChargingSummary chargingSummaryBeforeSuspending = chargingSessionService.getChargingSummary();
        assertThat(chargingSummaryBeforeSuspending.getStartedCount(), equalTo(2));
        assertThat(chargingSummaryBeforeSuspending.getSuspendedCount(), equalTo(0));

        // Suspending charging session from "30 seconds ago"
        ChargingSession chargingSessionSuspendRequest = new ChargingSession();
        chargingSessionSuspendRequest.setSuspendedAt(new Date(System.currentTimeMillis() - 20 * 1000));
        ChargingSession suspendedChargingSessionCreated30SecondsAgo = chargingSessionService.suspendChargingSession(createdChargingSession30SecondsAgo.getId(), chargingSessionSuspendRequest);

        ChargingSummary chargingSummaryAfterSuspending = chargingSessionService.getChargingSummary();
        assertThat(chargingSummaryAfterSuspending.getStartedCount(), equalTo(1));
        assertThat(chargingSummaryAfterSuspending.getSuspendedCount(), equalTo(1));
    }

    @Test
    public void getChargingSessionSummaryWithSuspendDateEqualsToNow() throws Exception {
        chargingSessionService.chargingSessions = new LinkedList<>();

        // Create charging session 30 seconds ago
        ChargingSession chargingSession30SecondsAgo = new ChargingSession();
        chargingSession30SecondsAgo.setStartedAt(new Date(System.currentTimeMillis() - 30 * 1000)); // 30 seconds ago
        ChargingSession createdChargingSession30SecondsAgo = chargingSessionService.addChargingSession(chargingSession30SecondsAgo);

        // Create charging session 20 seconds ago
        ChargingSession chargingSession20SecondsAgo = new ChargingSession();
        chargingSession20SecondsAgo.setStartedAt(new Date(System.currentTimeMillis() - 20 * 1000)); // 20 seconds ago
        ChargingSession createdChargingSession20SecondsAgo = chargingSessionService.addChargingSession(chargingSession20SecondsAgo);

        // Create charging session 2 minutes ago
        ChargingSession chargingSession2MinutesAgo = new ChargingSession();
        chargingSession2MinutesAgo.setStartedAt(new Date(System.currentTimeMillis() - 120 * 1000)); // 2 minutes ago
        ChargingSession createdChargingSession2MinutesAgo = chargingSessionService.addChargingSession(chargingSession2MinutesAgo);


        ChargingSummary chargingSummaryBeforeSuspending = chargingSessionService.getChargingSummary();
        assertThat(chargingSummaryBeforeSuspending.getStartedCount(), equalTo(2));
        assertThat(chargingSummaryBeforeSuspending.getSuspendedCount(), equalTo(0));

        // Suspending charging session from "30 seconds ago"
        ChargingSession chargingSessionSuspendRequest = new ChargingSession();
        chargingSessionSuspendRequest.setSuspendedAt(new Date());
        ChargingSession suspendedChargingSessionCreated30SecondsAgo = chargingSessionService.suspendChargingSession(createdChargingSession30SecondsAgo.getId(), chargingSessionSuspendRequest);

        ChargingSummary chargingSummaryAfterSuspending = chargingSessionService.getChargingSummary();
        assertThat(chargingSummaryAfterSuspending.getStartedCount(), equalTo(1));
        assertThat(chargingSummaryAfterSuspending.getSuspendedCount(), equalTo(1));
    }
}
