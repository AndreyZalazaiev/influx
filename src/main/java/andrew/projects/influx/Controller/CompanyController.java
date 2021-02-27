package andrew.projects.influx.Controller;

import andrew.projects.influx.Config.JwtTokenUtil;
import andrew.projects.influx.Domain.Company;
import andrew.projects.influx.Domain.User;
import andrew.projects.influx.Repos.CompanyRepo;
import andrew.projects.influx.Repos.UserRepo;
import andrew.projects.influx.Service.CompanyService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/company")
public class CompanyController {
    private final CompanyRepo companyRepo;
    private final UserRepo userRepo;
    private final CompanyService companyService;

    public CompanyController(CompanyRepo companyRepo, UserRepo userRepo, CompanyService companyService) {
        this.companyRepo = companyRepo;
        this.userRepo = userRepo;
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<?> getCompanies(HttpServletRequest req) {
        Optional<User> currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req));
        if (currentUser.isPresent()) {
            return ResponseEntity.ok(companyRepo.getAllByIdUser(currentUser.get().getId()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping
    public ResponseEntity<?> postCompany(HttpServletRequest req,  @RequestBody Company company) {
        User current = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req)).get();
        company.setIdUser(current.getId());

        if ((companyService.hasRightsToManipulateCompany(company, current))) {
            Company stored = companyRepo.findById(company.getId()).get();
            stored.setName(company.getName());
            return ResponseEntity.ok(companyRepo.save(stored));
        }
        return ResponseEntity.ok(companyRepo.save(company));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCompany(HttpServletRequest req, @RequestBody Company company) {
        User current = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req)).get();

        if (companyService.hasRightsToManipulateCompany(company, current)) {
            companyRepo.deleteInBatch(Collections.singletonList(company));
            return ResponseEntity.ok("Deleted");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


}
