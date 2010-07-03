package org.atlas.mda;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

/**
 *
 * @author Steve Andrews
 */
public class PrimitiveManager {

    private Map<String, String> primitives = new HashMap<String, String>();
    private static final String PRIMITIVE = "primitives/primitive";
    private static final String PIM = "pim";
    private static final String PSM = "psm";

    public PrimitiveManager() {
        load();
    }

    private void load() {
        try {
            InputStream s = ConfigurationLoader.getPrimitivesAsStream();

            Digester d = new Digester();
            d.push(this);
            d.addObjectCreate(PRIMITIVE, Primitive.class);
            d.addSetProperties(PRIMITIVE, new String[]{PIM, PSM}, new String[]{PIM, PSM});
            d.addSetNext(PRIMITIVE, "addPrimitive");

            d.parse(s);
        } catch (IOException ex) {
            Logger.getLogger(TargetManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(TargetManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addPrimitive(Primitive primitive) {
        primitives.put(primitive.getPim(), primitive.getPsm());
    }

    public String getPsmType(String pimType) throws TransformException {
        return primitives.get(pimType);
    }
}
