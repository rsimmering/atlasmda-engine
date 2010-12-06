package org.atlas.model.metamodel;

import java.util.ArrayList;
import java.util.List;


public class Control extends Type {

    private List<Operation> operations;
 
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
}
