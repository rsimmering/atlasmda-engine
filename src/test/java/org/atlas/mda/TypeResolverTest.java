package org.atlas.mda;

import org.atlas.metamodel.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author andrews
 */
public class TypeResolverTest {

    public TypeResolverTest() {
    }

    /**
     * Test of resolve method, of class TypeResolver.
     */
    @Test
    public void testResolve() throws Exception {
        System.out.println("resolve");
        Model pim = new Model();

        Entity e1 = new Entity();
        e1.setName("E1");
        Property e1p1 = new Property();
        e1p1.setName("e1p1");
        e1p1.setType("string");
        e1.addProperty(e1p1);
        Property e1p2 = new Property();
        e1p2.setName("e1p2");
        e1p2.setType("Enum1");
        e1.addProperty(e1p2);
        Property e1p3= new Property();
        e1p3.setName("e1p3");
        e1p3.setType("E2");
        e1.addProperty(e1p3);
        pim.addEntity(e1);

        Entity e2=new Entity();
        e2.setName("E2");
        Property e2p1 = new Property();
        e2p1.setName("e2p1");
        e2p1.setType("date");
        e2.addProperty(e2p1);
        pim.addEntity(e2);

        Enumeration enum1 = new Enumeration();
        enum1.setName("Enum1");
        pim.addEnumeration(enum1);

        TypeResolver.resolve(pim);

        assertEquals("String", e1p1.getType());
        assertEquals("Enum1", e1p2.getType());
        assertEquals("E2", e1p3.getType());
        assertEquals("java.util.Date", e2p1.getType());

    }

}