/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author eltet
 */
@Entity
@Table(name = "keys")
public class Key implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "buyprice")
    private double buyprice;
    @Column(name = "keystate")
    private String keyState;
    @Column(name = "keytype")
    private String keyType;
    
    public Key(){
        
    }

    public Key(double buyprice, String keyState, String keyType) {
        this.buyprice = buyprice;
        this.keyState = keyState;
        this.keyType = keyType;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
///////////////////////////////////////////////////
    public double getBuyprice() {
        return buyprice;
    }

    public String getKeyState() {
        return keyState;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setBuyprice(double buyprice) {
        this.buyprice = buyprice;
    }

    public void setKeyState(String keyState) {
        this.keyState = keyState;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }
    
    /////////////////////////
    
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
        if (!(object instanceof Key)) {
            return false;
        }
        Key other = (Key) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Key[ id=" + id + " ]";
    }
    
}
