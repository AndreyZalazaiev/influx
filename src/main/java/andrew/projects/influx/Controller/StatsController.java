package andrew.projects.influx.Controller;

import andrew.projects.influx.Service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stats")
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/{idCompany}")
    public ResponseEntity<?> getStatsForCompany(@PathVariable int idCompany, @RequestParam(defaultValue = "30") int period) {
        return ResponseEntity.ok(statsService.getStatsForPeriod(idCompany, period));
    }
}
