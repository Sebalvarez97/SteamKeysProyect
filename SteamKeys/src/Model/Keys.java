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
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Table;

/**
 *
 * @author eltet
 */
@Entity
@Table(name = "Keys")
public class Keys implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    //////////////////////////////
    @Column(name = "buyprice")
    private double buyprice;
    @Column(name = "type")
    private KeysType type;
    @Column(name = "buydate")
    private Date buydate;
    @Column(name = "state")
    private KeysState state;

    public double getBuyprice() {
        return buyprice;
    }

    public KeysType getType() {
        return type;
    }

    public Date getBuydate() {
        return buydate;
    }

    public KeysState getState() {
        return state;
    }

    public void setBuyprice(double buyprice) {
        this.buyprice = buyprice;
    }

    public void setType(KeysType type) {
        this.type = type;
    }

    public void setBuydate(Date buydate) {
        this.buydate = buydate;
    }

    public void setState(KeysState state) {
        this.state = state;
    }

    public Keys() {
        
    }

    public Keys(double buyprice, KeysType type, Date buydate, KeysState state) {
        this.buyprice = buyprice;
        this.type = type;
        this.buydate = buydate;
        this.state = state;
    }
/////////////////////////////////////////////////
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Keys)) {
            return false;
        }
        Keys other = (Keys) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Keys[ id=" + id + " ]";
    }
    
}
