package andrew.projects.influx.Repos;


import andrew.projects.influx.Domain.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RecommendationRepo extends JpaRepository<Recommendation,Integer> {
    List<Recommendation> findAllByIdCompany(int idCompany);
}
