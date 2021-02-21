package andrew.projects.influx.Repos;

import andrew.projects.influx.Domain.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepo extends JpaRepository<Resource, Integer> {

}
