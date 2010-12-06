package org.atlas.engine;

import org.atlas.engine.Stereotype;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Steve Andrews
 */
public class StereotypeTest {

    public StereotypeTest() {
    }

    /**
     * Test of valueOf method, of class Stereotype.
     */
    @Test
    public void testValueOfEntity() {
        System.out.println("testValueOfEntity");
        String name = "entity";
        Stereotype expResult = Stereotype.entity;
        Stereotype result = Stereotype.valueOf(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Stereotype.
     */
    @Test
    public void testToStringEntity() {
        System.out.println("testToStringEntity");
        Stereotype instance = Stereotype.entity;
        String expResult = "entity";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of valueOf method, of class Stereotype.
     */
    @Test
    public void testValueOfControl() {
        System.out.println("testValueOfControl");
        String name = "control";
        Stereotype expResult = Stereotype.control;
        Stereotype result = Stereotype.valueOf(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Stereotype.
     */
    @Test
    public void testToStringControl() {
        System.out.println("testToStringControl");
        Stereotype instance = Stereotype.control;
        String expResult = "control";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of valueOf method, of class Stereotype.
     */
    @Test
    public void testValueOfBoundary() {
        System.out.println("testValueOfBoundary");
        String name = "boundary";
        Stereotype expResult = Stereotype.boundary;
        Stereotype result = Stereotype.valueOf(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Stereotype.
     */
    @Test
    public void testToStringBoundary() {
        System.out.println("testToStringBoundary");
        Stereotype instance = Stereotype.boundary;
        String expResult = "boundary";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    

    /**
     * Test of valueOf method, of class Stereotype.
     */
    @Test
    public void testValueOfEnumeration() {
        System.out.println("testValueOfEnumeration");
        String name = "enumeration";
        Stereotype expResult = Stereotype.enumeration;
        Stereotype result = Stereotype.valueOf(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Stereotype.
     */
    @Test
    public void testToStringEnumeration() {
        System.out.println("testToStringEnumeration");
        Stereotype instance = Stereotype.enumeration;
        String expResult = "enumeration";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}