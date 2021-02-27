package andrew.projects.influx.Controller;

import andrew.projects.influx.Config.JwtTokenUtil;
import andrew.projects.influx.Domain.Company;
import andrew.projects.influx.Domain.Device;
import andrew.projects.influx.Domain.User;
import andrew.projects.influx.Repos.CompanyRepo;
import andrew.projects.influx.Repos.DeviceRepo;
import andrew.projects.influx.Repos.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/device")
public class DeviceController {
    final DeviceRepo deviceRepo;
    final UserRepo userRepo;
    final CompanyRepo companyRepo;

    public DeviceController(DeviceRepo deviceRepo, UserRepo userRepo, CompanyRepo companyRepo) {
        this.deviceRepo = deviceRepo;
        this.userRepo = userRepo;
        this.companyRepo = companyRepo;
    }

    @GetMapping("/{idCompany}")
    public ResponseEntity<?> getDevices(@PathVariable  int idCompany){
        return ResponseEntity.ok(deviceRepo.findAllByIdCompany(idCompany));
    }

    @PostMapping
    public ResponseEntity<?> addDevice(HttpServletRequest req, @RequestBody Device device) {
        Optional<User> currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req));
        Optional<Company> locatedCompany = companyRepo.findById(device.getIdCompany());

        if (locatedCompany.isPresent() && currentUser.isPresent()) {
            if (locatedCompany.get().getIdUser().equals(currentUser.get().getId())) {
                return  ResponseEntity.ok(deviceRepo.save(device));
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @DeleteMapping("/{deviceCode}")
    public ResponseEntity<?> deleteDevice(HttpServletRequest req, @PathVariable String deviceCode) {
        Optional<User> currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req));
        Optional<Device> device = deviceRepo.getFirstByDeviceCode(deviceCode);
        if (currentUser.isPresent() && device.isPresent()) {
            Optional<Company> locatedCompany = companyRepo.findById(device.get().getIdCompany());

            if (locatedCompany.isPresent()) {
                if (locatedCompany.get().getIdUser().equals(currentUser.get().getId())) {
                    deviceRepo.delete(device.get());
                }
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
