 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author eltet
 */
@Entity
public class KeysState implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
/////////////////////////////////////////////////////
     
     private int IDstate;
     private String stateDescription;

    public KeysState(int IDstate, String stateDescription) {
        this.IDstate = IDstate;
        this.stateDescription = stateDescription;
    }

    
    
    public KeysState() {
        
    }

    public int getIDstate() {
        return IDstate;
    }

    public String getStateDescription() {
        return stateDescription;
    }

    public void setIDstate(int IDstate) {
        this.IDstate = IDstate;
    }

    public void setStateDescription(String stateDescription) {
        this.stateDescription = stateDescription;
    }
    
  /////////////////////////////////////////////////////////////
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KeysState)) {
            return false;
        }
        KeysState other = (KeysState) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.KeysState[ id=" + id + " ]";
    }
    
}
