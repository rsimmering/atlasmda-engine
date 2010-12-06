package org.atlas.engine;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

/**
 * Responsible for getting templates into the transformation engine
 *
 * @author andrews
 */
public class TemplateManager {

    private VelocityEngine velocityEngine = null;

    public TemplateManager() {

    }

    private VelocityEngine getEngine() throws TransformException {
        if (velocityEngine == null) {
            try {
                Properties props = new Properties();
                props.setProperty("resource.loader", "file");
                props.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
                props.setProperty("file.resource.loader.cache", "true");
                props.setProperty("file.resource.loader.path", Context.getTemplates());
                velocityEngine = new VelocityEngine();
                velocityEngine.init(props);
            } catch (Exception ex) {
                Logger.getLogger(TemplateManager.class.getName()).log(Level.SEVERE, null, ex);
                throw new TransformException("Unable to create Velocity engine", ex);
            }
        }

        return velocityEngine;
    }

    public Template getTemplate(Target target) throws TransformException {
        Template template = null;
        
        try {
            template = getEngine().getTemplate(target.getTemplate());
        } catch (ResourceNotFoundException ex) {
            Logger.getLogger(TemplateManager.class.getName()).log(Level.SEVERE, null, ex);
            throw new TransformException("Template [" + target.getTemplate() + "] not found", ex);
        } catch (ParseErrorException ex) {
            Logger.getLogger(TemplateManager.class.getName()).log(Level.SEVERE, null, ex);
            throw new TransformException("Unable to parse template [" + target.getTemplate() + "]", ex);
        } catch (Exception ex) {
            Logger.getLogger(TemplateManager.class.getName()).log(Level.SEVERE, null, ex);
            throw new TransformException("Unknown problem with template [" + target.getTemplate() + "]", ex);
        }

        return template;
    }
    
    
}
