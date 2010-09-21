package org.atlas.mda;

import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.atlas.metamodel.Boundary;
import org.atlas.metamodel.Control;
import org.atlas.metamodel.Entity;
import org.atlas.metamodel.Enumeration;
import org.atlas.metamodel.Model;
import org.atlas.metamodel.Type;

/**
 * Loads up a model and then applies transformation templates to it.
 * 
 * @author Steve Andrews
 */
public class ModelTransformer {

    /**
     * Apply the templates to the model elements
     */
    public void transform() throws TransformException {
        Model model = Context.getModelManager().getModel();
        TypeResolver.resolve(model);

        Collection<Entity> entities = model.getEntities();
        Logger.getLogger(ModelTransformer.class.getName()).log(Level.INFO, "Transforming " + entities.size() + " Entities");
        List<Target> targets = Context.getTargets(Stereotype.entity);
        if (targets != null && targets.size() > 0) {
            // Apply the entity templates to the entities in the PIM
            for (Entity entity : entities) {
                Logger.getLogger(ModelTransformer.class.getName()).log(Level.INFO, entity.getName());
                for (Target target : targets) {
                    TypeTransformer.transform(target, entity);
                }
            }
        }

        // Apply the templates to the enums in the PIM
        Collection<Enumeration> enums = model.getEnumerations();
        Logger.getLogger(ModelTransformer.class.getName()).log(Level.INFO, "Transforming " + enums.size() + " Enums");
        targets = Context.getTargets(Stereotype.enumeration);
        if (targets != null && targets.size() > 0) {
            for (Enumeration e : enums) {
                Logger.getLogger(ModelTransformer.class.getName()).log(Level.INFO, e.getName());
                for (Target target : targets) {
                    TypeTransformer.transform(target, e);
                }
            }
        }

        // Apply the templates to the controls in the PIM
        Collection<Control> controls = model.getControls();
        Logger.getLogger(ModelTransformer.class.getName()).log(Level.INFO, "Transforming " + controls.size() + " Controls");
        targets = Context.getTargets(Stereotype.control);
        if (targets != null && targets.size() > 0) {
            for (Control c : controls) {
                Logger.getLogger(ModelTransformer.class.getName()).log(Level.INFO, c.getName());
                for (Target target : targets) {
                    TypeTransformer.transform(target, c);
                }
            }
        }

        // Apply the templates to the controls in the PIM
        Collection<Boundary> boundries = model.getBoundaries();
        Logger.getLogger(ModelTransformer.class.getName()).log(Level.INFO, "Transforming " + boundries.size() + " Boundries");
        targets = Context.getTargets(Stereotype.boundary);
        if (targets != null && targets.size() > 0) {
            for (Boundary b : boundries) {
                Logger.getLogger(ModelTransformer.class.getName()).log(Level.INFO, b.getName());
                for (Target target : targets) {
                    TypeTransformer.transform(target, b);
                }
            }
        }

        targets = Context.getCollectionTargets(Stereotype.control);
        if (targets != null && targets.size() > 0) {
            Collection<Type> controlTypes = (Collection) controls;
            if(controlTypes != null && controlTypes.size() > 0){
                for (Target target : targets) {
                    TypeTransformer.transformCollection(target, controlTypes);
                }
            }
        }

        targets = Context.getCollectionTargets(Stereotype.entity);
        if (targets != null && targets.size() > 0) {
            Collection<Type> entityTypes = (Collection) entities;
            if(entityTypes != null && entityTypes.size() > 0){
                for (Target target : targets) {
                    TypeTransformer.transformCollection(target, entityTypes);
                }
            }
        }

        targets = Context.getCollectionTargets(Stereotype.enumeration);
        if (targets != null && targets.size() > 0) {
            Collection<Type> enumTypes = (Collection) enums;
            if(enumTypes != null && enumTypes.size() > 0){
                for (Target target : targets) {
                    TypeTransformer.transformCollection(target, enumTypes);
                }
            }
        }

        targets = Context.getCollectionTargets(Stereotype.boundary);
        if (targets != null && targets.size() > 0) {
            Collection<Type> boundryTypes = (Collection) boundries;
            if(boundryTypes != null && boundryTypes.size() > 0){
                for (Target target : targets) {
                    TypeTransformer.transformCollection(target, boundryTypes);
                }
            }
        }
    }
}
