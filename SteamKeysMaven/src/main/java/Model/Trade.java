/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author eltet
 */
@Entity
@Table(name = "trade")
public class Trade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "dateoftrade")
    private Date dateoftrade;
    
    @Column(name = "priceinstore")
    private int priceinstore;
    
    @OneToMany
    @JoinColumn(name = "items")
    private List<SteamItem> items;
    
    @Column(name = "balancestore")
    private int balancestore;
    
    @OneToMany
    @JoinColumn(name = "keytraded")
    private List <Key> keytraded;

    public Trade(Date dateoftrade, int priceinstore, List<SteamItem> items, int balancestore, List<Key> keytraded) {
        this.dateoftrade = dateoftrade;
        this.priceinstore = priceinstore;
        this.items = items;
        this.balancestore = balancestore;
        this.keytraded = keytraded;
    }

    public Trade() {
    }

    public Trade(Date dateoftrade) {
        this.dateoftrade = dateoftrade;
    }
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void AddKey(Key k){
        this.keytraded.add(k);
    }    
    public void AddItem(SteamItem i){
        this.items.add(i);
    }

    public void setItems(List<SteamItem> items) {
        this.items = items;
    }

    public void setKeytraded(List<Key> keytraded) {
        this.keytraded = keytraded;
    }
    
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Date getDateoftrade() {
        return dateoftrade;
    }

    public int getPriceinstore() {
        return priceinstore;
    }

    public List<SteamItem> getItems() {
        return items;
    }

    public int getBalancestore() {
        return balancestore;
    }

    public List<Key> getKeytraded() {
        return keytraded;
    }

    public void setDateoftrade(Date dateoftrade) {
        this.dateoftrade = dateoftrade;
    }

    public void setPriceinstore(int priceinstore) {
        this.priceinstore = priceinstore;
    }

    public void setBalancestore(int balancestore) {
        this.balancestore = balancestore;
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
        if (!(object instanceof Trade)) {
            return false;
        }
        Trade other = (Trade) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Trade[ id=" + id + " ]";
    }
    
}
