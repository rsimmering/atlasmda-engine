package org.atlas.model.metamodel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Model extends Element {

    private Map<String, Entity> entities;
    private Map<String, Control> controls;
    private Map<String, Boundary> boundaries;
    private Map<String, Enumeration> enumerations;

    public Model() {
        entities = new HashMap<String, Entity>();
        enumerations = new HashMap<String, Enumeration>();
        controls = new HashMap<String, Control>();
        boundaries = new HashMap<String, Boundary>();
    }

    public Entity getEntity(String name) {
        return entities.get(name);
    }

    public void addEntity(Entity entity) {
        entities.put(entity.getName(), entity);
    }

    public Collection<Entity> getEntities() {
        return entities.values();
    }

    public Enumeration getEnumeration(String name) {
        return enumerations.get(name);
    }

    public void addEnumeration(Enumeration enumeration) {
        enumerations.put(enumeration.getName(), enumeration);
    }

    public Collection<Enumeration> getEnumerations() {
        return enumerations.values();
    }

    public Control getControl(String name) {
        return controls.get(name);
    }

    public void addControl(Control control) {
        controls.put(control.getName(), control);
    }

    public Collection<Control> getControls() {
        return controls.values();
    }

    public Boundary getBoundary(String name) {
        return boundaries.get(name);
    }

    public void addBoundary(Boundary boundary) {
        boundaries.put(boundary.getName(), boundary);
    }

    public Collection<Boundary> getBoundaries() {
        return boundaries.values();
    }
}
