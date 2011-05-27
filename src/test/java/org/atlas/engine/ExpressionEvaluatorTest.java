package org.atlas.engine;
import static org.junit.Assert.*;

import org.atlas.model.metamodel.Entity;
import org.atlas.model.metamodel.Operation;
import org.atlas.model.metamodel.Tag;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExpressionEvaluatorTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testSimpleExpression() throws TransformException {
    	Entity entity = new Entity();
    	entity.setName("Test");
    	
    	Tag tag = new Tag();
    	tag.setName("transient");
    	tag.setValue("true");

    	entity.addTag(tag);
    	
    	ExpressionEvaluator eval = ExpressionEvaluator.instance();
    	boolean result = eval.evaluateExpression("${type.hasTag('transient')}", entity);
    	boolean result2 = eval.evaluateExpression("!${type.hasTag('transient')}", entity);
    	assertTrue(result);
    	assertFalse(result2);
    }
    
    @Test
    public void testComplexExpression() throws TransformException {
    	Entity entity = new Entity();
    	entity.setName("Test");
    	
    	Tag tag = new Tag();
    	tag.setName("transient");
    	tag.setValue("true");

    	entity.addTag(tag);
    	
    	ExpressionEvaluator eval = ExpressionEvaluator.instance();
    	boolean result = eval.evaluateExpression("${type.hasTag('transient')} || ${type.hasTag('composite')}", entity);
    	boolean result2 = eval.evaluateExpression("!(${type.hasTag('transient')} || ${type.hasTag('composite')})", entity);
    	assertTrue(result);
    	assertFalse(result2);
    }
    
    @Test
    public void testUtilityExpression() throws TransformException {
    	Entity entity = new Entity();
    	entity.setName("Test");
    	
    	Tag tag = new Tag();
    	tag.setName("transient");
    	tag.setValue("true");

    	entity.addTag(tag);
    	
    	ExpressionEvaluator eval = ExpressionEvaluator.instance();
    	boolean result = eval.evaluateExpression("${stringutil.boolTag(type, 'transient')} || ${stringutil.boolTag(type, 'composite')}", entity);
    	assertTrue(result);
    }
}
