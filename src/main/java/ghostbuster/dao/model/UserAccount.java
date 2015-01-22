package ghostbuster.dao.model;

import javax.persistence.*;

@Entity
public class UserAccount extends AbstractEntityWithAutoId{

    @Column(nullable=false, unique=true, name="NAME")
    private String name;

    @Column(nullable=false, name="PASSWORD")
    private String password;

    @OneToOne(cascade= CascadeType.ALL)
    private AccountDetails details;
//

    public AccountDetails getDetails() {
        return details;
    }

    public void setDetails(AccountDetails details) {
        this.details = details;
    }

//    @OneToMany(fetch = FetchType.LAZY)
//    private Set<Role> roles = new HashSet<Role>();
//
//    @OneToMany(fetch = FetchType.EAGER, cascade= CascadeType.ALL, mappedBy="account")
//    private Set<Permission> permissions = new HashSet<Permission>();

    public UserAccount() {
    }


    public UserAccount(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAccount that = (UserAccount) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
