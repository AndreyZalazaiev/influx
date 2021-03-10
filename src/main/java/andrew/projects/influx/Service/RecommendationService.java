package andrew.projects.influx.Service;

import andrew.projects.influx.DTO.StatsDTO;
import andrew.projects.influx.Domain.Recommendation;
import andrew.projects.influx.Domain.RecommendationBuilder;
import andrew.projects.influx.Exception.NotEnoughData;
import andrew.projects.influx.Repos.SalesRepo;
import lombok.NoArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.LongSummaryStatistics;

@Service
@NoArgsConstructor
public class RecommendationService implements RecommendationFunctional {
    private final int GATHERING_PERIOD = 90;
    private final String LINE_SEPARATOR = System.lineSeparator();
    private StatsService statsService;
    private SalesRepo salesRepo;

    @Autowired
    public RecommendationService(StatsService statsService, SalesRepo salesRepo) {
        this.statsService = statsService;
        this.salesRepo = salesRepo;
    }

    public Recommendation getPriceRecommendations(int idCompany) throws NotEnoughData {
        List<StatsDTO> periodData = statsService.getStatsForPeriod(idCompany, GATHERING_PERIOD);
        if (periodData.size() < 2) {
            throw new NotEnoughData("Gathered information is not enough for analysis");
        }
        LongSummaryStatistics stats = periodData.stream().mapToLong(StatsDTO::getCount).summaryStatistics();

        double variance = periodData.stream().mapToDouble(s -> Math.pow(s.getCount() - stats.getAverage(), 2)).sum();
        double standardDeviation = Math.sqrt(variance);

        return new RecommendationBuilder()
                .setDate(LocalDateTime.now())
                .setPeriod(GATHERING_PERIOD)
                .setIdCompany(idCompany)
                .setText(new StringBuilder().append("Variance: ").append(variance).append(LINE_SEPARATOR)
                        .append("Standard deviation: ").append(standardDeviation).append(LINE_SEPARATOR)
                        .append("Overpriced product: ").append(periodData.get(periodData.size() - 1).getName()).append(LINE_SEPARATOR)
                        .append("Unappreciated: ").append(periodData.get(0).getName()).toString())
                .createRecommendation();

    }

    public Recommendation getRecommendations(int idCompany) throws NotEnoughData {
        List<StatsDTO> periodData = statsService.getStatsForPeriod(idCompany, GATHERING_PERIOD);
        if (periodData.size() < 2) {
            throw new NotEnoughData("Gathered information is not enough for analysis");
        }

        val sales =salesRepo.findAllByIdCompany(idCompany);


        return null;
    }
}
