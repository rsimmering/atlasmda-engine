package org.atlas.mda;

/**
 * Indicates attempt to overwrite a non-overwritable file
 * @author Steve Andrews
 */
public class OverwriteException extends Exception {

    private String outputFileName=null;

    public OverwriteException(String outputFileName) {
       super();

       this.outputFileName=outputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

}
