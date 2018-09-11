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
public class TypeStateDTO extends DTO{
    
    private long id;
    private String description;

    public TypeStateDTO(long id, String description) {
        this.id = id;
        this.description = description;
    }
    public TypeStateDTO(String description){
        this.description = description;
    }
    public TypeStateDTO() {
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

    @Override
    public int compareTo(DTO t) {
        throw new UnsupportedOperationException("Not supported comparator yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
