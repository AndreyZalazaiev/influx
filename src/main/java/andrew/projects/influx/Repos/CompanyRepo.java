package andrew.projects.influx.Repos;


import andrew.projects.influx.Domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CompanyRepo extends JpaRepository<Company, Integer> {
    Iterable<Company> getAllByIdUser(@Param("IdUser") int IdUser);
    Optional<Company> getCompanyByIdAndAndIdUser(int idCompany, int idUser);

    @Override
    @Transactional
    void deleteById(Integer integer);

}
