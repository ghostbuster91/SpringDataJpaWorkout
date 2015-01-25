package ghostbuster.springDataJpaWorkout.model;

import javax.persistence.*;

@Entity
public class Permission extends AbstractEntityWithAutoId {

    @Enumerated(EnumType.STRING)
    @Column(name="TYPE", nullable=false)
    private PermissionType type;

    @ManyToOne(cascade= CascadeType.ALL)
    private UserAccount account;

    public void setId(Long id){
        this.id = id;
    }

    public Permission(PermissionType type) {
        this.type = type;
    }

    public PermissionType getType() {
        return type;
    }

    public void setType(PermissionType type) {
        this.type = type;
    }

    public UserAccount getAccount() {
        return account;
    }

    public void setAccount(UserAccount account) {
        this.account = account;
    }
}
