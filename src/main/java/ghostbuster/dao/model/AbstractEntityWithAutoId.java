/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ghostbuster.dao.model;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractEntityWithAutoId implements Serializable {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
