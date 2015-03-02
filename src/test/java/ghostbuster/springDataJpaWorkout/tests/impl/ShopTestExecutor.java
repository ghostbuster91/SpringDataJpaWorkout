package ghostbuster.springDataJpaWorkout.tests.impl;

import ghostbuster.springDataJpaWorkout.ApplicationConfiguration;
import ghostbuster.springDataJpaWorkout.dao.shop.ArticleRepository;
import ghostbuster.springDataJpaWorkout.dao.shop.PriceRepository;
import ghostbuster.springDataJpaWorkout.model.shop.Article;
import ghostbuster.springDataJpaWorkout.model.shop.Price;
import ghostbuster.springDataJpaWorkout.model.shop.PricePK;
import ghostbuster.springDataJpaWorkout.tests.AbstractTestExecutor;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = ApplicationConfiguration.class)
public class ShopTestExecutor extends AbstractTestExecutor{

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    ArticleRepository articleRepository;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    PriceRepository priceRepository;

    @Test
    @Rollback(false)
    public void A_testCreatingArticle() throws Exception {
        Article article = new Article();
        article.setName("Apple");
        article.setDescription("One apple per day keeps doctor away");
        article.setId((long) 1);
        articleRepository.save(article);

        assertThat(articleRepository.findWithSimilarDescription("doctor")).isNotEmpty();
    }

    @Test
    @Rollback(false)
    public void B_testCreatingPriceForArticle() throws Exception {
        Price price = new Price();
        PricePK key = new PricePK();
        key.setArticleId((long) 1);
        key.setPriceId((long) 1);

        price.setId(key);
        priceRepository.save(price);

        assertThat(priceRepository.findPriceByArticleIdAndPriceId((long)1, (long) 1)).isNotNull();
    }
}
