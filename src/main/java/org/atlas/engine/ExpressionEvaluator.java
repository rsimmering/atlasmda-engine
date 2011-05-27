package org.atlas.engine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jboolexpr.BooleanExpression;
import jboolexpr.MalformedBooleanException;

import org.atlas.model.metamodel.Type;

public class ExpressionEvaluator {
	private static ExpressionEvaluator INSTANCE = null;
	private static final String TYPE = "type";
    
    public static ExpressionEvaluator instance(){
        if (INSTANCE == null) {
            INSTANCE = new ExpressionEvaluator();
        }

        return INSTANCE;
    }

    public static void reset() {
        INSTANCE = null;
    }

    private  ExpressionEvaluator() {
    }
    
    public static boolean evaluateExpression(String expression, Type type) throws TransformException{
    	if("true".equals(expression)){
    		return true;
    	}
    	else if("false".equals(expression)){
    		return false;
    	}
    	else{
    		// Find the Output with Regex
            Pattern pattern = Pattern.compile("\\$\\{.*?\\}");
            Matcher matcher = pattern.matcher(expression);

            while (matcher.find()) {
                String match = matcher.group();
                //trim surrounding
                String matchToEvaluate = match.substring(2, match.length()-1);
                
                if(matchToEvaluate.startsWith(TYPE)){
                	String methodCall = matchToEvaluate.substring(5);
                	String methodName = methodCall.substring(0, methodCall.indexOf("("));
                	Object[] parameters = parseParameters(methodCall, type);
                	
                	Class c = type.getClass();
                	Method mToCall=null;
                	Method[] methods = c.getMethods();
                	for(Method m : methods){
                		if(m.getName().equals(methodName)){
                			mToCall = m;
                			break;
                		}
                	}
                	
                	try {
						Object result = mToCall.invoke(type, parameters);
						expression = expression.replace(match, result.toString());
					}
                	catch (IllegalArgumentException e) {
                		throw new TransformException("Illegal Argument sent to method \""+methodName+"\" in expression: "+expression);
					} catch (IllegalAccessException e) {
						throw new TransformException("Illegal Access in method \""+methodName+"\" in expression: "+expression);
					} catch (InvocationTargetException e) {
						throw new TransformException("Invocation Exception in method \""+methodName+"\" in expression: "+expression);
					}
                }
                else{
                	String methodName = null;
                	try {
						Map<String, Object> utilities = Context.getUtilities();
						Object utility = null;
						Iterator<String> utilNames = utilities.keySet().iterator();
						
						while(utilNames.hasNext()){
							String name = utilNames.next();
							if(matchToEvaluate.startsWith(name)){
								// Found a Utility
								utility = utilities.get(name);
								break;
							}
						}
						if(utility == null){
							throw new TransformException("Invalid object "+ matchToEvaluate + " in expression: "+expression);
						}

	                	String methodCall = matchToEvaluate.substring(matchToEvaluate.indexOf(".")+1);
	                	methodName = methodCall.substring(0, methodCall.indexOf("("));
	                	Object[] parameters = parseParameters(methodCall, type);
	                	
	                	Class c = utility.getClass();
	                	Method mToCall=null;
	                	Method[] methods = c.getMethods();
	                	for(Method m : methods){
	                		if(m.getName().equals(methodName)){
	                			mToCall = m;
	                			break;
	                		}
	                	}
	                	if(mToCall == null){
	                		throw new TransformException("Could find method \""+methodName+"\" in expression: "+expression);
	                	}
	                	
						Object result = mToCall.invoke(type, parameters);
						expression = expression.replace(match, result.toString());
						
                	} catch (IllegalArgumentException e) {
                		throw new TransformException("Illegal Argument sent to method \""+methodName+"\" in expression: "+expression);
					} catch (IllegalAccessException e) {
						throw new TransformException("Illegal Access in method \""+methodName+"\" in expression: "+expression);
					} catch (InvocationTargetException e) {
						throw new TransformException("Invocation Exception in method \""+methodName+"\" in expression: "+expression);
					}
                }
            }
    	}
    	try {
			BooleanExpression be = BooleanExpression.readLeftToRight(expression);
			return be.booleanValue();
		} catch (MalformedBooleanException e) {
			throw new TransformException("Error in boolean expression: "+expression + " "+e.getMessage());
		}
    }
    
    private static Object[] parseParameters(String methodCall, Type type){
    	String parameterString = methodCall.substring(methodCall.indexOf("(")+1, methodCall.indexOf(")"));
    	String[] parameters = (String[])parameterString.split("[,]");
    	Object[] results = new Object[parameters.length];
    	for(int i=0; i < parameters.length; i++){
    		String parameter = parameters[i].trim();
    		if(parameter.startsWith("'") && parameter.endsWith("'")){
    			results[i] = parameter.substring(1,parameter.length()-1);
    		}
    		else if(TYPE.equals(parameter)){
    			results[i] = type;
    		}
    		else{
    			results[i] = parameter;
    		}
    	}
    	return results;
    	
    }
}
