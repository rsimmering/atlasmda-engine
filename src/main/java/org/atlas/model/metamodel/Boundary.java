package org.atlas.model.metamodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Boundary extends Type {

    private List<Operation> operations;
    private Map<String, Property> properties = new HashMap<String, Property>();

    public Boundary() {
    }

    public List<Operation> getOperations() {
        if (operations == null) {
            operations = new ArrayList<Operation>();
        }
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }


    public void addOperation(Operation operation) {
        getOperations().add(operation);
    }

    public Collection<Property> getProperties() {
        return properties.values();
    }

    public void addProperty(Property property) {
        properties.put(property.getName(), property);
    }

    public Property getProperty(String name) {
        return properties.get(name);
    }
}
