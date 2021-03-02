package andrew.projects.influx.Controller;

import andrew.projects.influx.Domain.Recommendation;
import andrew.projects.influx.Exception.NotEnoughData;
import andrew.projects.influx.Repos.RecommendationRepo;
import andrew.projects.influx.Service.RecommendationFunctional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recommendation")
@RequiredArgsConstructor
public class RecommendationController {
    private RecommendationRepo recommendationRepo;
    private RecommendationFunctional recommendationService;

    @Autowired
    public RecommendationController(RecommendationRepo recommendationRepo, RecommendationFunctional recommendationService) {
        this.recommendationRepo = recommendationRepo;
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{idCompany}")
    public ResponseEntity<?> getRecommendations(@PathVariable int idCompany) {
        List<Recommendation> recommendations = new ArrayList<>();
        try {
            recommendations.add(recommendationService.getPriceRecommendations(idCompany));
            recommendations.add(recommendationService.getRecommendations(idCompany));
        } catch (NotEnoughData e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Optional<Recommendation> storedRecommendation = recommendationRepo.findTopByIdCompany(idCompany);

        if (storedRecommendation.isPresent()) {
            if (storedRecommendation.get().getDate().toLocalDate().isEqual(LocalDate.now())) {
                return ResponseEntity.ok(recommendations);
            }
        }
        return ResponseEntity.ok(recommendationRepo.saveAll(recommendations));

    }


}
