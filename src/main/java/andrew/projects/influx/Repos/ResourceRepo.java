package andrew.projects.influx.Repos;

import andrew.projects.influx.Domain.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceRepo extends JpaRepository<Resource, Integer> {
    List<Resource> getAllByIdCompany(int idCompany);
}
