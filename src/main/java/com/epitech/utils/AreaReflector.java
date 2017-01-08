package com.epitech.utils;

import com.epitech.action.IAction;
import com.epitech.reaction.IReaction;
import com.epitech.service.IService;

import java.lang.reflect.Constructor;

/**
 * This class is used to instanciate some Area
 * class by String.
 */
public class                    AreaReflector {
    /**
     * This method is used to instanciate an action
     * by name.
     *
     * @param actionName th string action name.
     * @param token the token given at action construction.
     * @return a new instance of Action.
     */
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

    /**
     * This method is used to instanciate a reaction
     * by String name.
     *
     * @param reactionName the reaction name.
     * @param token the token given at reaction construction.
     * @param data the data returned by the run action method.
     * @see IAction#run()
     * @return a new reaction instance.
     */
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

    /**
     * This method is used to instanciate an action
     * by String name.
     *
     * @param serviceName the service name.
     * @return a new Service instance.
     */
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

    /**
     * This method is used to get service
     * name in Camel case.
     *
     * @param moduleName the module name to process.
     * @return a formatted string ( ex : 'foo' : 'FooService' )
     */
    public static String        getServiceNameByModuleName(String moduleName) {
        return  Character.toUpperCase(moduleName.charAt(0)) + moduleName.substring(1) + "Service";
    }
}
