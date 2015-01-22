package ghostbuster.dao.impl;

import ghostbuster.dao.model.UserAccount;
import org.h2.engine.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    @Cacheable("byUsername")
    UserAccount findByName(String name);


    @Override
    @CacheEvict("byUsername")
    <S extends UserAccount> S save(S entity);
}
