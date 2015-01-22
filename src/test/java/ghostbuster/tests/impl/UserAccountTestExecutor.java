package ghostbuster.tests.impl;

import ghostbuster.dao.ApplicationConfiguration;
import ghostbuster.dao.model.UserAccount;
import ghostbuster.dao.impl.UserAccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = ApplicationConfiguration.class)
public class UserAccountTestExecutor {

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private UserAccountRepository userRepository;

    @Autowired
    private CacheManager cacheManager;

    @Test
    public void repository_is_not_null() {
        assertThat(userRepository).isNotNull();
    }

    @Test
    public void test_can_add_entity_to_dataBase() throws Exception {
        userRepository.save(new UserAccount("Jan", "qweASD"));

        assertThat(userRepository.findAll()).hasSize(1);
    }

    @Test
    public void test_should_entity_be_cached() throws Exception {

        UserAccount ua = new UserAccount("Janek", "Asd");
        ua = userRepository.save(ua);
        UserAccount result = userRepository.findByName("Janek");
        assertThat(result).isEqualTo(ua);

        // Verify entity cached
        Cache cache = cacheManager.getCache("byUsername");
        Cache.ValueWrapper wrapper = cache.get("Janek");
        assertThat(wrapper.get()).isEqualTo(ua);

    }

    @Test
    public void testB_Get() {
        UserAccount user = userRepository.findOne((long) 124123);
        assertThat(user).isNull();
    }

    @Test
    public void testF_UpdateRollbacked() {
//        userRepository.update(1, "kazik");
        userRepository.save(new UserAccount("Jan","asdasd"));
    }

}
