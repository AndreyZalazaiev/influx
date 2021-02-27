package andrew.projects.influx.Controller;

import andrew.projects.influx.Config.JwtTokenUtil;
import andrew.projects.influx.Domain.Company;
import andrew.projects.influx.Domain.Resource;
import andrew.projects.influx.Domain.User;
import andrew.projects.influx.Repos.CompanyRepo;
import andrew.projects.influx.Repos.ResourceRepo;
import andrew.projects.influx.Repos.UserRepo;
import andrew.projects.influx.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/resource")
public class ResourceController {
    private final ResourceRepo resourceRepo;
    private final CompanyRepo companyRepo;
    private final UserRepo userRepo;
    private final CompanyService companyService;

    public ResourceController(ResourceRepo resourceRepo, CompanyRepo companyRepo, UserRepo userRepo, CompanyService companyService) {
        this.resourceRepo = resourceRepo;
        this.companyRepo = companyRepo;
        this.userRepo = userRepo;
        this.companyService = companyService;
    }

    @GetMapping("/{idCompany}")
    public ResponseEntity<?> getResources(@PathVariable Integer idCompany) {
        return ResponseEntity.ok(resourceRepo.getAllByIdCompany(idCompany));
    }

    @PostMapping
    public ResponseEntity<?> createResource(HttpServletRequest req, @RequestBody Resource resource) {
        Optional<User> currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req));
        Optional<Company> currentCompany = companyRepo.findById(resource.getIdCompany());

        if (currentUser.isPresent() && currentCompany.isPresent()) {
            if (companyService.hasRightsToManipulateCompany(currentCompany.get(), currentUser.get())) {
                Resource r = new Resource(resource.getName(), resource.getPrice(), resource.getIdCompany());
                return ResponseEntity.ok(resourceRepo.save(r));
            }
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/{idResource}")
    public ResponseEntity<?> deleteResource(HttpServletRequest req, @PathVariable Integer idResource) {
        Optional<User> currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req));
        Optional<Resource> currentResource =resourceRepo.findById(idResource);

        if(currentUser.isPresent() && currentResource.isPresent()) {
            if(companyRepo.findById(currentResource.get().getIdCompany()).get().getIdUser().equals(currentUser.get().getId())){
               return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

}
