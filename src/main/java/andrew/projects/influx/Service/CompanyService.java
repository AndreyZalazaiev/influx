package andrew.projects.influx.Service;

import andrew.projects.influx.Domain.Company;
import andrew.projects.influx.Domain.User;

public class CompanyService {
    public static boolean hasRightsToManipulateCompany(Company company, User current) {
        return current.getCompanies().stream().anyMatch(c -> c.getId().equals(company.getId()));
    }

}
