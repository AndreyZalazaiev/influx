package andrew.projects.influx.Repos;

import andrew.projects.influx.Domain.Visit;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VisitRepo extends CrudRepository<Visit,Integer> {
    List<Visit> findAllByIdCompany(int idCompany);
}
