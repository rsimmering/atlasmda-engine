package org.atlas.engine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.atlas.model.metamodel.Model;
import org.atlas.model.adapter.AdapterException;
import org.atlas.model.adapter.Adapter;
import org.atlas.model.adapter.AdapterFactory;

/**
 * Responsible for getting the model into the transformation engine
 *
 * @author Steve Andrews
 */
public final class ModelManager {

    private Model MODEL = null;

    public ModelManager() {
    }

    public Model getModel() throws TransformException {
        if (MODEL == null) {
            MODEL = new Model();
            buildModel();
        }

        return MODEL;
    }


    /**
     * Builds the model from all the adapted PIMs and PSMs specified in the context
     */
    private void buildModel() throws TransformException {

    // Adapt all the PIM inputs to the metamodel
        for(ModelInput mi : Context.getModelInputs()) {
            adapt(mi);
        }
    }

    /**
     * Manipulates the model based on an input model
     *
     * @param modelInput
     * @throws TransformException
     */
    private void adapt(ModelInput modelInput) throws TransformException {
        String modelFileName = modelInput.getFile();
        String modelAdapterName = modelInput.getAdapter();

        try {
            long startTime = System.currentTimeMillis();
            Adapter modelAdapter = AdapterFactory.getAdapter(modelAdapterName);
            
            File modelFile = null;
            
            
            if(modelFileName.indexOf("classpath:") != -1){
            	modelFileName = modelFileName.substring(10);
            	
            	InputStream stream = Context.class.getResourceAsStream(modelFileName);
            	
            System.out.println(stream);
            	modelFile = File.createTempFile(modelFileName, "tmp");
            	modelFile.deleteOnExit();
            	
            	OutputStream out = new FileOutputStream(modelFile);

                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = stream.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                stream.close();
                out.close();
            }
            else{
            	modelFile = new File(modelFileName);
            	System.out.println("other: "+ modelFileName);
            }
            MODEL = modelAdapter.adapt(modelFile, MODEL);
            long stopTime = System.currentTimeMillis();
            long runTime = stopTime - startTime;
            System.out.println("Run time: " + runTime);
            Logger.getLogger(ModelManager.class.getName()).log(Level.INFO, "Successfully loaded PIM in " + runTime + " ms");
        } catch (AdapterException ex) {
            Logger.getLogger(ModelManager.class.getName()).log(Level.SEVERE, null, ex);
            throw new TransformException("Unable to create model adapter [" + modelAdapterName + "]", ex);
        } catch (IOException e) {
			 Logger.getLogger(ModelManager.class.getName()).log(Level.SEVERE, null, e);
             throw new TransformException("Unable to create model adapter [" + modelAdapterName + "]", e);
		}
    }

    public void setModel(Model model) {
        MODEL = model;

    }
}
