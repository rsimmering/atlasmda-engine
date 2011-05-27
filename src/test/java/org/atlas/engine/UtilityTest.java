package org.atlas.engine;

import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class UtilityTest {
    public UtilityTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of getTemplateInput method, of class Context.
     */
    @Test
    public void testGetUtility() {
        System.out.println("getUtility");
        try{
            List<UtilityInput> utilities = Context.getUtilityInputs();
            assertEquals(utilities.size(), 2);
            UtilityInput utility = utilities.get(0);
            assertEquals("org.atlas.engine.utility.NamingUtility", utility.getImpl());
            assertEquals("util", utility.getName());
        }
        catch(TransformException e){
            fail("TransformationException thrown"+e);
        }
    }
}
