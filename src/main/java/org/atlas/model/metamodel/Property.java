package org.atlas.model.metamodel;

/**
 * A property for an entity
 * 
 * @see Entity
 * @author Steve Andrews
 *
 */
public class Property extends Element {

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
