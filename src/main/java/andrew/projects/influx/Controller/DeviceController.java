package andrew.projects.influx.Controller;

import andrew.projects.influx.Config.JwtTokenUtil;
import andrew.projects.influx.Domain.Company;
import andrew.projects.influx.Domain.Device;
import andrew.projects.influx.Domain.User;
import andrew.projects.influx.Exception.ObjectNotFound;
import andrew.projects.influx.Repos.CompanyRepo;
import andrew.projects.influx.Repos.DeviceRepo;
import andrew.projects.influx.Repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/device")
@RequiredArgsConstructor
public class DeviceController {
    private final DeviceRepo deviceRepo;
    private final UserRepo userRepo;
    private final CompanyRepo companyRepo;


    @GetMapping("/{idCompany}")
    public ResponseEntity<?> getDevices(@PathVariable int idCompany) {
        return ResponseEntity.ok(deviceRepo.findAllByIdCompany(idCompany));
    }

    @PostMapping
    public ResponseEntity<?> createDevice(HttpServletRequest req, @RequestBody Device device) {
        User currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req)).get();
            if ((companyRepo.getCompanyByIdAndAndIdUser(device.getIdCompany(), currentUser.getId()).isPresent())) {
                return ResponseEntity.ok(deviceRepo.save(device));
        }
        return ResponseEntity.ok(new ObjectNotFound("Company does not exist").getMessage());
    }

    @PutMapping("/{deviceCode}")
    public ResponseEntity<?> updateDevice(HttpServletRequest req, @RequestBody Device device, @PathVariable String deviceCode) {
        User currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req)).get();

        if ((companyRepo.getCompanyByIdAndAndIdUser(device.getIdCompany(), currentUser.getId()).isPresent())) {
            Device storedDevice =deviceRepo.findById(deviceCode).get();
            storedDevice.setIdCompany(device.getIdCompany());
            return ResponseEntity.ok(deviceRepo.save(storedDevice));
        }
        return ResponseEntity.ok(new ObjectNotFound("Company does not exist").getMessage());
    }

    @DeleteMapping("/{deviceCode}")
    public ResponseEntity<?> deleteDevice(HttpServletRequest req, @PathVariable String deviceCode) {
        Optional<User> currentUser = userRepo.findByUsername(JwtTokenUtil.obtainUserName(req));
        Device device = deviceRepo.getOne(deviceCode);
        if (currentUser.isPresent() ) {
            Optional<Company> locatedCompany = companyRepo.findById(device.getIdCompany());

            if (locatedCompany.isPresent()) {
                if (locatedCompany.get().getIdUser().equals(currentUser.get().getId())) {
                    deviceRepo.deleteById(deviceCode);
                    return  ResponseEntity.ok("Deleted device with id: "+deviceCode);
                }
            }
        }
        return ResponseEntity.ok(new ObjectNotFound("Device does not exist").getMessage());
    }
}
