package org.atlas.model.metamodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class Entity extends Type {

    private Entity generalization;
    private Map<String, Property> properties = new HashMap<String, Property>();
    private List<Operation> operations;
    private Map<Association.Multiplicity, List<Association>> associations;
    private Map<String, Association> associationsByName;
    private boolean hasMany = false;
    private boolean abstractEntity = false;

    public Collection<Property> getProperties() {
        return properties.values();
    }

    public void addProperty(Property property) {
        properties.put(property.getName(), property);
    }

    public Property getProperty(String name) {
        return properties.get(name);
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

    public Map<Association.Multiplicity, List<Association>> getAssociations() {
        if (associations == null) {
            associations = new HashMap<Association.Multiplicity, List<Association>>();
        }
        return associations;
    }

    public List<Association> getAssociations(Association.Multiplicity multiplicity) {
        List<Association> a = getAssociations().get(multiplicity);
        if (a == null) {
            a = new ArrayList<Association>();
            associations.put(multiplicity, a);
        }

        return a;
    }

    public Map<String, Association> getAssociationsByName() {
        if (associationsByName == null) {
            associationsByName = new HashMap<String, Association>();
        }

        return associationsByName;
    }

    public Association getAssociation(String name) {
        return getAssociationsByName().get(name);
    }

    public List<Association> getOneToMany() {
        return getAssociations(Association.Multiplicity.OneToMany);
    }

    public List<Association> getOneToOne() {
        return getAssociations(Association.Multiplicity.OneToOne);
    }

    public List<Association> getManyToMany() {
        return getAssociations(Association.Multiplicity.ManyToMany);
    }

    public List<Association> getManyToOne() {
        return getAssociations(Association.Multiplicity.ManyToOne);
    }

    public void addAssociation(Association association) {

        if (association.getName() == null) {
            association.setName(StringUtils.uncapitalize(association.getEntity().getName()));
        }

        if (association.getRole() == null) {
            association.setRole(StringUtils.uncapitalize(this.getName()));
        }

        if (association.getMultiplicity() != null) {
            getAssociations(association.getMultiplicity()).add(association);

            switch (association.getMultiplicity()) {
                case OneToMany:
                    if (association.isOwner()) {
                        hasMany = true;
                    }
                    break;
                case ManyToMany:
                    hasMany = true;
                    break;
                default:
                    break;
            }
        }

        getAssociationsByName().put(association.getName(), association);
    }

    public void addAssociation(Association.Multiplicity multiplicity, Entity entity, String name, String role, boolean owner) {
        Association a = new Association();
        a.setEntity(entity);
        a.setMultiplicity(multiplicity);
        a.setOwner(owner);
        a.setName(name);
        a.setRole(role);
        addAssociation(a);
    }

    public void addAssociation(Association.Multiplicity multiplicity, Entity entity, boolean owner) {
        addAssociation(multiplicity, entity, null, null, owner);
    }

    public void addAssociation(Association.Multiplicity multiplicity, Entity entity) {
        addAssociation(multiplicity, entity, null, null, false);
    }

    public boolean hasMany() {
        return hasMany;
    }

    /**
     * Get the entity that this entity is a specialization of
     * @return
     */
    public Entity getGeneralization() {
        return generalization;
    }

    public void setGeneralization(Entity generalization) {
        this.generalization = generalization;
    }

    public boolean isAbstractEntity() {
        return abstractEntity;
    }

    public void setAbstractEntity(boolean abstractEntity) {
        this.abstractEntity = abstractEntity;
    }
}
