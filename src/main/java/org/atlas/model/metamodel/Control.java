package org.atlas.model.metamodel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;



public class Control extends Type {

    private Map<String, Operation> operations = new HashMap<String, Operation>();

    public Collection<Operation> getOperations() {
        return operations.values();
    }

    public void addOperation(Operation operation) {
        operations.put(operation.getName(), operation);
    }

    public Operation getOperation(String name) {
        return operations.get(name);
    }
}
