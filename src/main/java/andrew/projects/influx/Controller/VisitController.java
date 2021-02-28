package andrew.projects.influx.Controller;

import andrew.projects.influx.Config.JwtTokenUtil;
import andrew.projects.influx.Domain.Company;
import andrew.projects.influx.Domain.User;
import andrew.projects.influx.Domain.Visit;
import andrew.projects.influx.Exception.ObjectNotFound;
import andrew.projects.influx.Repos.CompanyRepo;
import andrew.projects.influx.Repos.UserRepo;
import andrew.projects.influx.Repos.VisitRepo;
import andrew.projects.influx.Service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/visit")
@RequiredArgsConstructor
public class VisitController {
    private final VisitRepo visitRepo;
    private final UserRepo userRepo;
    private final CompanyRepo companyRepo;
    private final VisitService visitService;

    @GetMapping("/{idCompany}")
    public ResponseEntity<?> getVisits(@PathVariable int idCompany) {
        return ResponseEntity.ok(visitRepo.findAllByIdCompany(idCompany));
    }

    @PostMapping
    public ResponseEntity<?> createVisit(HttpServletRequest req, @RequestBody Visit visit) {
        Optional<User> currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req));
        Optional<Company> locatedCompany = companyRepo.findById(visit.getIdCompany());

        if (locatedCompany.isPresent() && currentUser.isPresent()) {
            if (locatedCompany.get().getId() == currentUser.get().getId()) {

                if (visitService.isVisitPresent(visit)) {
                    return ResponseEntity.ok(visitService.updateVisitCounter(visit));
                } else {
                    return ResponseEntity.ok(visitService.createNewVisit(locatedCompany.get()));
                }
            }
        }
        return ResponseEntity.ok(new ObjectNotFound("Visit does not exist").getMessage());
    }
    @PutMapping("/{idVisit}")
    public ResponseEntity<?> updateVisit(HttpServletRequest req, @RequestBody Visit visit,@PathVariable int idVisit) {
        Optional<User> currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req));
        Optional<Company> locatedCompany = companyRepo.findById(visit.getIdCompany());

        if (locatedCompany.isPresent() && currentUser.isPresent()) {
            if (locatedCompany.get().getId() == currentUser.get().getId()) {

                if (visitService.isVisitPresent(visit)) {
                    Visit stored =visitRepo.findById(idVisit).get();
                    stored.setCount(visit.getCount());
                    stored.setDate(visit.getDate());
                    stored.setIdCompany(visit.getIdCompany());

                    return ResponseEntity.ok(visitRepo.save(stored));
                }
            }
        }
        return ResponseEntity.ok(new ObjectNotFound("Visit does not exist").getMessage());
    }

    @DeleteMapping("/{idVisit}")
    public ResponseEntity<?> deleteVisit(HttpServletRequest req, @PathVariable int idVisit) {
        Optional<User> currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req));
        Optional<Visit> recommendation = visitRepo.findById(idVisit);

        if (currentUser.isPresent() && recommendation.isPresent()) {
            Optional<Company> locatedCompany = companyRepo.findById(recommendation.get().getIdCompany());

            if (locatedCompany.isPresent()) {
                if (locatedCompany.get().getIdUser().equals(currentUser.get().getId())) {
                    visitRepo.deleteById(idVisit);
                    return ResponseEntity.ok("Deleted visit with id " + idVisit);
                }
            }
        }
        return ResponseEntity.ok(new ObjectNotFound("Visit does not exist").getMessage());
    }
}
