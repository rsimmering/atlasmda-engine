package org.atlas.mda;


public class ModelInput {
    private String file;
    private String adapter;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file.replace(Context.ROOT, Context.getRootFolder());
    }

    public String getAdapter() {
        return adapter;
    }

    public void setAdapter(String adapter) {
        this.adapter = adapter;
    }
}
