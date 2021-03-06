/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    public SteamItem(Long id) {
        this.id = id;
    }
        
    @Column(name = "ispending")
    private boolean pending = false;
    
    @Column(name = "storeprice")
    private int storeprice;
    
    @Column(name = "sellprice")
    private int sellprice;
    
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_id")
    private Trade trade;

    public SteamItem() {
    }
    
    public SteamItem(int cant, int storeprice, int sellprice) {
        this.storeprice = storeprice;
        this.sellprice = sellprice;
    }

    public void setStoreprice(int storeprice) {
        this.storeprice = storeprice;
    }

    public void setSellprice(int sellprice) {
        this.sellprice = sellprice;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean isPending() {
        return pending;
    }

    public Trade getTrade() {
        return trade;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }

    public int getStoreprice() {
        return storeprice;
    }

    public int getSellprice() {
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
