package andrew.projects.influx.Domain;

import andrew.projects.influx.Service.CustomLocalDateTimeInternatsionalizator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recommendation extends BaseEntity {
    @Column(nullable = false)
    Integer idCompany;
    String text;
    @JsonSerialize(using  = CustomLocalDateTimeInternatsionalizator.class)
    LocalDateTime date;
    @Column(columnDefinition = "int default 7")
    private Integer period;

}
