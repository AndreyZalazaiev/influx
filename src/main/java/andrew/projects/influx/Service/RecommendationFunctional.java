package andrew.projects.influx.Service;

import andrew.projects.influx.Domain.Recommendation;
import andrew.projects.influx.Exception.NotEnoughData;

public interface RecommendationFunctional {
    Recommendation getRecommendations(int idCompany) throws NotEnoughData;
    Recommendation getPriceRecommendations(int idCompany) throws NotEnoughData;
}
