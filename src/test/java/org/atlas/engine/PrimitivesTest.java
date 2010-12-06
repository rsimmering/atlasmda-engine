package org.atlas.engine;

import org.atlas.engine.TransformException;
import org.atlas.engine.PrimitiveManager;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author andrews
 */
public class PrimitivesTest {

    public PrimitivesTest() {
    }


    /**
     * Test of getPsmType method, of class PrimitiveManager
     */
    @Test
    public void testGetPsmType() throws TransformException {

        System.out.println("getPsmType");
        String pimType = "string";
        String expResult = "String";

        PrimitiveManager primitives = new PrimitiveManager();

        String result = primitives.getPsmType(pimType);
        assertEquals(expResult, result);

        pimType="date";
        expResult="java.util.Date";
        result=primitives.getPsmType(pimType);
        assertEquals(expResult, result);

    }

}