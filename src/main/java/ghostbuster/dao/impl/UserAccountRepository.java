package ghostbuster.dao.impl;

import ghostbuster.dao.model.UserAccount;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    @Cacheable("byUsername")
    UserAccount findByName(String name);

    List<UserAccount> findAllByName(String name);

    @Override
    @CacheEvict("byUsername")
    <S extends UserAccount> S save(S entity);
}
