/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TransporterUnits;

import java.util.Date;

/**
 *
 * @author eltet
 */
public class KeyDTO extends DTO{
    
    private long id;
    private Date buydate;
    //private double buyprice;
    private String type;
    private String state;
    
    public KeyDTO() {
    }

    public KeyDTO(String type, String state) {
//        this.buyprice = buyprice;
        this.type = type;
        this.state = state;
    }

    public long getId() {
        return id;
    }
    public Date getbuydate(){
        return this.buydate;
    }
    public void setbuydate(Date buydate){
        this.buydate = buydate;
    }
//    public double getBuyprice() {
//        return buyprice;
//    }

    public String getType() {
        return type;
    }

    public String getState() {
        return state;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public void setBuyprice(double buyprice) {
//        this.buyprice = buyprice;
//    }

    public void setType(String type) {
        this.type = type;
    }

    public void setState(String state) {
        this.state = state;
    }
   
    
    
}
