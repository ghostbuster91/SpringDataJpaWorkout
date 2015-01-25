package ghostbuster.springDataJpaWorkout.model;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.math.BigDecimal;

@Entity
public class Transaction extends AbstractEntityWithAutoId {

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime createdDateTime;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime lastModifiedDateTime;


    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public LocalDateTime getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    @PrePersist
    private void setCreatedDateTime(){
        createdDateTime = LocalDateTime.now();
    }

    @PreUpdate
    private void setLastModifiedDateTime(){
        lastModifiedDateTime = LocalDateTime.now();
    }
}
