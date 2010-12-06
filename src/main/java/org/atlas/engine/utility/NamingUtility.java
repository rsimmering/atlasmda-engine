package org.atlas.engine.utility;

import org.apache.commons.lang.StringUtils;

/**
 * Utility that provides basic naming functions
 *
 * @author andrews
 */
public class NamingUtility extends StringUtils {

    public static String addSpacerToCamelCase(String input, String spacer) {
        StringBuffer b = new StringBuffer();

        String[] words = StringUtils.splitByCharacterTypeCamelCase(input);
        int i = 1;
        for (String word : words) {
            b.append(word);
            if (i < words.length) {
                b.append(spacer);
            }
            i++;
        }

        return b.toString();
    }

    public static String addSpacerToCamelCaseUpper(String input, String spacer) {
        return addSpacerToCamelCase(input, spacer).toUpperCase();
    }
}
