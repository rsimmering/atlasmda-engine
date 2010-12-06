package org.atlas.model.metamodel;

/**
 *
 * @author andrews
 */
public class Tag {

    private String name;
    private String value;
    private String type;

    public Tag() {
        
    }

    public Tag(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Tag(String name, String value, String type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
