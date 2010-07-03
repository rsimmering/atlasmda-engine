package org.atlas.mda;


public class Output {
    private String name;
    private String dir;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir.replace(Context.ROOT, Context.getRootFolder());
        System.out.println(this.dir);
    }
}
