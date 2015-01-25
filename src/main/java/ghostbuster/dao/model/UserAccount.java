package ghostbuster.dao.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @OneToMany(fetch = FetchType.LAZY)
    private List<Role> roles = new ArrayList<Role>();

    @OneToMany(fetch = FetchType.EAGER, cascade= CascadeType.ALL, mappedBy="account")
    private Set<Permission> permissions = new HashSet<Permission>();

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
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", details=" + details +
                '}';
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
