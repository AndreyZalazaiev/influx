package andrew.projects.influx.Controller;

import andrew.projects.influx.Config.JwtTokenUtil;
import andrew.projects.influx.Domain.Company;
import andrew.projects.influx.Domain.Resource;
import andrew.projects.influx.Domain.User;
import andrew.projects.influx.Exception.ObjectNotFound;
import andrew.projects.influx.Repos.CompanyRepo;
import andrew.projects.influx.Repos.ResourceRepo;
import andrew.projects.influx.Repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/resource")
@RequiredArgsConstructor
public class ResourceController {
    private final ResourceRepo resourceRepo;
    private final CompanyRepo companyRepo;
    private final UserRepo userRepo;

    @GetMapping("/{idCompany}")
    public ResponseEntity<?> getResources(@PathVariable Integer idCompany) {
        return ResponseEntity.ok(resourceRepo.getAllByIdCompany(idCompany));
    }

    @PostMapping
    public ResponseEntity<?> createResource(HttpServletRequest req, @RequestBody Resource resource) {
        Optional<User> currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req));
        Optional<Company> currentCompany = companyRepo.findById(resource.getIdCompany());

        if (currentUser.isPresent() && currentCompany.isPresent()) {
            if (companyRepo.getCompanyByIdAndAndIdUser(currentCompany.get().getId(), currentUser.get().getId()).isPresent()) {
                Resource r = new Resource(resource.getName(), resource.getPrice(), resource.getIdCompany());
                return ResponseEntity.ok(resourceRepo.save(r));
            }
        }
        return ResponseEntity.ok(new ObjectNotFound("Resource does not exist").getMessage());
    }

    @PutMapping("/{idResource}")
    public ResponseEntity<?> updateResource(HttpServletRequest req, @RequestBody Resource resource, @PathVariable int idResource) {
        Optional<User> currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req));
        Optional<Resource> resourceStored = resourceRepo.findById(idResource);

        if (resourceStored.isPresent() && currentUser.isPresent()) {
            resourceStored.get().setName(resource.getName());
            resourceStored.get().setPrice(resource.getPrice());
            return ResponseEntity.ok(companyRepo.findById(resourceStored.get().getIdCompany()).get()
                    .getIdUser().equals(currentUser.get().getId())
                    ? resourceRepo.save(resourceStored.get())
                    : (new ObjectNotFound("Resource does not exist").getMessage()));
        }
        return ResponseEntity.ok(new ObjectNotFound("Resource does not exist").getMessage());
    }

    @DeleteMapping("/{idResource}")
    public ResponseEntity<?> deleteResource(HttpServletRequest req, @PathVariable Integer idResource) {
        Optional<User> currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req));
        Optional<Resource> currentResource = resourceRepo.findById(idResource);

        if (currentUser.isPresent() && currentResource.isPresent()) {
            if (companyRepo.findById(currentResource.get().getIdCompany()).get().getIdUser().equals(currentUser.get().getId())) {

                resourceRepo.deleteById(idResource);
                return ResponseEntity.ok("Deleted resource with id " + idResource);
            }
        }
        return ResponseEntity.ok(new ObjectNotFound("Resource does not exist").getMessage());
    }

}
