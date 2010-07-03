package org.atlas.mda;

import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author andrews
 */
public class ContextTest {

    public ContextTest() {
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
    public void testGetTemplate() {
        System.out.println("getTemplate");
        String expResult = "src/test/resources/templates";
        TemplateInput result = null;
        try{
            result = Context.getTemplateInput();
        }
        catch(TransformException e){
            fail("TransformationException thrown"+e);
        }
        assertEquals(expResult, result.getDir());
    }


    /**
     * Test of getModelInputs method, of class Context.
     */
    @Test
    public void testGetModels() {
        System.out.println("getModels");
        String expResult = "src/test/resources/project.xml";
        List<ModelInput> result = null;
        try{
            result = Context.getModelInputs();
        }
        catch(TransformException e){
            fail("TransformationException thrown"+e);
        }
        assertEquals(1, result.size());
        assertEquals(expResult, result.get(0).getFile());
    }


    /**
     * Test of getOutputs method, of class Context.
     */
    @Test
    public void testGetOutputs() {
        System.out.println("getOutputs");
        String expResult = "target/testoutput/src/generated/resources";
        Map<String, Output> result = null;
        try{
            result = Context.getOutputs();
        }
        catch(TransformException e){
            fail("TransformationException thrown"+e);
        }
        assertEquals(expResult, result.get("generated.resource.root").getDir());
    }

}