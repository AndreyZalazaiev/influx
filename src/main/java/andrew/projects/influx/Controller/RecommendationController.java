package andrew.projects.influx.Controller;

import andrew.projects.influx.Config.JwtTokenUtil;
import andrew.projects.influx.Domain.Company;
import andrew.projects.influx.Domain.Recommendation;
import andrew.projects.influx.Domain.User;
import andrew.projects.influx.Repos.CompanyRepo;
import andrew.projects.influx.Repos.RecommendationRepo;
import andrew.projects.influx.Repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/recommendation")
@RequiredArgsConstructor
public class RecommendationController {
   private final RecommendationRepo recommendationRepo;
   private final UserRepo userRepo;
   private final CompanyRepo companyRepo;


    @GetMapping("/{idCompany}")
    public ResponseEntity<?> getRecommendations(@PathVariable int idCompany) {
        return ResponseEntity.ok(recommendationRepo.findAllByIdCompany(idCompany));
    }


}
