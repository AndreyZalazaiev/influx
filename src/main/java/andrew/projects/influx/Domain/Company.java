package andrew.projects.influx.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Company extends BaseEntity {
    @Column(nullable = false)
    private Integer idUser;
    @Column(nullable = false)
    private String name;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idCompany", referencedColumnName = "id")
    private Set<Recommendation> recommendations;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idCompany", referencedColumnName = "id")
    private Set<Resource> resources;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idCompany", referencedColumnName = "id")
    private Set<Sales> sales ;


}