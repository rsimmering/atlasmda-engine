package org.atlas.engine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.atlas.model.metamodel.Type;

/**
 * Responsible for transforming a type to a target output file based on a template
 * @author Steve Andrews
 */
public class TypeTransformer {

    private static void addUtilities(VelocityContext vc) throws TransformException {
        Set<String> utilities = Context.getUtilities().keySet();
        for (String utility : utilities) {
            vc.put(utility, Context.getUtility(utility));
        }
    }

    public static File transform(Target target, Type type) throws TransformException {
    	String conditional = target.getCondition();
    	if(!StringUtils.isBlank(conditional)){
    		Condition c = Context.getCondition(conditional);
    		if(c == null){
    			throw new TransformException("Condition [" + conditional + "] does not exist!");
    		}
    		if(!ExpressionEvaluator.evaluateExpression(c.getExpression(), type)){
        		return null;
    		}
    	}
    	
        File outputFile = null;
        FileWriter fw = null;
        Template template = null;
        try {
            outputFile = OutputFileManager.getOutputFile(target, type);
            fw = new FileWriter(outputFile, false);

            VelocityContext vc = new VelocityContext();
            vc.put("context", Context.instance());
            vc.put("target", target);
            vc.put("type", type);
            addUtilities(vc);

            template = Context.getTemplateManager().getTemplate(target);
            template.merge(vc, fw);

            fw.close();
        } catch (ResourceNotFoundException ex) {
            Logger.getLogger(TemplateManager.class.getName()).log(Level.SEVERE, null, ex);
            throw new TransformException("Template [" + template.getName() + "] not found", ex);
        } catch (ParseErrorException ex) {
            Logger.getLogger(TemplateManager.class.getName()).log(Level.SEVERE, null, ex);
            throw new TransformException("Unable to parse template [" + template.getName() + "]", ex);
        } catch (MethodInvocationException ex) {
            Logger.getLogger(TemplateManager.class.getName()).log(Level.SEVERE, null, ex);
            throw new TransformException("Unknown problem with template [" + template.getName() + "]", ex);
        } catch (IOException ex) {
            Logger.getLogger(TypeTransformer.class.getName()).log(Level.SEVERE, null, ex);
            throw new TransformException("Unable to write to file [" + outputFile.getName() + "]", ex);
        } catch (OverwriteException ex) {
            Logger.getLogger(TypeTransformer.class.getName()).log(Level.INFO, "Output file [" + ex.getOutputFileName() + "] already exists");
        } catch (Exception ex) {
            Logger.getLogger(TypeTransformer.class.getName()).log(Level.SEVERE, null, ex);
            throw new TransformException("Unable to write to file [" + outputFile.getName() + "]", ex);
        }

        return outputFile;
    }

    public static File transformCollection(Target target, Collection<Type> types) throws TransformException {
    	String condition = target.getCondition();
    	if(!StringUtils.isBlank(condition)){
    		Condition c = Context.getCondition(condition);
    		if(c == null){
    			throw new TransformException("Condition [" + condition + "] does not exist!");
    		}
    		
    		Collection<Type> result = new HashSet<Type>();
    		for(Type type : types){
    			if(ExpressionEvaluator.evaluateExpression(c.getExpression(), type)){
    				result.add(type);
    			}
    		}
    		types = result;
    	}
    	if(types.size() == 0)
    		return null;
    	
        File outputFile = null;
        FileWriter fw = null;
        Template template = null;
        try {
            outputFile = OutputFileManager.getOutputFile(target);
            fw = new FileWriter(outputFile, false);

            VelocityContext vc = new VelocityContext();
            vc.put("context", Context.instance());
            vc.put("target", target);
            vc.put("types", types);
            addUtilities(vc);

            template = Context.getTemplateManager().getTemplate(target);
            template.merge(vc, fw);

            fw.close();
        } catch (ResourceNotFoundException ex) {
            Logger.getLogger(TemplateManager.class.getName()).log(Level.SEVERE, null, ex);
            throw new TransformException("Template [" + template.getName() + "] not found", ex);
        } catch (ParseErrorException ex) {
            Logger.getLogger(TemplateManager.class.getName()).log(Level.SEVERE, null, ex);
            throw new TransformException("Unable to parse template [" + template.getName() + "]", ex);
        } catch (MethodInvocationException ex) {
            Logger.getLogger(TemplateManager.class.getName()).log(Level.SEVERE, null, ex);
            throw new TransformException("Unknown problem with template [" + template.getName() + "]", ex);
        } catch (IOException ex) {
            Logger.getLogger(TypeTransformer.class.getName()).log(Level.SEVERE, null, ex);
            throw new TransformException("Unable to write to file [" + outputFile.getName() + "]", ex);
        } catch (OverwriteException ex) {
            Logger.getLogger(TypeTransformer.class.getName()).log(Level.INFO, "Output file [" + ex.getOutputFileName() + "] already exists");
        } catch (Exception ex) {
            Logger.getLogger(TypeTransformer.class.getName()).log(Level.SEVERE, null, ex);
            throw new TransformException("Unable to write to file [" + outputFile.getName() + "]", ex);
        }

        return outputFile;
    }
}
