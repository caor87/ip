package com.geolocation.ip.controller;

import com.geolocation.ip.model.IPInfo;
import com.geolocation.ip.service.IPInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class IPInfoController {

    private final IPInfoService ipInfoService;

    @GetMapping("/traceip/{ip}")
    public ResponseEntity<IPInfo> traceIP(@PathVariable String ip) {
        IPInfo ipInfo = ipInfoService.getIPInfo(ip);
        return new ResponseEntity<>(ipInfo, HttpStatus.OK);
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Double>> getStats() {
        List<IPInfo> allInfos = ipInfoService.getAllPInfos();
        Map<String, Double> stats = new HashMap<>();

        double maxDistance = allInfos.stream().mapToDouble(IPInfo::getDistance).max().orElse(0);
        double minDistance = allInfos.stream().mapToDouble(IPInfo::getDistance).min().orElse(0);
        double avgDistance = allInfos.stream().mapToDouble(IPInfo::getDistance).average().orElse(0);

        stats.put("maxDistance", maxDistance);
        stats.put("minDistance", minDistance);
        stats.put("avgDistance", avgDistance);

        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
}
