/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
    
    @Column(name = "ingameprice")
    private int keyprice;
    
    @Column(name = "ganancia")
    private int ganancia;
    
    @Column(name = "balancestore")
    private int balancestore;
    
    @OneToMany(mappedBy = "trade" ,cascade = CascadeType.ALL)
    private List <Key> keytraded = new ArrayList();
    
    @OneToMany(mappedBy = "trade" ,cascade = CascadeType.ALL, orphanRemoval = true)
    private List <SteamItem> items = new ArrayList();
 
    @Column(name = "cantkey")
    private int cantkey;

    public void setCantkey(int cantkey) {
        this.cantkey = cantkey;
    }
    
    public int getCantkey() {
        return cantkey;
    }

    public Trade(Date dateoftrade, int priceinstore, int ganancia, int balancestore, List<Key> keytraded) {
        this.dateoftrade = dateoftrade;
        this.priceinstore = priceinstore;
        this.ganancia = ganancia;
        this.balancestore = balancestore;
        this.keytraded = keytraded;
    }
    public void CleanITems(){
        this.items = new ArrayList();
    }
    public Trade() {
    }

    public Trade(Date dateoftrade) {
        this.dateoftrade = dateoftrade;
    }
    public Trade(long id){
        this.id = id;
    }

    public Trade(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return id;
    }
    public int getGanancia(){
        return ganancia;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public void AddKey(Key k){
        this.keytraded.add(k);
    }
    public void DeleteKey(Key k){
        this.keytraded.remove(k);
    }
    public void AddItem(SteamItem item){
        this.items.add(item);
    }
    public void DeleteItem(SteamItem item){
        this.items.remove(item);
    }
    public void setKeyprice(int keyprice) {
        this.keyprice = keyprice;
    }

    public int getKeyprice() {
        return keyprice;
    }
    
    public void setItems(List<SteamItem> items) {
        this.items = items;
    }

    public List<SteamItem> getItems() {
        return items;
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

    public void setGanancia(int ganancia){
        this.ganancia = ganancia;
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
