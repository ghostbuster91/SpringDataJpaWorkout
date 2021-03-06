package ghostbuster.springDataJpaWorkout.tests.impl;

import ghostbuster.springDataJpaWorkout.model.bank.*;
import ghostbuster.springDataJpaWorkout.tests.AbstractTestExecutor;
import ghostbuster.springDataJpaWorkout.ApplicationConfiguration;
import ghostbuster.springDataJpaWorkout.dao.bank.PermissionRepository;
import ghostbuster.springDataJpaWorkout.dao.bank.TransactionRepository;
import ghostbuster.springDataJpaWorkout.dao.bank.UserAccountRepository;
import ghostbuster.springDataJpaWorkout.utils.NonPersistClass;
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

import java.math.BigDecimal;
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
    public void testB_can_add_entity_to_dataBase(){
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
    public void testD_should_find_added_entity(){
        UserAccount saved = userRepository.save(new UserAccount("Janek", "Asd"));
        UserAccount result = userRepository.findByName("Janek");

        assertThat(result).isEqualTo(saved);
    }

    @Test
    @Transactional(readOnly = false)
    @Rollback(false)
    public void testEA_update_entity() {
        //given:
        UserAccount saved = userRepository.save(new UserAccount("Jan", "asdasd"));
        assertThat(userRepository.findAll()).hasSize(1);
        final Long savedId = saved.getId();

        //when:
        saved.setName("Janusz");
        //we don't need to call explicitly repository.save, changes will be persisted after transaction committed
    }

    @Test
    @Transactional(readOnly = false)
    @Rollback(false)
    public void testEB_update_entity() {
        //then:
        assertThat(userRepository.findAll()).hasSize(1);
        assertThat(userRepository.findByName("Janusz")).isNotNull();

        //clean
        cleanAllRepositories();
    }

    @Test
    @Transactional(readOnly = false)
    public void testF_should_entity_be_cached(){
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
    public void testGA_entity_should_be_removed_from_cache(){
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
    public void testGB_entity_should_be_removed_from_cache(){
        //then
        Cache cache = cacheManager.getCache("byUsername");
        assertThat(cache.get("Janek")).isNull();

        //clean
        cleanAllRepositories();
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
    public void testLA_transactional_property_should_not_be_persist() {
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
        cleanAllRepositories();
    }

    @Ignore
    @Test
    @Transactional(readOnly = false)
    public void testM_transactional_property_can_be_injected_from_outside() {
        //TODO: configure aspectJ or Hibernate event listener
        UserAccount ua = new UserAccount("kasper", "qwe");
        ua = userRepository.save(ua);

        ua = userRepository.findByName("kasper");
        assertThat(ua.getInjectableProperty()).isNotNull();
    }

    @Test
    @Transactional(readOnly = false)
    @Rollback(false)
    public void testMA_delete_entity_from_collection() {
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
    public void testMB_delete_entity_from_collection(){
        UserAccount ua = userRepository.findByName("albercik");

        //we have to remove the connections from both entities
        ua.getPermissions().stream().forEach(p -> p.setAccount(null));
        ua.getPermissions().removeAll(ua.getPermissions());
        ua = userRepository.save(ua);
    }

    @Test
    @Transactional(readOnly = false)
    @Rollback(false)
    public void testMC_delete_entity_from_collection() {
        //then
        assertThat(userRepository.findByName("albercik").getPermissions()).isEmpty();
        assertThat(permissionRepository.findAll()).isNotEmpty(); // The related child entity is still in repository!

        //clean
        cleanAllRepositories();
    }

    @Test
    @Transactional(readOnly = false)
    @Rollback(false)
    public void testNA_entity_should_appeared_in_proper_repository_when_added_as_a_child() {
        UserAccount ua = new UserAccount("albercik", "katakumba");
        Transaction transaction = new Transaction();

        ua.getTransactions().add(transaction);
        userRepository.save(ua);
    }

    @Test
    @Transactional(readOnly = false)
    @Rollback(false)
    public void testNB_entity_should_appeared_in_proper_repository_when_added_as_a_child() {
        //then
        assertThat(transactionRepository.findAll()).isNotEmpty();

        //clean
        cleanAllRepositories();
    }

    @Test
    @Transactional(readOnly = false)
    @Rollback(false)
    public void testOA_delete_entity_from_collection_using_orphanRemoval() {
        UserAccount ua = new UserAccount("albercik", "katakumba");
        Transaction transaction = new Transaction();

        ua.getTransactions().add(transaction);
        userRepository.save(ua);
    }

    @Test
    @Transactional(readOnly = false)
    @Rollback(false)
    public void testOB_delete_entity_from_collection_using_orphanRemoval() {
        UserAccount ua = userRepository.findByName("albercik");
        ua.getTransactions().remove(0);
    }

    @Test
    @Transactional(readOnly = false)
    @Rollback(false)
    public void testOC_delete_entity_from_collection_using_orphanRemoval() {
        //then
        assertThat(transactionRepository.findAll()).isEmpty(); // The child entity was removed as soon as it was no longer referenced from the "parent" entity,

        //clean
        cleanAllRepositories();
    }

    @Test
    @Transactional(readOnly = false)
    public void testP_prePersist_annotation() {
        Transaction transaction = new Transaction();

        transaction = transactionRepository.save(transaction);
        assertThat(transaction.getCreatedDateTime()).isNotNull();
    }

    @Test
    @Transactional(readOnly = false)
    @Rollback(false)
    public void testRA_preUpdate_annotation(){
        //given
        Transaction transaction = new Transaction();
        transaction.setAmount(new BigDecimal(11));
        transaction = transactionRepository.save(transaction);
    }

    @Test
    @Transactional(readOnly = false)
    @Rollback(false)
    public void testRB_preUpdate_annotation(){
        //when
        Transaction transaction = transactionRepository.findAll().get(0);
        transaction.setAmount(new BigDecimal(112));
    }

    @Test
    @Transactional(readOnly = false)
    @Rollback(false)
    public void testRC_preUpdate_annotation(){
        //than
        Transaction transaction = transactionRepository.findAll().get(0);
        assertThat(transaction.getLastModifiedDateTime()).isNotNull();

        //clean
        cleanAllRepositories();
    }

    @Transactional(readOnly = false)
    private void cleanAllRepositories(){
        transactionRepository.delete(transactionRepository.findAll());
        userRepository.delete(userRepository.findAll());
        permissionRepository.delete(permissionRepository.findAll());
    }
}
