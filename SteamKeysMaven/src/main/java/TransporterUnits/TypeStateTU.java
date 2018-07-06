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
public class TypeStateTU extends Transporter{
    
    private long id;
    private String description;

    public TypeStateTU(long id, String description) {
        this.id = id;
        this.description = description;
    }

    public TypeStateTU() {
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
