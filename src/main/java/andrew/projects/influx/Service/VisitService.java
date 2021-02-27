package andrew.projects.influx.Service;

import andrew.projects.influx.Domain.Company;
import andrew.projects.influx.Domain.Visit;
import andrew.projects.influx.Domain.VisitBuilder;
import andrew.projects.influx.Repos.VisitRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class VisitService {
    final VisitRepo visitRepo;

    public VisitService(VisitRepo visit) {
        this.visitRepo = visit;
    }

    public Visit updateVisitCounter(Visit v) {
        Visit visit =visitRepo.findAllByIdCompany(v.getIdCompany()).get(0);
        visit.setCount(visit.getCount() + 1);
        return visitRepo.save(visit);
    }

    public Visit createNewVisit(Company company) {
        return visitRepo.save(new VisitBuilder()
                .setIdCompany(company.getId())
                .setDate(LocalDate.now())
                .setCount(1)
                .createVisit());
    }

    public boolean isVisitPresent(Visit visit) {
        return visitRepo.findVisitByDateAndIdCompany(LocalDate.now(), visit.getIdCompany()).isPresent();
    }
}
