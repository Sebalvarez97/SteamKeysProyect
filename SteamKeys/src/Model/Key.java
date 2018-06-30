/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 *
 * @author eltet
 */
public class Key {
    
    private double buyprice;
    private KeyType type;
    private Date buydate;
    
    private KeyState state;

    public double getBuyprice() {
        return buyprice;
    }

    public KeyType getType() {
        return type;
    }

    public Date getBuydate() {
        return buydate;
    }

    public KeyState getState() {
        return state;
    }

    public void setBuyprice(double buyprice) {
        this.buyprice = buyprice;
    }

    public void setType(KeyType type) {
        this.type = type;
    }

    public void setBuydate(Date buydate) {
        this.buydate = buydate;
    }

    public void setState(KeyState state) {
        this.state = state;
    }

    public Key() {
        
    }

    public Key(double buyprice, KeyType type, Date buydate, KeyState state) {
        this.buyprice = buyprice;
        this.type = type;
        this.buydate = buydate;
        this.state = state;
    }
    
    
    
    
}
