/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ghostbuster.springDataJpaWorkout.model.bank;

import javax.persistence.*;

@Entity
public class AccountDetails extends AbstractEntityWithAutoId{

    @OneToOne(fetch= FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private UserAccount userAccount;

    private String fullName;

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}