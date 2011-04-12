package org.atlas.model.metamodel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Boundary extends Type {

    private Map<String, Operation> operations = new HashMap<String, Operation>();
    private Map<String, Property> properties = new HashMap<String, Property>();

    public Boundary() {
    }

    public Collection<Operation> getOperations() {
        return operations.values();
    }

    public void addOperation(Operation operation) {
        operations.put(operation.getName(), operation);
    }

    public Operation getOperation(String name) {
        return operations.get(name);
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
