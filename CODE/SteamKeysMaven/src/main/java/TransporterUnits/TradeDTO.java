/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TransporterUnits;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author eltet
 */
public class TradeDTO extends DTO{

    
    private Date dateoftrade;
    private int priceinstore;
    private int ingameprice;
    private int balance;
    private int ganancia;
    private int cantkeys;
    private List<KeyDTO> keys = new ArrayList();
    private List<SteamItemDTO> items = new ArrayList();

    public TradeDTO(int priceinstore, int balance) {
        this.priceinstore = priceinstore;
        this.balance = balance;
    }
   public TradeDTO(int priceinstore, int balance, List<KeyDTO> keys) {
        this.priceinstore = priceinstore;
        this.balance = balance;
        this.keys = keys;
    }
    public TradeDTO() {
    }

    public Date getDateoftrade() {
        return dateoftrade;
    }

    public int getPriceinstore() {
        return priceinstore;
    }

    public int getIngameprice() {
        return ingameprice;
    }

    public int getBalance() {
        return balance;
    }

    public int getGanancia() {
        return ganancia;
    }

    public int getCantkeys() {
        return cantkeys;
    }

    public List<KeyDTO> getKeys() {
        return keys;
    }

    public List<SteamItemDTO> getItems() {
        return items;
    }

    public void setDateoftrade(Date dateoftrade) {
        this.dateoftrade = dateoftrade;
    }

    public void setPriceinstore(int priceinstore) {
        this.priceinstore = priceinstore;
    }

    public void setIngameprice(int ingameprice) {
        this.ingameprice = ingameprice;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setGanancia(int ganancia) {
        this.ganancia = ganancia;
    }

    public void setCantkeys(int cantkeys) {
        this.cantkeys = cantkeys;
    }

    public void setKeys(List<KeyDTO> keys) {
        this.keys = keys;
    }

    public void setItems(List<SteamItemDTO> items) {
        this.items = items;
    }
    public void AddKey(KeyDTO key){
        this.keys.add(key);
    }
    public void AddItem(SteamItemDTO item){
        this.items.add(item);
    }
    
    public void DeleteKey(KeyDTO key){
        this.keys.remove(key);
    }
    public void DeleteItem(SteamItemDTO item){
        this.items.remove(item);
    }
    @Override
    public int compareTo(DTO t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
