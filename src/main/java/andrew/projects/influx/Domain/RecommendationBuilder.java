package andrew.projects.influx.Domain;

import java.time.LocalDateTime;

public class RecommendationBuilder {
    private Integer idCompany;
    private String text;
    private LocalDateTime date;
    private Integer period;

    public RecommendationBuilder setIdCompany(Integer idCompany) {
        this.idCompany = idCompany;
        return this;
    }

    public RecommendationBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public RecommendationBuilder setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public RecommendationBuilder setPeriod(Integer period) {
        this.period = period;
        return this;
    }

    public Recommendation createRecommendation() {
        return new Recommendation(idCompany, text, date, period);
    }
}