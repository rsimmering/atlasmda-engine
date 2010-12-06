package org.atlas.model.metamodel;

import org.apache.commons.lang.StringUtils;

public class Association extends Element {

    public enum Multiplicity {
        OneToOne,
        OneToMany,
        ManyToMany,
        ManyToOne
    };

    private Entity entity;
    private Multiplicity multiplicity;
    private boolean owner;
    private String role;

    public Association() {
    }

    @Override
    public String getName() {
        if(super.getName()==null && entity != null) {
            setName(StringUtils.uncapitalize(entity.getName()));
        }

        return super.getName();
    }

    /**
     * The type of entity associated with
     * @return
     */
    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }


    /**
     * The multiplicity of the association
     * @return
     */
    public Multiplicity getMultiplicity() {
        return multiplicity;
    }

    public void setMultiplicity(Multiplicity type) {
        this.multiplicity = type;
    }

    /**
     * Whether the entity with this association owns the association
     * @return
     */
    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    /**
     * The role the entity that has this association plays in the association
     * @return
     */
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
}
