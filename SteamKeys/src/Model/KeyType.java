/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author eltet
 */
public class KeyType {
    
    private int IDtype;
    private String TipeDescription; 

    public KeyType(int IDtype, String TipeDescription) {
        this.IDtype = IDtype;
        this.TipeDescription = TipeDescription;
    }

    public KeyType() {
    }

    public int getIDtype() {
        return IDtype;
    }

    public String getTipeDescription() {
        return TipeDescription;
    }

    public void setIDtype(int IDtype) {
        this.IDtype = IDtype;
    }

    public void setTipeDescription(String TipeDescription) {
        this.TipeDescription = TipeDescription;
    }
    
    
    
}
