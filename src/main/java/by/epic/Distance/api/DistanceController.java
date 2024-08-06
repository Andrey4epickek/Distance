package by.epic.Distance.api;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class DistanceController {

    private static final int MAX_REQUESTS_PER_MINUTE = 10;
    public static final Cache<String, Integer> requestCounter = CacheBuilder.newBuilder()
            .expireAfterAccess(1, TimeUnit.MINUTES)
            .build();

    @GetMapping("/distance")
    public ResponseEntity<Map<String, Double>> calculateDistance(
            @RequestParam("lat1") double lat1,
            @RequestParam("lon1") double lon1,
            @RequestParam("lat2") double lat2,
            @RequestParam("lon2") double lon2,
            HttpServletRequest request) {
        int currentRequests = requestCounter.getIfPresent(request.getRemoteAddr()) != null ? requestCounter.getIfPresent(request.getRemoteAddr()) : 0;
        if (currentRequests >= MAX_REQUESTS_PER_MINUTE) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        requestCounter.put(request.getRemoteAddr(), currentRequests + 1);

        double distance = DistanceCalculator.calculateDistance(lat1, lon1, lat2, lon2);
        Map<String, Double> response = new HashMap<>();
        response.put("distance", distance);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}