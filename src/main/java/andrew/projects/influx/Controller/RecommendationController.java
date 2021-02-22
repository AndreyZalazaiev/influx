package andrew.projects.influx.Controller;

import andrew.projects.influx.Config.JwtTokenUtil;
import andrew.projects.influx.Domain.Company;
import andrew.projects.influx.Domain.Recommendation;
import andrew.projects.influx.Domain.User;
import andrew.projects.influx.Repos.CompanyRepo;
import andrew.projects.influx.Repos.RecommendationRepo;
import andrew.projects.influx.Repos.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {
    final RecommendationRepo recommendationRepo;
    final UserRepo userRepo;
    final CompanyRepo companyRepo;

    public RecommendationController(RecommendationRepo recommendationRepo, UserRepo userRepo, CompanyRepo companyRepo) {
        this.recommendationRepo = recommendationRepo;
        this.userRepo = userRepo;
        this.companyRepo = companyRepo;
    }


    @GetMapping("/{idCompany}")
    public ResponseEntity<?> getRecommendations(@PathVariable int idCompany) {
        return ResponseEntity.ok(recommendationRepo.findAllByIdCompany(idCompany));
    }

    @PostMapping
    public ResponseEntity<?> addRecommendation(HttpServletRequest req, @RequestBody Recommendation recommendation) {
        Optional<User> currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req));
        Optional<Company> locatedCompany = companyRepo.findById(recommendation.getIdCompany());

        if (locatedCompany.isPresent() && currentUser.isPresent()) {
            if (locatedCompany.get().getIdUser().equals(currentUser.get().getId())) {
                recommendationRepo.save(recommendation);
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/{idRecommendation}")
    public ResponseEntity<?> deleteRecommendation(HttpServletRequest req, @PathVariable int idRecommendation) {
        Optional<User> currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req));
        Optional<Recommendation> recommendation = recommendationRepo.findById(idRecommendation);

        if (currentUser.isPresent() && recommendation.isPresent()) {
            Optional<Company> locatedCompany = companyRepo.findById(recommendation.get().getIdCompany());

            if (locatedCompany.isPresent()) {
                if (locatedCompany.get().getIdUser().equals(currentUser.get().getId())) {
                    recommendationRepo.delete(recommendation.get());
                }
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
