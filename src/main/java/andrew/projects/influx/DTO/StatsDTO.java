package andrew.projects.influx.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatsDTO {
    private int idResource;
    private String name;
    private long count;
}
