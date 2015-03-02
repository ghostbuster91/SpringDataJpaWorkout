package ghostbuster.springDataJpaWorkout.model.shop;

import com.sun.istack.internal.NotNull;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PricePK implements Serializable {

    @Column(name = "ID_ARTYKULU")
    @NotNull
    private Long articleId;

    @Column(name = "ID_CENY")
    @NotNull
    private Long priceId;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }
}
