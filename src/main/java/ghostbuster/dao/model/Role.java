package ghostbuster.dao.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Role extends AbstractEntityWithAutoId{

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private RoleType type;

    @Column( nullable=false)
    private Byte priority;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date validThru;


    public Role() {
    }

    public Role(RoleType type, Byte priority, Date validThru) {
        this.type = type;
        this.priority = priority;
        this.validThru = validThru;
    }

    public RoleType getType() {
        return type;
    }

    public void setType(RoleType type) {
        this.type = type;
    }

    public Byte getPriority() {
        return priority;
    }

    public void setPriority(Byte priority) {
        this.priority = priority;
    }

    public Date getValidThru() {
        return validThru;
    }

    public void setValidThru(Date validThru) {
        this.validThru = validThru;
    }
}
