package org.atlas.engine.utility;

import org.apache.commons.lang.StringUtils;
import org.atlas.model.metamodel.Boundary;
import org.atlas.model.metamodel.Control;
import org.atlas.model.metamodel.Element;
import org.atlas.model.metamodel.Entity;
import org.atlas.model.metamodel.Property;
import org.atlas.model.metamodel.Type;

public class EvaluationUtility {

    
    static public boolean hasBooleanPropertyTag(Type type, String tagName) {
        Entity entity = (Entity) type;

        for (Property property : entity.getProperties()) {
            for (String key : property.getTags().keySet()) {
                if (tagName.equals(key) && !StringUtils.isBlank(property.getTagValue(tagName)) && ("yes".equals(property.getTagValue(tagName)) || Boolean.parseBoolean(property.getTagValue(tagName)))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    static public boolean boolTag(Element element, String tagName){
        if(element.getTags().containsKey(tagName) && !StringUtils.isBlank(element.getTagValue(tagName)) && ("yes".equals(element.getTagValue(tagName)) || Boolean.parseBoolean(element.getTagValue(tagName)))){
            return true;
        }
        return false;
    }
    
    static public boolean hasOperations(Entity entity){
    	if(entity.getOperations().size() > 0){
    		return true;
    	}
    	return false;
    }
    
    static public boolean hasOperations(Control control){
    	if(control.getOperations().size() > 0){;
    		return true;
    	}
    	return false;
    }
    
    static public boolean hasOperations(Boundary boundry){
    	if(boundry.getOperations().size() > 0){;
    		return true;
    	}
    	return false;
    }
}
