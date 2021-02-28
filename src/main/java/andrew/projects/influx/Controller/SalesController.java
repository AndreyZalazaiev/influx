package andrew.projects.influx.Controller;

import andrew.projects.influx.Config.JwtTokenUtil;
import andrew.projects.influx.Domain.*;
import andrew.projects.influx.Exception.ObjectNotFound;
import andrew.projects.influx.Repos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SalesController {
    private final SalesRepo salesRepo;
    private final UserRepo userRepo;
    private final CompanyRepo companyRepo;

    @GetMapping("/{idCompany}")
    public ResponseEntity<?> getSales(@PathVariable int idCompany) {
        return ResponseEntity.ok(salesRepo.findAllByIdCompany(idCompany));
    }

    @PostMapping
    public ResponseEntity<?> createSale(HttpServletRequest req, @RequestBody Sales sales) {
        Optional<User> currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req));
        Optional<Company> locatedCompany = companyRepo.findById(sales.getIdCompany());

        if (locatedCompany.isPresent() && currentUser.isPresent()) {
            if (locatedCompany.get().getIdUser().equals(currentUser.get().getId())) {
                return ResponseEntity.ok(salesRepo.save(sales));
            }
        }
        return ResponseEntity.ok(new ObjectNotFound("Sale does not exist").getMessage());
    }
    @PutMapping("/{idSale}")
    public ResponseEntity<?> updateSale(HttpServletRequest req, @RequestBody Sales sales,@PathVariable int idSale) {
        Optional<User> currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req));
        Optional<Company> locatedCompany = companyRepo.findById(sales.getIdCompany());

        if (locatedCompany.isPresent() && currentUser.isPresent()) {
            if (locatedCompany.get().getIdUser().equals(currentUser.get().getId())) {

                Sales stored = salesRepo.findById(idSale).get();
                stored.setIdCompany(sales.getIdCompany());
                stored.setDate(sales.getDate());
                stored.setIdResource(sales.getIdResource());

                return ResponseEntity.ok(salesRepo.save(stored));
            }
        }
        return ResponseEntity.ok(new ObjectNotFound("Sale or company does not exist").getMessage());
    }

    @DeleteMapping("/{idSale}")
    public ResponseEntity<?> deleteSale(HttpServletRequest req, @PathVariable int idSale) {
        Optional<User> currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req));
        Optional<Sales> recommendation = salesRepo.findById(idSale);

        if (currentUser.isPresent() && recommendation.isPresent()) {
            Optional<Company> locatedCompany = companyRepo.findById(recommendation.get().getIdCompany());

            if (locatedCompany.isPresent()) {
                if (locatedCompany.get().getIdUser().equals(currentUser.get().getId())) {
                    salesRepo.deleteById(idSale);
                    return ResponseEntity.ok("Deleted sale with id "+idSale);
                }
            }
        }
        return ResponseEntity.ok(new ObjectNotFound("Sale does not exist").getMessage());
    }
}
