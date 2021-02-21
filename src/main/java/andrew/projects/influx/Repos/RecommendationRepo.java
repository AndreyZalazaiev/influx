package andrew.projects.influx.Repos;


import andrew.projects.influx.Domain.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RecommendationRepo extends JpaRepository<Recommendation,Integer> {
}
