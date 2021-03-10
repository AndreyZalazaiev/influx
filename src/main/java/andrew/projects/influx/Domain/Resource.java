package andrew.projects.influx.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Resource extends BaseEntity {
    String name;
    String price;
    @Column(nullable = false)
    Integer idCompany;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idResource", referencedColumnName = "id")
    private List<Sales> sales = new ArrayList<>();

    public Resource(String name, String price, Integer idCompany) {
        this.name = name;
        this.price = price;
        this.idCompany = idCompany;
    }
}
