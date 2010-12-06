package org.atlas.engine;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

public class ConfigurationLoader {

    private static final String CONTEXT = "context.xml";
    private static String configLocation = null;

    private ConfigurationLoader() {
    }

    public static InputStream getContextAsStream() throws IOException {
        InputStream s = null;
        if (configLocation != null) {
            URL contextURL = URI.create("file:///" + configLocation + "//" + CONTEXT).toURL();
            s = contextURL.openStream();
        } // Not specified so try to load from classpath
        else {
            s = ConfigurationLoader.class.getClassLoader().getResourceAsStream(CONTEXT);
        }
        return s;
    }

    public static void setConfigLocation(String location) {
        configLocation = location.replace(Context.ROOT, Context.getRootFolder());
    }

    public static String getConfigLocation() {
        return configLocation;
    }
}
