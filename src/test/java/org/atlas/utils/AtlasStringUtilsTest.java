package org.atlas.utils;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author andrews
 */
public class AtlasStringUtilsTest {

    public AtlasStringUtilsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of addSpacerToCamelCase method, of class AtlasStringUtils.
     */
    @Test
    public void testAddSpacerToCamelCase() {
        System.out.println("addSpacerToCamelCase");
        String input = "thisIsATest";
        String spacer = "_";
        String expResult = "this_Is_A_Test";
        String result = AtlasStringUtils.addSpacerToCamelCase(input, spacer);
        assertEquals(expResult, result);

        input = "test";
        expResult = "test";
        result = AtlasStringUtils.addSpacerToCamelCase(input, spacer);
        assertEquals(expResult, result);

    }

    /**
     * Test of addSpacerToCamelCase method, of class AtlasStringUtils.
     */
    @Test
    public void testAddSpacerToCamelCaseUpper() {
        System.out.println("addSpacerToCamelCaseUpper");
        String input = "thisIsATest";
        String spacer = "_";
        String expResult = "THIS_IS_A_TEST";
        String result = AtlasStringUtils.addSpacerToCamelCaseUpper(input, spacer);
        assertEquals(expResult, result);

        input = "test";
        expResult = "TEST";
        result = AtlasStringUtils.addSpacerToCamelCaseUpper(input, spacer);
        assertEquals(expResult, result);

    }


}