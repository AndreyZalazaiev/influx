package andrew.projects.influx.Repos;


import andrew.projects.influx.Domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Integer> {
}
