package ghostbuster.springDataJpaWorkout.model.shop;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "CENA_ARTYKULU")
public class Price implements Serializable {

    @EmbeddedId
    private PricePK id;

    @Column(name = "CENA_NETTO")
    private BigDecimal netPrice;

    @Column(name = "CENA_BRUTTO")
    private BigDecimal grossPrice;


    public PricePK getId() {
        return id;
    }


    public void setId(PricePK id) {
        this.id = id;
    }

    public BigDecimal getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(BigDecimal netPrice) {
        this.netPrice = netPrice;
    }

    public BigDecimal getGrossPrice() {
        return grossPrice;
    }

    public void setGrossPrice(BigDecimal grossPrice) {
        this.grossPrice = grossPrice;
    }

    @Override
    public String toString() {
        return "Price{" +
                "priceId=" + id +
                ", netPrice=" + netPrice +
                ", grossPrice=" + grossPrice +
                '}';
    }
}
