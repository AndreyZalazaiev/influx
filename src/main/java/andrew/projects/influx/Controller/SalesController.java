package andrew.projects.influx.Controller;

import andrew.projects.influx.Config.JwtTokenUtil;
import andrew.projects.influx.Domain.*;
import andrew.projects.influx.Repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/sales")
public class SalesController {
    final SalesRepo salesRepo;
    final UserRepo userRepo;
    final CompanyRepo companyRepo;

    public SalesController(SalesRepo salesRepo, UserRepo userRepo, CompanyRepo companyRepo) {
        this.salesRepo = salesRepo;
        this.userRepo = userRepo;
        this.companyRepo = companyRepo;
    }


    @GetMapping("/{idCompany}")
    public ResponseEntity<?> getSales(@PathVariable int idCompany) {
        return ResponseEntity.ok(salesRepo.findAllByIdCompany(idCompany));
    }

    @PostMapping
    public ResponseEntity<?> addSale(HttpServletRequest req, @RequestBody Sales sales) {
        Optional<User> currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req));
        Optional<Company> locatedCompany = companyRepo.findById(sales.getIdCompany());

        if (locatedCompany.isPresent() && currentUser.isPresent()) {
            if (locatedCompany.get().getIdUser().equals(currentUser.get().getId())) {
                return ResponseEntity.ok(salesRepo.save(sales));
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/{idSale}")
    public ResponseEntity<?> deleteSale(HttpServletRequest req, @PathVariable int idSale) {
        Optional<User> currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req));
        Optional<Sales> recommendation = salesRepo.findById(idSale);

        if (currentUser.isPresent() && recommendation.isPresent()) {
            Optional<Company> locatedCompany = companyRepo.findById(recommendation.get().getIdCompany());

            if (locatedCompany.isPresent()) {
                if (locatedCompany.get().getIdUser().equals(currentUser.get().getId())) {
                    salesRepo.delete(recommendation.get());
                }
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
