package ghostbuster.springDataJpaWorkout.model.shop;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ARTYKUL")
public class Article implements Serializable {

    @Id
    @Column(name ="ID_ARTYKULU")
    private Long id;

    @Column(name = "NAZWA")
    private String name;

    @Column(name = "OPIS")
    private String description;

    @OneToOne
    @JoinColumns(
            {
                    @JoinColumn(updatable=false,insertable=false, name="ID_ARTYKULU", referencedColumnName="ID_ARTYKULU"),
                    @JoinColumn(updatable=false,insertable=false, name="ID_CENY_DOM", referencedColumnName="ID_CENY"),
            }
    )
    private Price price;

    public Price getPrice() {
        return price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Price price) {
        this.price = price;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}