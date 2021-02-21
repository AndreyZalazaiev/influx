package andrew.projects.influx.Repos;

import andrew.projects.influx.Domain.Visit;
import org.springframework.data.repository.CrudRepository;

public interface VisitRepo extends CrudRepository<Visit,Integer> {
}
