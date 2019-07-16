//package com.EVBox.challenge;
//
//import static org.hamcrest.Matchers.*;
//import static org.junit.Assert.*;
//
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import com.EVBox.challenge.charging.ChargingSession;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class ChargingSessionControllerIT {
//
//    @LocalServerPort
//    private int port;
//
//    private URL base;
//
//    @Autowired
//    private TestRestTemplate template;
//
//    @Before
//    public void setUp() throws Exception {
//        this.base = new URL("http://localhost:" + port + "/");
//    }
//
//    @Test
//    public void saveChargingSession() throws Exception {
//        ChargingSession chargingSession = new ChargingSession();
//        chargingSession.setStartedAt(new Date("2019-07-16T05:00:10.560+0000"));
//        ResponseEntity<ChargingSession> response = template.postForEntity(base.toString() + "/chargingSessions/", chargingSession, ChargingSession.class);
//        assertThat(response.getBody().getStartedAt(), equalTo(chargingSession.getStartedAt().getTime()));
//        assertThat(response.getBody().getStartedAt(), equalTo(chargingSession.getLastModified()));
////        ChargingSession chargingSession1 = new ChargingSession("1", "1", "One");
////        List<ChargingSession> chargingSessions = new ArrayList<>();
////        chargingSessions.add(chargingSession1);
////        assertThat(response.getBody(), equalTo(chargingSessions));
//    }
//
////    @Test
////    public void getChargingSession() throws Exception {
////        ResponseEntity<String> response = template.getForEntity(base.toString() + "/chargingSessions/", String.class);
////        ChargingSession chargingSession1 = new ChargingSession("1", "1", "One");
////        List<ChargingSession> chargingSessions = new ArrayList<>();
////        chargingSessions.add(chargingSession1);
////        assertThat(response.getBody(), equalTo(chargingSessions));
////    }
//}
