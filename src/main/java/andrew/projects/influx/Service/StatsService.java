package andrew.projects.influx.Service;

import andrew.projects.influx.DTO.StatsDTO;
import andrew.projects.influx.Domain.Resource;
import andrew.projects.influx.Domain.Sales;
import andrew.projects.influx.Repos.ResourceRepo;
import andrew.projects.influx.Repos.SalesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatsService {
    private final ResourceRepo resourceRepo;
    private final SalesRepo salesRepo;
    @Autowired
    public StatsService(ResourceRepo resourceRepo, SalesRepo salesRepo) {
        this.resourceRepo = resourceRepo;
        this.salesRepo = salesRepo;
    }

    public List<StatsDTO> getStatsForPeriod(int idCompany, int period) {
        Map<Integer, Long> sales = salesRepo.findAllByIdCompany(idCompany).stream()
                .filter(s -> s.getDate().isAfter(LocalDateTime.from(LocalDateTime.now()).minusDays(period)))
                .collect(Collectors.groupingBy(Sales::getIdResource, Collectors.counting()))
                .entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));


        resourceRepo.getAllByIdCompany(idCompany).stream()
                .filter(r -> !sales.containsKey(r.getId()))
                .forEach(resource -> sales.put(resource.getId(), 0L));

        return convertMapToDTO(sales, idCompany);
    }

    private List<StatsDTO> convertMapToDTO(Map<Integer, Long> map, int idCompany) {
        List<StatsDTO> list = new ArrayList<>();
        List<Resource> resources = resourceRepo.getAllByIdCompany(idCompany);

        map.forEach((id, count) -> list.add(new StatsDTO(id, resources.stream().filter(r -> r.getId().equals(id)).findFirst().get().getName(), count)));
        return list;
    }
}
