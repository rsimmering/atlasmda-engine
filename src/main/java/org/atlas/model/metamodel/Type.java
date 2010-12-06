package org.atlas.model.metamodel;

public abstract class Type extends Element {

    private String namespace;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
