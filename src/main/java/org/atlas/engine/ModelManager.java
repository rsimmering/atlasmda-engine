package org.atlas.engine;

import java.io.File;
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
            File modelFile = new File(modelFileName);
            MODEL = modelAdapter.adapt(modelFile, MODEL);
            long stopTime = System.currentTimeMillis();
            long runTime = stopTime - startTime;
            System.out.println("Run time: " + runTime);
            Logger.getLogger(ModelManager.class.getName()).log(Level.INFO, "Successfully loaded PIM in " + runTime + " ms");
        } catch (AdapterException ex) {
            Logger.getLogger(ModelManager.class.getName()).log(Level.SEVERE, null, ex);
            throw new TransformException("Unable to create model adapter [" + modelAdapterName + "]", ex);
        }
    }

    public void setModel(Model model) {
        MODEL = model;

    }
}
