package andrew.projects.influx.Controller;

import andrew.projects.influx.Config.JwtTokenUtil;
import andrew.projects.influx.Domain.Company;
import andrew.projects.influx.Domain.Recommendation;
import andrew.projects.influx.Domain.User;
import andrew.projects.influx.Domain.Visit;
import andrew.projects.influx.Repos.CompanyRepo;
import andrew.projects.influx.Repos.RecommendationRepo;
import andrew.projects.influx.Repos.UserRepo;
import andrew.projects.influx.Repos.VisitRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/visit")
public class VisitController {
    final VisitRepo visitRepo;
    final UserRepo userRepo;
    final CompanyRepo companyRepo;

    public VisitController(VisitRepo visitRepo, UserRepo userRepo, CompanyRepo companyRepo) {
        this.visitRepo = visitRepo;
        this.userRepo = userRepo;
        this.companyRepo = companyRepo;
    }


    @GetMapping("/{idCompany}")
    public ResponseEntity<?> getVisits(@PathVariable int idCompany) {
        return ResponseEntity.ok(visitRepo.findAllByIdCompany(idCompany));
    }

    @PostMapping
    public ResponseEntity<?> addVisit(HttpServletRequest req, @RequestBody Visit visit) {
        Optional<User> currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req));
        Optional<Company> locatedCompany = companyRepo.findById(visit.getIdCompany());

        if (locatedCompany.isPresent() && currentUser.isPresent()) {
            if (locatedCompany.get().getIdUser().equals(currentUser.get().getId())) {
                visitRepo.save(visit);
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/{idVisit}")
    public ResponseEntity<?> deleteVisit(HttpServletRequest req, @PathVariable int idVisit) {
        Optional<User> currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req));
        Optional<Visit> recommendation = visitRepo.findById(idVisit);

        if (currentUser.isPresent() && recommendation.isPresent()) {
            Optional<Company> locatedCompany = companyRepo.findById(recommendation.get().getIdCompany());

            if (locatedCompany.isPresent()) {
                if (locatedCompany.get().getIdUser().equals(currentUser.get().getId())) {
                    visitRepo.delete(recommendation.get());
                }
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
