package andrew.projects.influx.Repos;

import andrew.projects.influx.Domain.Visit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VisitRepo extends CrudRepository<Visit,Integer> {
    List<Visit> findAllByIdCompany(int idCompany);
    Optional<Visit> findVisitByDateAndIdCompany(LocalDate date, int idCompany);
}
