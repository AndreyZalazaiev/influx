package andrew.projects.influx.Repos;


import andrew.projects.influx.Domain.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface RecommendationRepo extends JpaRepository<Recommendation,Integer> {
    List<Recommendation> findAllByIdCompany(int idCompany);
    Optional<Recommendation> findTopByIdCompany(int idCompany);
}
