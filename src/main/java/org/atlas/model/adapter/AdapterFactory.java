package org.atlas.model.adapter;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.atlas.engine.TransformException;

public class AdapterFactory {

    public static Adapter getAdapter(String adapterClass) throws AdapterException, TransformException {
        Adapter adapter = null;

        try {
            Class clazz = Class.forName(adapterClass);
            adapter = (Adapter) clazz.newInstance();
            Logger.getLogger(AdapterFactory.class.getName()).log(Level.INFO, "Successfully loaded adapter class [" + clazz.getName() + "]");
        } catch (ClassNotFoundException e) {
            Logger.getLogger(AdapterFactory.class.getName()).log(Level.SEVERE, "Unable to find class from file [" + adapterClass +  "]", e);
            throw new AdapterException("Unable to find class from file [" + adapterClass  + "]", e);
        } catch (InstantiationException e) {
            Logger.getLogger(AdapterFactory.class.getName()).log(Level.SEVERE, "Unable to instanciate class from file [" + adapterClass +  "]", e);
            throw new AdapterException("Unable to instantiate class from file [" + adapterClass + "]", e);
        } catch (IllegalAccessException e) {
            Logger.getLogger(AdapterFactory.class.getName()).log(Level.SEVERE, "IllegalAccess of class from file [" + adapterClass + "]", e);
            throw new AdapterException("IllegalAccess of class from file [" + adapterClass  + "]", e);

        }

        return adapter;
    }
    
}
