/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TransporterUnits;

/**
 *
 * @author eltet
 */
public class SteamItemDTO extends DTO{
    private Long id;
    private boolean ispending;
    private int storeprice;
    private int sellprice;
    private Long trade_id;

    public SteamItemDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SteamItemDTO(Long id) {
        this.id = id;
    }
    
    public SteamItemDTO(boolean ispending, int storeprice, int sellprice) {
        this.ispending = ispending;
        this.storeprice = storeprice;
        this.sellprice = sellprice;
    }

    public boolean isIspending() {
        return ispending;
    }

    public int getStoreprice() {
        return storeprice;
    }

    public int getSellprice() {
        return sellprice;
    }

    public Long getTrade_id() {
        return trade_id;
    }

    public void setIspending(boolean ispending) {
        this.ispending = ispending;
    }

    public void setStoreprice(int storeprice) {
        this.storeprice = storeprice;
    }

    public void setSellprice(int sellprice) {
        this.sellprice = sellprice;
    }

    public void setTrade_id(Long trade_id) {
        this.trade_id = trade_id;
    }
    
    @Override
    public int compareTo(DTO dto) {
        SteamItemDTO itemdto = (SteamItemDTO) dto;
        if (storeprice < itemdto.getStoreprice()) {
                return -1;
            }
            if (sellprice > itemdto.getSellprice()) {
                return 1;
            }
            return 0;
    }
    
    
    
    
    
}
