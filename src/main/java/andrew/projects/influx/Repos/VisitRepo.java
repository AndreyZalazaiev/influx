package andrew.projects.influx.Repos;

import andrew.projects.influx.Domain.Visit;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VisitRepo extends CrudRepository<Visit, Integer> {
    List<Visit> findAllByIdCompany(int idCompany);

    Optional<Visit> findVisitByDateAndIdCompany(LocalDate date, int idCompany);
}
