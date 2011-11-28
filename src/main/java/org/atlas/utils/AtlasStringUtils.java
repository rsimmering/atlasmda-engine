package org.atlas.utils;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.atlas.model.metamodel.Element;
import org.atlas.model.metamodel.Entity;
import org.atlas.model.metamodel.Property;
import org.atlas.model.metamodel.Tag;
import org.atlas.model.metamodel.Type;

public final class AtlasStringUtils extends StringUtils {

    public static String addSpacerToCamelCase(String input, String spacer) {
        StringBuffer b = new StringBuffer();

        String[] words = StringUtils.splitByCharacterTypeCamelCase(input);
        int i = 1;
        for (String word : words) {
            b.append(word);
            if (i < words.length) {
                b.append(spacer);
            }
            i++;
        }

        return b.toString();
    }

    public static String addSpacerToCamelCaseUpper(String input, String spacer) {
        return addSpacerToCamelCase(input, spacer).toUpperCase();
    }

    static public boolean hasTag(Element type, String tagName) {
        if(type.getTag(tagName) == null || StringUtils.isBlank(type.getTag(tagName).getValue())){
            return false;
        }
        return true;
    }

    static public boolean hasTagofType(Type type, String tagName, String typeType) {
        List<Tag> tagList = type.getTagsOfType(typeType);
        if(tagList == null){
            return false;
        }
        for(Tag tag : tagList){
            if(tagName.equals(tag.getName())){
                return true;
            }
        }
        return false;
    }
    
    static public boolean hasBooleanPropertyTag(Type type, String tagName) {
        Entity entity = (Entity) type;

        for (Property property : entity.getProperties()) {
            for (String key : property.getTags().keySet()) {
                if (tagName.equals(key) && !isBlank(property.getTagValue(tagName)) && ("yes".equals(property.getTagValue(tagName)) || Boolean.parseBoolean(property.getTagValue(tagName)))) {
                    return true;
                }
            }
        }
        return false;
    }

   static public String buildNS(String namespace, Type type){
        return buildNS(namespace, null, type);

    }

    static public String buildNS(String namespace, String packageName, Type type){
        if(StringUtils.isBlank(type.getNamespace()) && StringUtils.isBlank(packageName)){
          return namespace;
        }
        else if(StringUtils.isBlank(packageName)){
            return namespace+"."+type.getNamespace();
        }
        else if(StringUtils.isBlank(type.getNamespace())){
            return namespace+"."+packageName;
        }
        return namespace + "." + packageName + "." + type.getNamespace();
    }

    static public boolean boolTag(Element element, String tagName){
            if(element.getTags().containsKey(tagName) && !isBlank(element.getTagValue(tagName)) && ("yes".equals(element.getTagValue(tagName)) || Boolean.parseBoolean(element.getTagValue(tagName)))){
                return true;
            }
            return false;
    }
    
    static public String boolTag(Property property, String tagName, String textToDisplay){
            if(property.getTags().containsKey(tagName) && !isBlank(property.getTagValue(tagName)) && ("yes".equals(property.getTagValue(tagName)) || Boolean.parseBoolean(property.getTagValue(tagName)))){
                return textToDisplay;
            }
            return "";
    }
}
