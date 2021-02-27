package andrew.projects.influx.Domain;

import andrew.projects.influx.Service.CustomLocalDateInternatsionalizator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Visit extends BaseEntity {
    @Column(nullable = false)
    private Integer idCompany;
    @Column(columnDefinition = "int default 0")
    private Integer count;
    @CreationTimestamp
    @JsonSerialize(using = CustomLocalDateInternatsionalizator.class)
    @Column(nullable = false,updatable = false)
    LocalDate date;
}
