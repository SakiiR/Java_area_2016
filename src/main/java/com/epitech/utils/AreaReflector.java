package com.epitech.utils;

import com.epitech.action.IAction;
import com.epitech.reaction.IReaction;
import com.epitech.service.IService;
import org.apache.tomcat.util.bcel.Const;

import java.lang.reflect.Constructor;

public class                    AreaReflector {
    public static IAction       instanciateAction(String actionName, String token, IService service) {
        IAction                 actionObject = null;

        try {
            Class actionClass = Class.forName(actionName);
            Class[] types = {String.class, IService.class};
            Constructor actionConstructor = actionClass.getConstructor(types);
            Object[] parameters = {token, service};
            actionObject = (IAction) actionConstructor.newInstance(parameters);
        } catch (Exception e) {
            Logger.logWarning("Failed to instanciate Action %s", actionName);
        }
        return actionObject;
    }

    public static IReaction     instanciateReaction(String reactionName, String token, IService service, Object data) {
        IReaction               reactionObject = null;

        return reactionObject;
    }

    public static IService      instanciateService(String serviceName) {
        IService                serviceObject = null;

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
