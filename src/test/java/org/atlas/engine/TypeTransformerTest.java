package org.atlas.engine;

import org.atlas.engine.OverwriteException;
import org.atlas.engine.Stereotype;
import org.atlas.engine.TransformException;
import org.atlas.engine.Target;
import org.atlas.engine.Context;
import org.atlas.engine.TypeTransformer;
import java.io.File;

import org.atlas.model.metamodel.Entity;
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


        Target target = Context.getTargets(Stereotype.entity).get(0);

        Entity entity = new Entity();
        entity.setName("Sample");

        File outputFile = TypeTransformer.transform(target, entity);
        assertTrue(outputFile.exists());

         outputFile.delete();
    }

}