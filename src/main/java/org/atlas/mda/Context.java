package org.atlas.mda;

import java.io.File;
import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;

/**
 * Responsible for loading up the context from the properties
 * 
 * @author Steve Andrews
 */
public class Context {

    public static final String ROOT = "${root}";
    private static final String FILE = "file";
    private static final String ADAPTER = "adapter";
    private static final String DIRECTORY = "dir";
    private static final String NAME = "name";
    private static final String IMPL = "impl";
    private Map<String, Output> outputs = new HashMap<String, Output>();
    private Map<String, Object> utilities = new HashMap<String, Object>();
    private List<ModelInput> modelInputs = new ArrayList<ModelInput>();
    private List<UtilityInput> utilityInputs = new ArrayList<UtilityInput>();
    private TemplateInput templateInput = new TemplateInput();
    private ModelManager modelManager = new ModelManager();
    private PrimitiveManager primitiveManager = new PrimitiveManager();
    private TargetManager targetManager = new TargetManager();
    private TemplateManager templateManager = new TemplateManager();
    private static String ROOT_FOLDER = "";
    private static Context INSTANCE = null;

    public static Context instance() throws TransformException {
        if (INSTANCE == null) {
            INSTANCE = new Context();
            INSTANCE.load();
        }

        return INSTANCE;
    }

    public static void reset() {
        INSTANCE = null;
    }

    private Context() {
    }

    private void load() throws TransformException {
        try {
            InputStream s = ConfigurationLoader.getContextAsStream();

            Digester d = new Digester();
            d.push(this);
            d.addObjectCreate("context/inputs/model", ModelInput.class);
            d.addSetProperties("context/inputs/model", new String[]{FILE, ADAPTER}, new String[]{FILE, ADAPTER});
            d.addSetNext("context/inputs/model", "addModelInput");

            d.addObjectCreate("context/inputs/templates", TemplateInput.class);
            d.addSetProperties("context/inputs/templates", new String[]{FILE, DIRECTORY}, new String[]{FILE, DIRECTORY});
            d.addSetNext("context/inputs/templates", "setTemplateInput");

            d.addObjectCreate("context/inputs/utility", UtilityInput.class);
            d.addSetProperties("context/inputs/utility", new String[]{NAME, IMPL}, new String[]{NAME, IMPL});
            d.addSetNext("context/inputs/utility", "addUtilityInput");


            d.addObjectCreate("context/outputs/output", Output.class);
            d.addSetProperties("context/outputs/output", new String[]{NAME, DIRECTORY}, new String[]{NAME, DIRECTORY});
            d.addSetNext("context/outputs/output", "addOutput");

            d.parse(s);

            //validateContext();
        } catch (IOException ex) {
            Logger.getLogger(TargetManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(TargetManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void validateContext() throws TransformException {
        if (modelInputs.isEmpty()) {
            throw new TransformException("No models specified!");
        }

        // Adapt all the PIM inputs to the metamodel
        for (ModelInput pim : getModelInputs()) {
            validateModelLocation(pim);
        }

        if (StringUtils.isBlank(templateInput.getDir())) {
            throw new TransformException("No Template Directory Specified!");
        }
        File templateDir = new File(templateInput.getDir());

        //Check if directory exists, if not then see if the manual config location was set
        if (!templateDir.exists() && StringUtils.isNotBlank(ConfigurationLoader.getConfigLocation())) {
            String sTemplateDir = ConfigurationLoader.getConfigLocation() + "/" + templateInput.getDir();
            templateDir = new File(sTemplateDir);

            if (!templateDir.exists()) {
                throw new TransformException("Cannot locate Template Directory: " + templateInput.getDir());
            }

            // Normalize to Absolute Path
            String fullPath = templateDir.getAbsolutePath();
            templateInput.setDir(fullPath);
        }
    }

    private void validateModelLocation(ModelInput input) throws TransformException {
        String sConfigModelPath = ConfigurationLoader.getConfigLocation() + "/" + input.getFile();

        if (StringUtils.isNotBlank(input.getFile()) && !new File(input.getFile()).isFile()) {
            // Check classpath
            URL url = Context.class.getClassLoader().getResource(input.getFile());
            if (url != null && StringUtils.isNotBlank(url.getFile())) {
                input.setFile(url.getFile());
            } //check manual Config location
            else if (new File(sConfigModelPath).isFile()) {
                input.setFile(sConfigModelPath);
            } else {
                throw new TransformException("Cannot locate File: " + input.getFile());
            }
        } else if (StringUtils.isBlank(input.getFile())) {
            throw new TransformException("No File Specified!!");
        }
        Logger.getLogger(Context.class.getName()).log(Level.INFO, "Found Model File at: " + input.getFile());
    }

    public static void setRootFolder(String rootFolder) {
        ROOT_FOLDER = rootFolder;
        System.out.println(ROOT_FOLDER);
    }

    public static String getRootFolder() {
        return ROOT_FOLDER;
    }

    public static ModelManager getModelManager() throws TransformException {
        return instance().modelManager;
    }

    public static PrimitiveManager getPrimitives() throws TransformException {
        return instance().primitiveManager;
    }

    public static TargetManager getTargetManager() throws TransformException {
        return instance().targetManager;
    }

    public static TemplateManager getTemplateManager() throws TransformException {
        return instance().templateManager;
    }

    public void setTemplateInput(TemplateInput template) {
        templateInput = template;
    }

    public static TemplateInput getTemplateInput() throws TransformException {
        return instance().templateInput;
    }

    public void addUtilityInput(UtilityInput utility) {
        utilityInputs.add(utility);
    }

    public static List<UtilityInput> getUtilityInputs() throws TransformException {
        return instance().utilityInputs;
    }

    public void addModelInput(ModelInput model) {
        modelInputs.add(model);
    }

    public static List<ModelInput> getModelInputs() throws TransformException {
        return instance().modelInputs;
    }

    public void addOutput(Output output) {
        outputs.put(output.getName(), output);
    }

    public static Output getOutput(String name) throws TransformException {
        return instance().outputs.get(name);
    }

    public static Map<String, Output> getOutputs() throws TransformException {
        return instance().outputs;
    }

    public static Map<String, Object> getUtilities() throws TransformException {
        if (instance().utilities.size() == 0 && instance().utilityInputs.size() > 0) {
            for (UtilityInput u : instance().utilityInputs) {
                try {
                    Class clazz = Class.forName(u.getImpl());
                    Object utilityClass = clazz.newInstance();
                    instance().utilities.put(u.getName(), utilityClass);
                } catch (ClassNotFoundException e) {
                    Logger.getLogger(TransformException.class.getName()).log(Level.SEVERE, "Unable to find class from file [" + u.getImpl() + "]", e);
                    throw new TransformException("Unable to find class from file [" + u.getImpl() + "]", e);
                } catch (InstantiationException e) {
                    Logger.getLogger(TransformException.class.getName()).log(Level.SEVERE, "Unable to instanciate class from file [" + u.getImpl() + "]", e);
                    throw new TransformException("Unable to instantiate class from file [" + u.getImpl() + "]", e);
                } catch (IllegalAccessException e) {
                    Logger.getLogger(TransformException.class.getName()).log(Level.SEVERE, "IllegalAccess of class from file [" + u.getImpl(), e);
                    throw new TransformException("IllegalAccess of class from file [" + u.getImpl() + "]", e);
                }
            }
        }

        return instance().utilities;
    }

    public static Object getUtility(String name) throws TransformException {
        return getUtilities().get(name);
    }

    public void setUtilities(Map<String, Object> utilities) {
        this.utilities = utilities;
    }
}
