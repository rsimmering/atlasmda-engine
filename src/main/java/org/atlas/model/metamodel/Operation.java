package org.atlas.model.metamodel;

import java.util.ArrayList;
import java.util.List;

public class Operation extends Element {

    private List<Parameter> parameters;
    private String returnType;
    private boolean returnMany=false;
    private String documentation;

    public Operation() {
    }

    public List<Parameter> getParameters() {
        if (parameters == null) {
            parameters = new ArrayList<Parameter>();
        }
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(Parameter parameter) {
        getParameters().add(parameter);
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public boolean isReturnMany() {
        return returnMany;
    }

    public void setReturnMany(boolean returnMany) {
        this.returnMany = returnMany;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

}
