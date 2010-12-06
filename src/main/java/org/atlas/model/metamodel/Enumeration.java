package org.atlas.model.metamodel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Enumeration extends Type {

    private Map<String, Literal> literals;

    public Enumeration() {
        literals = new HashMap<String, Literal>();
    }

    public void addLiteral(Literal literal) {
        literals.put(literal.getName(), literal);
    }

    public void addLiteral(String name) {
        Literal literal = new Literal();
        literal.setName(name);
        addLiteral(literal);
    }

    public Collection<Literal> getLiterals() {
        return literals.values();
    }

    public Literal getLiteral(String name) {
        return literals.get(name);
    }
}
