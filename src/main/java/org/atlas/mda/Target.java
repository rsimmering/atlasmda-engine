package org.atlas.mda;

public class Target {

    private String name;
    private String stereotype;
    private Boolean collection;
    private String template;
    private String outputFile;
    private String namespace;
    private String outputPath;
    private Boolean overwrite;

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

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
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

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }
    

}
