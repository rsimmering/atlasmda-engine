package org.atlas.mda;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.atlas.metamodel.Entity;
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
    public void testGetOutputFileName() {
        System.out.println("testGetOutputFileName");
        String expResult = "SampleBase.java";


        Target target = new TargetManager().getTargets(Stereotype.entity).get(0);

        Entity entity = new Entity();
        entity.setName("Sample");

        String result = OutputFileManager.getOutputFileName(target, entity);
        assertEquals(expResult, result);

        expResult = "Sample.java";
        target = new TargetManager().getTargets(Stereotype.entity).get(1);
        result = OutputFileManager.getOutputFileName(target, entity);
        assertEquals(expResult, result);
    }

    /**
     * Test of testGetOutputFileName method, of class OutputFileManager.
     */
    @Test
    public void testGetOutputFolderName() throws TransformException {
        System.out.println("testGetOutputFolderName");
        String expResult = "target/testoutput/src/generated/java/org/atlas/test/entity";

        Entity entity = new Entity();
        entity.setName("Sample");

        Target target = new TargetManager().getTargets(Stereotype.entity).get(0);

        String result = OutputFileManager.getOutputFolderName(target, entity);
        assertEquals(expResult, result);

        expResult = "target/testoutput/src/main/java/org/atlas/test/entity";
        target = new TargetManager().getTargets(Stereotype.entity).get(1);
        result = OutputFileManager.getOutputFolderName(target, entity);
        assertEquals(expResult,result);
    }

    @Test
    public void testGetOutputFile() throws IOException, OverwriteException, TransformException {
        System.out.println("testGetOutputFile");

        Target target = new TargetManager().getTargets(Stereotype.entity).get(0);

        Entity entity = new Entity();
        entity.setName("Sample");

        File file = OutputFileManager.getOutputFile(target, entity);
        FileWriter fw = new FileWriter(file,true);
        fw.write(file.getAbsolutePath());
        fw.close();
        assertTrue(file.exists());
        file.delete();

        target = new TargetManager().getTargets(Stereotype.entity).get(1);
        file = OutputFileManager.getOutputFile(target, entity);
        fw = new FileWriter(file,true);
        fw.write(file.getAbsolutePath());
        fw.close();
        assertTrue(file.exists());
        file.delete();
    }


    @Test
    public void testGetOutputFileOverwriteException() throws IOException, OverwriteException, TransformException {
        System.out.println("testGetOutputFileOverwriteException");

        Target target = new TargetManager().getTargets(Stereotype.entity).get(1);

        Entity entity = new Entity();
        entity.setName("Sample");

        File file = OutputFileManager.getOutputFile(target, entity);
        FileWriter fw = new FileWriter(file,true);
        fw.write(file.getAbsolutePath());
        fw.close();
        assertTrue(file.exists());

        try {
            OutputFileManager.getOutputFile(target, entity);
        }
        catch(OverwriteException e) {
            assertTrue(true);
        }

        file.delete();

    }


    @Test
    public void testGetOutputFileOverwrite() throws OverwriteException, IOException, TransformException {
        System.out.println("testGetOutputFileOverwriteException");

        Target target = new TargetManager().getTargets(Stereotype.entity).get(0);

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
