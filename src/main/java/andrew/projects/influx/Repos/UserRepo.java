package andrew.projects.influx.Repos;


import andrew.projects.influx.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
	
	Optional<User> findByUsername(String username);

	@Query("from User u where u.emailConfirmation=:emailConfirmation")
	Optional<User> findUserByActivationCode(@Param("emailConfirmation") String emailConfirmation);

	
}