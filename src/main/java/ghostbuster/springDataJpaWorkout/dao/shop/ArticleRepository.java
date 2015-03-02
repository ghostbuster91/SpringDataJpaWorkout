package ghostbuster.springDataJpaWorkout.dao.shop;

import ghostbuster.springDataJpaWorkout.model.shop.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {

    @SuppressWarnings("JpaQlInspection")
    @Query(value="SELECT p FROM Article p WHERE p.description LIKE %:searchKeyword%")
    public List<Article> findWithSimilarDescription(@Param("searchKeyword") String searchKeyword);
}