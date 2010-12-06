package org.atlas.engine;

import org.atlas.engine.OutputFileManager;
import org.atlas.engine.OverwriteException;
import org.atlas.engine.Stereotype;
import org.atlas.engine.TransformException;
import org.atlas.engine.Target;
import org.atlas.engine.Context;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.atlas.model.metamodel.Entity;
import org.atlas.model.metamodel.Enumeration;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author andrews
 */
public class OutputFileManagerTest {

    public OutputFileManagerTest() {
    }

    /**
     * Test of testGetOutputFileName method, of class OutputFileManager.
     */
    @Test
    public void testGetOutputFileName() throws TransformException {
        System.out.println("testGetOutputFileName");
        String expResult = "Sample.xml";


        Target target = Context.getTargets(Stereotype.entity).get(0);

        Entity entity = new Entity();
        entity.setName("Sample");

        String result = OutputFileManager.getOutputFileName(target, entity);
        assertEquals(expResult, result);

    }

    /**
     * Test of testGetOutputFileName method, of class OutputFileManager.
     */
    @Test
    public void testGetOutputFolderName() throws TransformException {
        System.out.println("testGetOutputFolderName");
        String expResult = "target/testoutput/entity";

        Entity entity = new Entity();
        entity.setName("Sample");

        Target target = Context.getTargets(Stereotype.entity).get(0);

        String result = OutputFileManager.getOutputFolderName(target, entity);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetOutputFile() throws IOException, OverwriteException, TransformException {
        System.out.println("testGetOutputFile");

        Target target = Context.getTargets(Stereotype.entity).get(0);

        Entity entity = new Entity();
        entity.setName("Sample");

        File file = OutputFileManager.getOutputFile(target, entity);
        FileWriter fw = new FileWriter(file,true);
        fw.write(file.getAbsolutePath());
        fw.close();
        assertTrue(file.exists());
        file.delete();
    }


    @Test
    public void testGetOutputFileOverwriteException() throws IOException, OverwriteException, TransformException {
        System.out.println("testGetOutputFileOverwriteException");

        Target target = Context.getTargets(Stereotype.enumeration).get(0);

        Enumeration e = new Enumeration();
        e.setName("SampleEnum");

        File file = OutputFileManager.getOutputFile(target, e);
        FileWriter fw = new FileWriter(file,true);
        fw.write(file.getAbsolutePath());
        fw.close();
        assertTrue(file.exists());

        try {
            OutputFileManager.getOutputFile(target, e);
        }
        catch(OverwriteException ex) {
            assertTrue(true);
        }

        file.delete();

    }


    @Test
    public void testGetOutputFileOverwrite() throws OverwriteException, IOException, TransformException {
        System.out.println("testGetOutputFileOverwriteException");

        Target target = Context.getTargets(Stereotype.entity).get(0);

        Entity entity = new Entity();
        entity.setName("Sample");

        File file = OutputFileManager.getOutputFile(target, entity);
        FileWriter fw = new FileWriter(file,true);
        fw.write(file.getAbsolutePath() + " [original]");
        fw.close();
        assertTrue(file.exists());

        file = OutputFileManager.getOutputFile(target, entity);
        fw = new FileWriter(file,true);
        fw.write(file.getAbsolutePath() + " [new]");
        fw.close();
        assertTrue(file.exists());

        file.delete();

    }
}
