package andrew.projects.influx.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatsDTO {
    int idResource;
    long count;
}
