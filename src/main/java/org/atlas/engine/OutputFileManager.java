package org.atlas.engine;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.atlas.model.metamodel.Type;

/**
 * Responsible for creating output files from the transformation engine
 *
 * @author Steve Andrews
 */
public final class OutputFileManager {

    private static final String NAME_PLACEHOLDER = "${name}";
    private static final String TYPE_NAMESPACE_PLACEHOLDER = "${type.namespace}";
    /**
     * Generates the output folder name based on output properties from the context
     * @param target
     * @return
     * @throws TransformException
     */
    public static String getOutputFolderName(Target target, Type type) throws TransformException {
        String outputPath = target.getOutputPath();

        // Find the Output with Regex
        Pattern pattern = Pattern.compile("\\$\\{.*?\\}");
        Matcher matcher = pattern.matcher(outputPath);

        while (matcher.find()) {
            String match = matcher.group();

            if(TYPE_NAMESPACE_PLACEHOLDER.equals(match)){
                String namespace = type.getNamespace();
                namespace = namespace.replace('.', '/');
                outputPath = outputPath.replace(match, namespace);
            }
        }

        return outputPath;
    }

    /**
     * Generates the
     * @param target
     * @param type
     * @return
     */
    public static String getOutputFileName(Target target, Type type) {
        if (type == null) {
            return target.getOutputFile();
        }
        return StringUtils.replace(target.getOutputFile(), NAME_PLACEHOLDER, type.getName());
    }

    /**
     * Creates the output file for the target being generated for collections.  Overwrites overwritable files if they already exist.
     * @param target
     * @return
     */
    public static File getOutputFile(Target target) throws OverwriteException, TransformException {
        return getOutputFile(target, null);
    }

    /**
     * Creates the output file for the target being generated.  Overwrites overwritable files if they already exist.
     * @param target
     * @return
     */
    public static File getOutputFile(Target target, Type type) throws OverwriteException, TransformException {
        String outputFolderName = getOutputFolderName(target, type);
        String outputFileName = getOutputFileName(target, type);
        File file = new File(outputFolderName, outputFileName);
        if (file.exists()) {
            if (target.isOverwritable()) {
                file.delete();
                file = new File(outputFolderName, outputFileName);
            } else {
                throw new OverwriteException(outputFileName);
            }
        }

        file.getParentFile().mkdirs();

        return file;
    }
}
