package ghostbuster.dao.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class UserAccount {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable=false, unique=true, name="NAME")
    private String name;

    @Column(nullable=false, name="PASSWORD")
    private String password;

//    @OneToOne(cascade= CascadeType.ALL)
//    private AccountDetails details;
//
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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
