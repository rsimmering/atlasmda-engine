package org.atlas.model.metamodel;

import org.atlas.model.metamodel.Entity;
import org.atlas.model.metamodel.Property;
import static org.junit.Assert.*;

import org.atlas.model.metamodel.Association.Multiplicity;
import org.junit.Before;
import org.junit.Test;

public class EntityTest {

    private static Entity entity;

    @Before
    public void createEntity() {
        entity = new Entity();
        entity.setName("Entity");
    }

    @Test
    public void testEntity() {
        assertNotNull(entity);
    }

    @Test
    public void testGetName() {
        assertEquals("Entity", entity.getName());
    }

    @Test
    public void testGetProperties() {
        assertEquals(0, entity.getProperties().size());
    }

    @Test
    public void testAddProperty() {
        Property p = new Property();
        p.setName("Property1");
        entity.addProperty(p);
        assertEquals(1, entity.getProperties().size());
        assertEquals("Property1", entity.getProperty("Property1").getName());
    }

    @Test
    public void testGetAssociations() {
        assertEquals(0, entity.getAssociations().size());
    }

    @Test
    public void testAddAssociation() {
        Entity t = new Entity();
        t.setName("T");

        Entity t1 = new Entity();
        t1.setName("T1");

        Entity t2 = new Entity();
        t2.setName("T2");
        entity.addAssociation(Multiplicity.OneToMany, t);

        assertEquals(1, entity.getAssociations(Multiplicity.OneToMany).size());
        assertEquals("t", entity.getAssociations(Multiplicity.OneToMany).get(0).getName());

        entity.addAssociation(Multiplicity.ManyToMany, t1);

        assertEquals(1, entity.getAssociations(Multiplicity.ManyToMany).size());
        assertEquals("t1", entity.getAssociations(Multiplicity.ManyToMany).get(0).getName());

        entity.addAssociation(Multiplicity.ManyToMany, t2);

        assertEquals(2, entity.getAssociations(Multiplicity.ManyToMany).size());
        assertEquals("t2", entity.getAssociations(Multiplicity.ManyToMany).get(1).getName());

    }
}
