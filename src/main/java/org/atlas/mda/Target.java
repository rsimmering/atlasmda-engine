package org.atlas.mda;

import java.util.HashMap;
import java.util.Map;

public class Target {

    private String name;
    private String stereotype;
    private Boolean collection;
    private String template;
    private String outputFile;
    private String outputPath;
    private Boolean overwrite;
    private Map<String, String> properties;

    public Target() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public boolean isOverwritable() {
        return Boolean.valueOf(overwrite);
    }

    public Boolean getOverwrite() {
        return overwrite;
    }

    public void setOverwrite(Boolean overwrite) {
        this.overwrite = overwrite;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getStereotype() {
        return stereotype;
    }

    public void setStereotype(String inputType) {
        this.stereotype = inputType;
    }

    public Stereotype getTargetStereotype() {
        return Stereotype.valueOf(stereotype);
    }

    public boolean isCollectionType() {
        return Boolean.valueOf(collection);
    }

    public Boolean getCollection() {
        return collection;
    }

    public void setCollection(Boolean collection) {
        this.collection = collection;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String path) throws TransformException {
        outputPath = Context.replaceVariables(path);
        outputPath.replace(Context.ROOT, Context.getRootFolder());
    }

    public Map<String, String> getProperties() {
        if (properties == null) {
            properties = new HashMap<String, String>();
        }

        return properties;
    }

    public String getProperty(String name) {
        return getProperties().get(name);
    }

    public void setProperty(String name, String value) throws TransformException {
        getProperties().put(name, Context.replaceVariables(value));
    }

    
}
