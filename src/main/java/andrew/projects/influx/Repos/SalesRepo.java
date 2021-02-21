package andrew.projects.influx.Repos;

import andrew.projects.influx.Domain.Device;
import andrew.projects.influx.Domain.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepo extends JpaRepository<Sales,Integer> {

}
