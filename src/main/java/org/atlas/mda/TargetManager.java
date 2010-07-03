package org.atlas.mda;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

/**
 * Responsible for loading and answering the targets by type
 * @author Steve Andrews
 */
public class TargetManager {

    private static final String TARGET = "targets/target";
    private static final String NAME = "name";
    private static final String STEREOTYPE = "stereotype";
    private static final String TEMPLATE = "template";
    private static final String OUTPUT_FILE = "outputFile";
    private static final String NAMESPACE = "namespace";
    private static final String OUTPUT_PATH = "outputPath";
    private static final String OVERWRITE = "overwrite";
    private static final String COLLECTION = "collection";
    private Map<Stereotype, List<Target>> singleTargets = new HashMap<Stereotype, List<Target>>();
    private Map<Stereotype, List<Target>> collectionTargets = new HashMap<Stereotype, List<Target>>();

    public TargetManager() {
        load();
    }

    private void load() {
        try {
            InputStream s = ConfigurationLoader.getTargetsAsStream();

            Digester d = new Digester();
            d.push(this);
            d.addObjectCreate(TARGET, Target.class);
            d.addSetProperties(TARGET, new String[]{NAME, STEREOTYPE, COLLECTION, TEMPLATE, OUTPUT_FILE, NAMESPACE, OUTPUT_PATH, OVERWRITE}, new String[]{NAME, STEREOTYPE, COLLECTION, TEMPLATE, OUTPUT_FILE, NAMESPACE, OUTPUT_PATH, OVERWRITE});
            d.addSetNext(TARGET, "addTarget");
            d.parse(s);
        } catch (IOException ex) {
            Logger.getLogger(TargetManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(TargetManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addTarget(Target target) {
        if (target.getCollection() == null || !target.getCollection().booleanValue()) {
            if (singleTargets.get(target.getTargetStereotype()) == null) {
                singleTargets.put(target.getTargetStereotype(), new ArrayList<Target>());
            }
            singleTargets.get(target.getTargetStereotype()).add(target);
        } else {
            if (collectionTargets.get(target.getTargetStereotype()) == null) {
                collectionTargets.put(target.getTargetStereotype(), new ArrayList<Target>());
            }
            collectionTargets.get(target.getTargetStereotype()).add(target);
        }
    }

    public List<Target> getTargets(Stereotype type) {
        return singleTargets.get(type);
    }

    public List<Target> getCollectionTargets(Stereotype type) {
        return collectionTargets.get(type);
    }
}
