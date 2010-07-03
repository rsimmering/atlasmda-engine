package org.atlas.mda;

import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Steve Andrews
 */
public class TargetsTest {

    public TargetsTest() {
    }

    /**
     * Test of getTargets method, of class TargetManager.
     */
    @Test
    public void testGetTargets() {
        System.out.println("getTargets");

        List<Target> targets = new TargetManager().getTargets(Stereotype.entity);

        assertEquals(2, targets.size());

        Target t = targets.get(0);
        assertEquals("entity.impl.base", t.getName());
        assertEquals(Stereotype.entity, t.getTargetStereotype());
        assertEquals("entity.base.vm", t.getTemplate());
        assertEquals("${name}Base.java", t.getOutputFile());
        assertTrue(t.getOverwrite());
    }

}