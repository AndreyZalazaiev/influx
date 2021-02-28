package andrew.projects.influx.Controller;

import andrew.projects.influx.Config.JwtTokenUtil;
import andrew.projects.influx.Domain.Company;
import andrew.projects.influx.Domain.User;
import andrew.projects.influx.Exception.ObjectNotFound;
import andrew.projects.influx.Repos.CompanyRepo;
import andrew.projects.influx.Repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyRepo companyRepo;
    private final UserRepo userRepo;


    @GetMapping
    public ResponseEntity<?> getCompanies(HttpServletRequest req) {
        Optional<User> currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req));
        if (currentUser.isPresent()) {
            return ResponseEntity.ok(companyRepo.getAllByIdUser(currentUser.get().getId()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/{idCompany}")
    public ResponseEntity<?> getCompany(HttpServletRequest req, @PathVariable int idCompany) throws ObjectNotFound {
        return ResponseEntity.ok(companyRepo.findById(idCompany)
                .orElseThrow(() -> new ObjectNotFound("Company does not exist")));
    }

    @PostMapping
    public ResponseEntity<?> createCompany(HttpServletRequest req, @RequestBody Company company) {
        User current = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req)).get();
        company.setIdUser(current.getId());
        return ResponseEntity.ok(companyRepo.save(company));
    }

    @PutMapping("/{idCompany}")
    public ResponseEntity<?> updateCompany(HttpServletRequest req, @RequestBody Company company, @PathVariable int idCompany) {
        User current = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req)).get();

        return ResponseEntity.ok(companyRepo.getCompanyByIdAndAndIdUser(idCompany, current.getId())
                .map(c -> {
                            c.setName(company.getName());
                            return companyRepo.save(c);
                        }
                ));
    }

    @DeleteMapping("/{idCompany}")
    public ResponseEntity<?> deleteCompany(HttpServletRequest req, @PathVariable int idCompany) {
        User current = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req)).get();

        if (companyRepo.getCompanyByIdAndAndIdUser(idCompany, current.getId()).isPresent()) {
            companyRepo.deleteById(idCompany);
            return ResponseEntity.ok("Deleted: " + idCompany);
        }
        return ResponseEntity.ok(new ObjectNotFound("Company does not exist").getMessage());
    }


}
