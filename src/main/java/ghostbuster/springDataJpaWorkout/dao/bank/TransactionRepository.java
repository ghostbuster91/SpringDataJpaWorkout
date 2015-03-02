package ghostbuster.springDataJpaWorkout.dao.bank;

import ghostbuster.springDataJpaWorkout.model.bank.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
