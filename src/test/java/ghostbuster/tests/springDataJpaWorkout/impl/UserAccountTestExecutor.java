package ghostbuster.tests.springDataJpaWorkout.impl;

import ghostbuster.springDataJpaWorkout.ApplicationConfiguration;
import ghostbuster.springDataJpaWorkout.dao.PermissionRepository;
import ghostbuster.springDataJpaWorkout.dao.TransactionRepository;
import ghostbuster.springDataJpaWorkout.dao.UserAccountRepository;
import ghostbuster.springDataJpaWorkout.model.*;
import ghostbuster.springDataJpaWorkout.utils.NonPersistClass;
import ghostbuster.tests.springDataJpaWorkout.AbstractTestExecutor;
import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = ApplicationConfiguration.class)
public class UserAccountTestExecutor extends AbstractTestExecutor {

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private UserAccountRepository userRepository;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private PermissionRepository permissionRepository;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private TransactionRepository transactionRepository;

    @Autowired
    private CacheManager cacheManager;

    @After
    public void clearCache() {
        cacheManager.getCache("byUsername").clear();
    }

    @Test
    public void repository_is_not_null() {
        assertThat(userRepository).isNotNull();
    }

    @Test
    @Transactional(readOnly = true)
    public void testA_should_findAll_return_empty_list() {
        assertThat(userRepository.findAll()).isEmpty();
    }

    @Test
    @Transactional(readOnly = false)
    public void testB_can_add_entity_to_dataBase() throws Exception {
        userRepository.save(new UserAccount("Jan", "qweASD"));

        assertThat(userRepository.findAll()).hasSize(1);
    }

    @Test
    @Transactional(readOnly = true)
    public void testC_initial_repository_should_be_empty() {
        UserAccount user = userRepository.findOne((long) 124123);
        assertThat(user).isNull();
    }

    @Test
    @Transactional(readOnly = false)
    public void testD_should_find_added_entity() throws Exception {
        UserAccount saved = userRepository.save(new UserAccount("Janek", "Asd"));
        UserAccount result = userRepository.findByName("Janek");

        assertThat(result).isEqualTo(saved);
    }

    @Test
    @Transactional(readOnly = false)
    public void testE_update_entity() {
        //given:
        UserAccount saved = userRepository.save(new UserAccount("Jan", "asdasd"));
        assertThat(userRepository.findAll()).hasSize(1);
        final Long savedId = saved.getId();

        //when:
        saved.setName("Janusz");
        userRepository.save(saved);

        //then:
        assertThat(userRepository.findByName("Janusz")).isNotNull();
        assertThat(userRepository.findAll()).hasSize(1);
        assertThat(userRepository.findByName("Janusz").getId()).isEqualTo(savedId);
    }

    @Test
    @Transactional(readOnly = false)
    public void testF_should_entity_be_cached() {
        //given
        UserAccount ua = new UserAccount("Janek", "Asd");
        ua = userRepository.save(ua);

        //when
        UserAccount result = userRepository.findByName("Janek");

        //then
        Cache cache = cacheManager.getCache("byUsername");
        Cache.ValueWrapper wrapper = cache.get("Janek");
        assertThat(wrapper.get()).isEqualTo(ua).isEqualTo(result);

    }

    @Test
    @Rollback(false)
    @Transactional(readOnly = false)
    public void testGA_entity_should_be_removed_from_cache() {
        //given
        UserAccount ua = new UserAccount("Janek", "StareHaslo");
        ua = userRepository.save(ua);

        //when
        UserAccount result = userRepository.findByName("Janek");
        result.setPassword("NoweHaslo");
        userRepository.save(result);
    }

    @Test
    @Rollback(false)
    @Transactional(readOnly = false)
    public void testGB_entity_should_be_removed_from_cache() throws Exception {
        //then
        Cache cache = cacheManager.getCache("byUsername");
        assertThat(cache.get("Janek")).isNull();

        //clean
        userRepository.delete(userRepository.findByName("Janek"));
    }

    @Test
    @Transactional(readOnly = false)
    public void testH_should_be_able_to_delete_entity() {
        //given
        UserAccount ua = new UserAccount("Janek", "StareHaslo");
        ua = userRepository.save(ua);

        //when
        userRepository.delete(ua);

        //then
        assertThat(userRepository.findAll()).isEmpty();
    }

    @Transactional(readOnly = false)
    @Test
    public void testI_addCollection() {
        //given
        UserAccount ua = new UserAccount("albercik", "katakumba");
        Role role = new Role(RoleType.AUDITOR, new Byte("1"), new Date());

        //when
        ua.getRoles().add(role);
        ua = userRepository.save(ua);
        role = (Role) ua.getRoles().toArray()[0];

        //then
        assertThat(ua.getRoles()).contains(role);
    }

    @Transactional(readOnly = false)
    @Test
    public void testJ_lazyLoading() {
        //given
        UserAccount ua = new UserAccount("kasper", "qwe");
        ua.getRoles().add(new Role(RoleType.AUDITOR, new Byte("1"), new Date()));
        ua = userRepository.save(ua);

        //when
        Role role = ua.getRoles().get(0);

        //then
        assertThat(role).isNotNull();
    }

    @Transactional(readOnly = false)
    @Test
    public void testK_addCollectionBidirectional() {
        //given
        UserAccount ua = new UserAccount("albercik", "katakumba");
        Permission perm = new Permission(PermissionType.CREATION);

        //when
        perm.setAccount(ua);
        perm = permissionRepository.save(perm);

        ua.getPermissions().add(perm);
        ua = userRepository.save(ua);

        //then
        assertThat(ua.getPermissions()).contains(perm);
    }

    @Test
    @Transactional(readOnly = false)
    @Rollback(false)
    public void testLA_transactional_property_should_not_be_persist() throws Exception {
        //given
        UserAccount ua = new UserAccount("albercik", "katakumba");

        //when
        ua.setNonPersistProperty(new NonPersistClass());
        ua = userRepository.save(ua);
    }

    @Test
    @Transactional(readOnly = false)
    @Rollback(false)
    public void testLB_transactional_property_should_not_be_persist() {
        //then
        UserAccount ua = userRepository.findByName("albercik");
        assertThat(ua.getNonPersistProperty()).isNull();

        //clean
        userRepository.delete(userRepository.findAll());
    }

    @Ignore
    @Test
    @Transactional(readOnly = false)
    public void testM_transactional_property_can_be_injected_from_outside() {
        //TODO: configure aspectJ or Hibernate event listener
    }

    @Test
    @Transactional(readOnly = false)
    @Rollback(false)
    public void testMA_delete_entity_from_collection(){
        UserAccount ua = new UserAccount("albercik", "katakumba");
        Permission perm = new Permission(PermissionType.CREATION);

        perm.setAccount(ua);
        perm = permissionRepository.save(perm);

        ua.getPermissions().add(perm);
        ua = userRepository.save(ua);

        assertThat(permissionRepository.findAll()).isNotEmpty();
    }

    @Test
    @Transactional(readOnly = false)
    @Rollback(false)
    public void testMB_delete_entity_from_collection() throws Exception {
        UserAccount ua = userRepository.findByName("albercik");
        ua.getPermissions().remove(0);
        ua = userRepository.save(ua);
    }

    @Test
    @Transactional(readOnly = false)
    @Rollback(false)
    public void testMC_delete_entity_from_collection(){
        //then
        assertThat(permissionRepository.findAll()).isNotEmpty(); // The related child entity is still in repository!

        //clean
        userRepository.delete(userRepository.findAll());
        permissionRepository.delete(permissionRepository.findAll());
    }

    @Test
    @Transactional(readOnly = false)
    @Rollback(false)
    public void testNA_entity_should_appeared_in_proper_repository_when_added_as_a_child() throws Exception {
        UserAccount ua = new UserAccount("albercik", "katakumba");
        Transaction transaction = new Transaction();

        ua.getTransactions().add(transaction);
        userRepository.save(ua);
    }

    @Test
    @Transactional(readOnly = false)
    @Rollback(false)
    public void testNB_entity_should_appeared_in_proper_repository_when_added_as_a_child() throws Exception {
        //then
        assertThat(transactionRepository.findAll()).isNotEmpty();

        //clean
        transactionRepository.delete(transactionRepository.findAll());
        userRepository.delete(userRepository.findAll());
    }

    @Test
    @Transactional(readOnly = false)
    @Rollback(false)
    public void testOA_delete_entity_from_collection_using_orphanRemoval(){
        UserAccount ua = new UserAccount("albercik", "katakumba");
        Transaction transaction = new Transaction();

        ua.getTransactions().add(transaction);
        userRepository.save(ua);
    }

    @Test
    @Transactional(readOnly = false)
    @Rollback(false)
    public void testOB_delete_entity_from_collection_using_orphanRemoval() throws Exception {
        UserAccount ua = userRepository.findByName("albercik");
        ua.getTransactions().remove(0);
        ua = userRepository.save(ua);
    }

    @Test
    @Transactional(readOnly = false)
    @Rollback(false)
    public void testOC_delete_entity_from_collection_using_orphanRemoval(){
        //then
        assertThat(transactionRepository.findAll()).isEmpty(); // The child entity was removed as soon as it was no longer referenced from the "parent" entity,

        //clean
        userRepository.delete(userRepository.findAll());
    }


}
