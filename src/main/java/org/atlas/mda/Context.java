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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    private static final String DIR = "dir";
    private static final String NAME = "name";
    private static final String VALUE = "value";
    private static final String IMPL = "impl";
    private static final String STEREOTYPE = "stereotype";
    private static final String TEMPLATE = "template";
    private static final String OUTPUT_FILE = "outputFile";
    private static final String OUTPUT_PATH = "outputPath";
    private static final String OVERWRITE = "overwrite";
    private static final String COLLECTION = "collection";
    private static final String VARIABLE = "\\$\\{.*?\\}";
    private static final String SOURCE = ".source.";
    
    private static final String PROPERTY = "context/properties/property";
    private static final String TEMPLATES = "context/templates";
    private static final String PRIMITIVES = "context/primitives";
    private static final String UTILITY = "context/utilities/utility";
    private static final String MODEL = "context/models/model";
    private static final String TARGET = "context/targets/target";
    private static final String TARGET_PROPERTY = "context/targets/target/property";

    private static final String SET_PROPERTY = "setProperty";
    private static final String SET_TEMPLATES = "setTemplates";
    private static final String SET_PRIMITIVES = "setPrimitives";
    private static final String ADD_MODEL_INPUT = "addModelInput";
    private static final String ADD_TARGET = "addTarget";
    private static final String ADD_UTILITY_INPUT = "addUtilityInput";

    private String templates;
    private String primitives;
    private Map<String, Object> utilities = new HashMap<String, Object>();
    private List<ModelInput> modelInputs = new ArrayList<ModelInput>();
    private List<UtilityInput> utilityInputs = new ArrayList<UtilityInput>();
    private ModelManager modelManager = new ModelManager();
    private PrimitiveManager primitiveManager;
    private TemplateManager templateManager;
    private Map<String, String> properties;
    private Map<Stereotype, List<Target>> singleTargets = new HashMap<Stereotype, List<Target>>();
    private Map<Stereotype, List<Target>> collectionTargets = new HashMap<Stereotype, List<Target>>();
    private Set<String> outputPaths = new HashSet<String>();

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

            d.addCallMethod(PROPERTY, SET_PROPERTY, 2);
            d.addCallParam(PROPERTY, 0, NAME);
            d.addCallParam(PROPERTY, 1, VALUE);

            d.addObjectCreate(MODEL, ModelInput.class);
            d.addSetProperties(MODEL, new String[]{FILE, ADAPTER}, new String[]{FILE, ADAPTER});
            d.addSetNext(MODEL, ADD_MODEL_INPUT);

            d.addCallMethod(TEMPLATES, SET_TEMPLATES, 1);
            d.addCallParam(TEMPLATES, 0, DIR);

            d.addCallMethod(PRIMITIVES, SET_PRIMITIVES, 1);
            d.addCallParam(PRIMITIVES, 0, FILE);

            d.addObjectCreate(UTILITY, UtilityInput.class);
            d.addSetProperties(UTILITY, new String[]{NAME, IMPL}, new String[]{NAME, IMPL});
            d.addSetNext(UTILITY, ADD_UTILITY_INPUT);
            
            d.addObjectCreate(TARGET, Target.class);
            d.addSetProperties(TARGET, new String[]{NAME, STEREOTYPE, COLLECTION, TEMPLATE, OUTPUT_FILE, OUTPUT_PATH, OVERWRITE}, new String[]{NAME, STEREOTYPE, COLLECTION, TEMPLATE, OUTPUT_FILE, OUTPUT_PATH, OVERWRITE});
            d.addCallMethod(TARGET_PROPERTY, SET_PROPERTY, 2);
            d.addCallParam(TARGET_PROPERTY, 0, NAME);
            d.addCallParam(TARGET_PROPERTY, 1, VALUE);
            d.addSetNext(TARGET, ADD_TARGET);

            d.parse(s);

        } catch (IOException ex) {
            Logger.getLogger(Context.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Context.class.getName()).log(Level.SEVERE, null, ex);
        }

        primitiveManager = new PrimitiveManager();
        templateManager = new TemplateManager();
    }

    private void validateContext() throws TransformException {
        if (modelInputs.isEmpty()) {
            throw new TransformException("No models specified!");
        }

        // Adapt all the PIM inputs to the metamodel
        for (ModelInput pim : getModelInputs()) {
            validateModelLocation(pim);
        }

        if (StringUtils.isBlank(templates)) {
            throw new TransformException("No Template Directory Specified!");
        }
        File templateDir = new File(templates);

        //Check if directory exists, if not then see if the manual config location was set
        if (!templateDir.exists() && StringUtils.isNotBlank(ConfigurationLoader.getConfigLocation())) {
            String sTemplateDir = ConfigurationLoader.getConfigLocation() + "/" + templates;
            templateDir = new File(sTemplateDir);

            if (!templateDir.exists()) {
                throw new TransformException("Cannot locate Template Directory: " + templates);
            }

            // Normalize to Absolute Path
            String fullPath = templateDir.getAbsolutePath();
            setTemplates(fullPath);
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

    public static PrimitiveManager getPrimitiveManager() throws TransformException {
        return instance().primitiveManager;
    }

    public static TemplateManager getTemplateManager() throws TransformException {
        return instance().templateManager;
    }

    public static void setTemplates(String template) throws TransformException {
        instance().templates = replaceVariables(template);
    }

    public static String getTemplates() throws TransformException {
        return instance().templates;
    }

    public static void setPrimitives(String p) throws TransformException {
        instance().primitives = replaceVariables(p);
    }

    public static String getPrimitives() throws TransformException {
        return instance().primitives;
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

    public Map<String, String> getProperties() {
        if (properties == null) {
            properties = new HashMap<String, String>();
        }

        return properties;
    }

    public static String getProperty(String name) throws TransformException {
        return instance().getProperties().get(name);
    }

    public static void setProperty(String name, String value) throws TransformException {
        String v = replaceVariables(value);
        instance().getProperties().put(name, replaceVariables(v));
        if(name.contains(SOURCE)) {
            instance().outputPaths.add(v);
        }
    }

    public static String replaceVariables(String parameterized) throws TransformException {
        String resolved = parameterized.replace(Context.ROOT, Context.getRootFolder());

        Pattern pattern = Pattern.compile(VARIABLE);
        Matcher matcher = pattern.matcher(resolved);

        while (matcher.find()) {
            String match = matcher.group();

            // Trim the delimiters
            String name = match.substring(2, match.length() - 1);

            //Attempt to resolve the output from the context set of outputs
            String value = Context.getProperty(name);
            if (value == null) {
                Logger.getLogger(Context.class.getName()).log(Level.SEVERE, "Property '" + name + "' is specified in '" + parameterized + "' but it could not be resolved in the context.xml");
                throw new TransformException("Could not resolve property '" + name + "'");
            }

            resolved = resolved.replace(match, value);
        }

        return resolved;

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

    public static List<Target> getTargets(Stereotype type) throws TransformException {
        return instance().singleTargets.get(type);
    }

    public static List<Target> getCollectionTargets(Stereotype type) throws TransformException {
        return instance().collectionTargets.get(type);
    }

    public static Set<String> getOutputPaths() throws TransformException {
        return instance().outputPaths;
    }
}
