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


}
