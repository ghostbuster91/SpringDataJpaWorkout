package ghostbuster.springDataJpaWorkout.dao.shop;

import ghostbuster.springDataJpaWorkout.model.shop.Price;
import ghostbuster.springDataJpaWorkout.model.shop.PricePK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface PriceRepository extends PagingAndSortingRepository<Price,PricePK> {

    @SuppressWarnings("JpaQlInspection")
    @Query(value="SELECT p FROM Price p WHERE p.id.articleId = :articleId and p.id.priceId = :priceId")
    public Price findPriceByArticleIdAndPriceId(@Param("articleId") Long articleId, @Param("priceId") Long priceId);
}
