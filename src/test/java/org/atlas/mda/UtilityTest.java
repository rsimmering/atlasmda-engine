/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.atlas.mda;

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
            assertEquals(utilities.size(), 1);
            UtilityInput utility = utilities.get(0);
            assertEquals("org.atlas.utils.AtlasStringUtils", utility.getImpl());
            assertEquals("util", utility.getName());
        }
        catch(TransformException e){
            fail("TransformationException thrown"+e);
        }
    }
}
