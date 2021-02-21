package andrew.projects.influx.Controller;

import andrew.projects.influx.Config.JwtTokenUtil;
import andrew.projects.influx.Domain.Company;
import andrew.projects.influx.Domain.User;
import andrew.projects.influx.Repos.CompanyRepo;
import andrew.projects.influx.Repos.UserRepo;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@RestController
@RequestMapping("/company")
@Slf4j
public class CompanyController {
    @Autowired
    CompanyRepo companyRepo;
    @Autowired
    UserRepo userRepo;


    @GetMapping
    public ResponseEntity<?> getCompanies(HttpServletRequest req) {
        User currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req)).get();
        if (currentUser != null) {
            return ResponseEntity.ok(companyRepo.getAllByIdUser(currentUser.getId()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping
    public ResponseEntity<?> postCompany(HttpServletRequest req, @RequestBody Company company) {
        User current = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req)).get();
        company.setIdUser(current.getId());

        if (hasRigthsToManipulateCompany(company, current)) {
            Company stored = companyRepo.findById(company.getId()).get();
            stored.setName(company.getName());
            return ResponseEntity.ok(companyRepo.save(stored));
        }
        return ResponseEntity.ok(companyRepo.save(company));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCompany(HttpServletRequest req, @RequestBody Company company) {
        User current = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req)).get();

        if (hasRigthsToManipulateCompany(company, current)) {
            companyRepo.deleteInBatch(Collections.singletonList(company));
            return ResponseEntity.ok("Deleted");
        }
        return ResponseEntity.badRequest().body("Non company owner");
    }

    private boolean hasRigthsToManipulateCompany(@RequestBody Company company, User current) {
        return current.getCompanies().stream().anyMatch(c -> c.getId().equals(company.getId()));
    }
}
