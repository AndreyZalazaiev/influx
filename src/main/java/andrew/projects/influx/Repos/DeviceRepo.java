package andrew.projects.influx.Repos;

import andrew.projects.influx.Domain.Company;
import andrew.projects.influx.Domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface DeviceRepo extends JpaRepository<Device,String> {
    Optional<Device> getFirstByDeviceCode(String  deviceCode);
    List<Device> findAllByIdCompany(int idCompany);
}
