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
public class KeyState {
    
    private int IDstate;
    private String stateDescription;

    public KeyState(int IDstate, String stateDescription) {
        this.IDstate = IDstate;
        this.stateDescription = stateDescription;
    }

    
    
    public KeyState() {
        
    }

    public int getIDstate() {
        return IDstate;
    }

    public String getStateDescription() {
        return stateDescription;
    }

    public void setIDstate(int IDstate) {
        this.IDstate = IDstate;
    }

    public void setStateDescription(String stateDescription) {
        this.stateDescription = stateDescription;
    }
    
    
}
