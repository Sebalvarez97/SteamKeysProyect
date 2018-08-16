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
@Table(name = "item")
public class SteamItem implements Serializable {

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
    
    @Column(name = "cant")
    private int cant;
    
    @Column(name = "storeprice")
    private double storeprice;
    
    @Column(name = "sellprice")
    private double sellprice;

    public SteamItem() {
    }

    public SteamItem(int cant, double storeprice, double sellprice) {
        this.cant = cant;
        this.storeprice = storeprice;
        this.sellprice = sellprice;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    public void setStoreprice(double storeprice) {
        this.storeprice = storeprice;
    }

    public void setSellprice(double sellprice) {
        this.sellprice = sellprice;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getCant() {
        return cant;
    }

    public double getStoreprice() {
        return storeprice;
    }

    public double getSellprice() {
        return sellprice;
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
        if (!(object instanceof SteamItem)) {
            return false;
        }
        SteamItem other = (SteamItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.SteamItem[ id=" + id + " ]";
    }
    
}
