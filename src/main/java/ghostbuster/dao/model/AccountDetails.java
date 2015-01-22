/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ghostbuster.dao.model;

import javax.persistence.*;

@Entity
public class AccountDetails extends AbstractEntityWithAutoId{

    @OneToOne(fetch= FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private UserAccount userAccount;

    private String fullName;


}