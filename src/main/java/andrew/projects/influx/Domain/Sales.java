package andrew.projects.influx.Domain;

import andrew.projects.influx.Service.CustomDateInternatsionalizator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Sales extends BaseEntity {
    @Column(nullable = false)
    Integer idResource;
    @CreationTimestamp
    @JsonSerialize(using  = CustomDateInternatsionalizator.class)
    @Column(nullable = false,updatable = false)
    LocalDateTime date;
    @Column(nullable = false)
    Integer idCompany;
}
