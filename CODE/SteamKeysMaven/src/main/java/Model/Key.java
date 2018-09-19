/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "key")
public class Key implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "buydate")
    private Date buydate;
    
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "keystate")
    private KeyState keyState;
    
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "keytype")
    private KeyType keyType;
    
    @ManyToOne (fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "trade_id")
    private Trade trade;
    
    public Key(){
        
    }

    public Key(KeyState keyState, KeyType keyType) {
//        this.buyprice = buyprice;
        this.keyState = keyState;
        this.keyType = keyType;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
///////////////////////////////////////////////////
//    public double getBuyprice() {
//        return buyprice;
//    }

    public KeyState getKeyState() {
        return keyState;
    }

    public KeyType getKeyType() {
        return keyType;
    }

//    public void setBuyprice(double buyprice) {
//        this.buyprice = buyprice;
//    }

    public void setKeyState(KeyState keyState) {
        this.keyState = keyState;
    }

    public void setKeyType(KeyType keyType) {
        this.keyType = keyType;
    }
    
    public Date getBuyDate(){
        return this.buydate;
    }
    
    public void setBuyDate(Date date){
        this.buydate = date;
    }

    public Date getBuydate() {
        return buydate;
    }

    public Trade getTrade() {
        return trade;
    }

    public void setBuydate(Date buydate) {
        this.buydate = buydate;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
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
