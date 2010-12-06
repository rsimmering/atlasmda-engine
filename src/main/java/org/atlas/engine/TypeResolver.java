package org.atlas.engine;

import org.atlas.model.metamodel.Boundary;
import org.atlas.model.metamodel.Control;
import org.atlas.model.metamodel.Entity;
import org.atlas.model.metamodel.Operation;
import org.atlas.model.metamodel.Parameter;
import org.atlas.model.metamodel.Model;
import org.atlas.model.metamodel.Property;
import org.atlas.model.metamodel.Type;

/**
 * Resolves a type to a PIM type or to a primitive
 * @author Steve Andrews
 */
public final class TypeResolver {

    public static String resolve(Model model, String type) throws TransformException {
        if (model.getEntity(type) != null) {
            return type;
        } else if (model.getEnumeration(type) != null) {
            return type;
        } else if (Context.getPrimitiveManager().getPsmType(type) != null) {
            return Context.getPrimitiveManager().getPsmType(type);
        } else {
            throw new TransformException("Invalid type [" + type + "]");
        }
    }

    public static void resolveProperty(Model model, Type type, Property p) throws TransformException{
        try{
            String psmType = resolve(model,p.getType());
            p.setType(psmType);
        }
        catch(TransformException te){
            throw new TransformException("Invalid type [" + p.getType() + "] on Element ["+type.getName()+"] and Property ["+p.getName()+"]");
        }
    }

    public static void resolveOperation(Model model, Type type, Operation o) throws TransformException{
        try{
            String psmType = resolve(model,o.getReturnType());
            o.setReturnType(psmType);
        }
        catch(TransformException te){
            throw new TransformException("Invalid Return Type [" + o.getReturnType() + "] on Element ["+type.getName()+"] and Operation ["+o.getName()+"]");
        }

        for(Parameter p:o.getParameters()) {
            try{
                String psmType = resolve(model,p.getType());
                p.setType(psmType);
            }
            catch(TransformException te){
                throw new TransformException("Invalid Type [" + p.getType() + "] on Element ["+type.getName()+"] and Operation ["+o.getName()+"] and Parameter ["+p.getName()+"]");
            }
        }
    }


    /**
     * Iterate over all the properties and operation parameters and resolve the types to either PIM types or primitives
     * @param model
     */
    public static void resolve(Model model) throws TransformException {

        for (Entity e : model.getEntities()) {
            for (Property p : e.getProperties()) {
                resolveProperty(model, e, p);
            }
            for(Operation o:e.getOperations()) {
               resolveOperation(model, e, o);
            }
        }

        for(Control c:model.getControls()) {
            for(Operation o:c.getOperations()) {
                resolveOperation(model, c, o);
            }
        }

        for(Boundary b:model.getBoundaries()) {
            for (Property p : b.getProperties()) {
                resolveProperty(model, b, p);
            }
            for(Operation o:b.getOperations()) {
                resolveOperation(model, b, o);
            }
        }
    }
    
}
