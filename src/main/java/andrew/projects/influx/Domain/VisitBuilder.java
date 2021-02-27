package andrew.projects.influx.Domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class VisitBuilder {
    private Integer idCompany;
    private Integer count;
    private LocalDate date;

    public VisitBuilder setIdCompany(Integer idCompany) {
        this.idCompany = idCompany;
        return this;
    }

    public VisitBuilder setCount(Integer count) {
        this.count = count;
        return this;
    }

    public VisitBuilder setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public Visit createVisit() {
        return new Visit(idCompany, count, date);
    }
}