package andrew.projects.influx.Repos;

import andrew.projects.influx.Domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface DeviceRepo extends JpaRepository<Device,String> {

}
