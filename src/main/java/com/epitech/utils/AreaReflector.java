package com.epitech.utils;

import com.epitech.action.IAction;
import com.epitech.reaction.IReaction;
import com.epitech.service.IService;
import org.apache.tomcat.util.bcel.Const;

import java.lang.reflect.Constructor;

public class                    AreaReflector {
    public static IAction       instanciateAction(String actionName, String token) {
        IAction                 actionObject = null;

        actionName = "com.epitech.action." + actionName;
        try {
            Class actionClass = Class.forName(actionName);
            Class[] types = {String.class};
            Constructor actionConstructor = actionClass.getConstructor(types);
            Object[] parameters = {token};
            actionObject = (IAction) actionConstructor.newInstance(parameters);
        } catch (Exception e) {
            Logger.logWarning("Failed to instanciate Action %s", actionName);
            e.printStackTrace();
        }
        return actionObject;
    }

    public static IReaction     instanciateReaction(String reactionName, String token, Object data) {
        IReaction               reactionObject = null;

        reactionName = "com.epitech.reaction." + reactionName;
        try {
            Class reactionClass = Class.forName(reactionName);
            Class[] types = {String.class};
            Constructor reactionConstructor = reactionClass.getConstructor(types);
            Object[] parameters = {token};
            reactionObject = (IReaction) reactionConstructor.newInstance(parameters);
        } catch (Exception e) {
            Logger.logWarning("Failed to instanciate Reaction %s", reactionName);
            e.printStackTrace();
        }
        return reactionObject;
    }

    public static IService      instanciateService(String serviceName) {
        IService                serviceObject = null;

        serviceName = "com.epitech.service." + AreaReflector.getServiceNameByModuleName(serviceName);
        try {
            Class serviceClass = Class.forName(serviceName);
            Constructor serviceConstructor = serviceClass.getConstructor();
            serviceObject = (IService) serviceConstructor.newInstance();
        } catch (Exception e) {
            Logger.logWarning("Failed to instanciate service : %s", serviceName);
        }

        return serviceObject;
    }

    public static String        getServiceNameByModuleName(String moduleName) {
        return  Character.toUpperCase(moduleName.charAt(0)) + moduleName.substring(1) + "Service";
    }
}
