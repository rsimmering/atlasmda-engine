package org.atlas.mda;

import java.io.File;

import org.atlas.metamodel.Entity;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author andrews
 */
public class TypeTransformerTest {

    public TypeTransformerTest() {
    }

    /**
     * Test of transform method, of class TypeTransformer.
     */
    @Test
    public void testTransform() throws TransformException, OverwriteException {
        System.out.println("transform");


        Target target = new TargetManager().getTargets(Stereotype.entity).get(0);

        Entity entity = new Entity();
        entity.setName("Sample");

        File outputFile = TypeTransformer.transform(target, entity);
        assertTrue(outputFile.exists());

         outputFile.delete();
    }

}