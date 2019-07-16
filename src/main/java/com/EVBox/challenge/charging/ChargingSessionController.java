package com.EVBox.challenge.charging;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChargingSessionController {

    @Autowired
    private ChargingSessionService chargingSessionService;

    @RequestMapping("/chargingSessions")
    public List<ChargingSession> getAllChargingSessions() {
        return chargingSessionService.getAllChargingSessions();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/chargingSummary")
    public ChargingSummary getChargingSummary() {
        return chargingSessionService.getChargingSummary();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/chargingSessions/{id}")
    public ChargingSession getChargingSessionById(@PathVariable("id") UUID id) {
        return chargingSessionService.getChargingSession(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/chargingSessions")
    public ChargingSession addChargingSession(@RequestBody ChargingSession chargingSessionRequest) {
        return chargingSessionService.addChargingSession(chargingSessionRequest);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/chargingSessions/{id}")
    public ChargingSession suspendChargingSession(@PathVariable("id") UUID id, @RequestBody ChargingSession chargingSessionRequest) {
        return chargingSessionService.suspendChargingSession(id, chargingSessionRequest);
    }
}
