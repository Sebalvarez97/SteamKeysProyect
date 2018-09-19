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
    private String type;
    private String state;
    private Long tradeid;
    
    public KeyDTO() {
    }

    public KeyDTO(String type, String state) {
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
    public String getType() {
        return type;
    }
 
    public Date getBuydate() {
        return buydate;
    }

    public Long getTrade() {
        return tradeid;
    }

    public void setBuydate(Date buydate) {
        this.buydate = buydate;
    }

    public void setTrade(Long trade) {
        this.tradeid = trade;
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
    
    
    @Override
    public int compareTo(DTO dto) {
        KeyDTO key = (KeyDTO) dto;
        if (buydate.after(key.getbuydate())) {
                return -1;
            }
            if (buydate.before(key.getbuydate())) {
                return 1;
            }
            return 0;
    }

    
}
