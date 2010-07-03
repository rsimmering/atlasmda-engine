package org.atlas.mda;


public class TemplateInput {
    private String dir;
    private String file;


    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) throws TransformException {
        this.dir = dir.replace(Context.ROOT, Context.getRootFolder());
    }
}
