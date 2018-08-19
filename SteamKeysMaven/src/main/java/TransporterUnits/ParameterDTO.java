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
public class ParameterDTO extends DTO {
    
      private String name;
      private String description;
      private int value;

    public ParameterDTO(String name, String description, int value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public ParameterDTO() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }
      
      
    
}
