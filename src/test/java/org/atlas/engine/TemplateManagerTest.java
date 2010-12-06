package org.atlas.engine;

import org.atlas.engine.Stereotype;
import org.atlas.engine.TemplateManager;
import org.atlas.engine.Target;
import org.atlas.engine.Context;
import org.apache.velocity.Template;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author andrews
 */
public class TemplateManagerTest {

    public TemplateManagerTest() {
    }

    /**
     * Test of getTemplate method, of class TemplateManager.
     */
    @Test
    public void testGetTemplate() throws Exception {
        System.out.println("getTemplate");

        TemplateManager templateManager = new TemplateManager();

        Target target = Context.getTargets(Stereotype.entity).get(0);
        Template result = templateManager.getTemplate(target);
        assertNotNull(result);
    }
}
