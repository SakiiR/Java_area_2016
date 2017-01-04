package com.epitech.worker;

import com.epitech.action.IAction;
import com.epitech.model.Area;
import com.epitech.model.User;
import com.epitech.model.UserModule;
import com.epitech.reaction.IReaction;
import com.epitech.repository.AreaRepository;
import com.epitech.repository.UserRepository;
import com.epitech.service.IService;
import com.epitech.utils.AreaReflector;
import com.epitech.utils.ErrorCode;
import com.epitech.utils.Logger;

import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class is a Thread. It is used to
 * check for all user Area and execute
 * Actions and Reactions.
 */
public class AreaWorker implements Runnable {

    private UserRepository          userRepository;
    private AreaRepository          areaRepository;
    // etc ..

    public static Date              lastCheck;

    private boolean                 isRunning = true;

    /**
     * Contructor
     *
     * @param userRepository user repository
     * @param areaRepository area repository
     */
    public                          AreaWorker(UserRepository userRepository, AreaRepository areaRepository) {
        this.userRepository = userRepository;
        this.areaRepository = areaRepository;
    }

    public static boolean           isNewEntity(Date date) {
        return date.getTime() > AreaWorker.lastCheck.getTime();
    }

    /**
     * Return a token string by module name.
     *
     * @param user the user to search in.
     * @param moduleName the module name we are searching for.
     * @return a token string.
     */
    private String                  getTokenByModuleName(User user, String moduleName) {
        for (UserModule userModule : user.getModules()) {
            if (userModule.getModule().getName().equals(moduleName)) {
                return userModule.getToken();
            }
        }
        return null;
    }

    /**
     * This method process a user area.
     *
     * @param user the area owner.
     * @param area the area to process.
     */
    private void                    processArea(User user, Area area) {
        String                      actionToken = this.getTokenByModuleName(user, area.getActionModuleName());
        String                      reactionToken = this.getTokenByModuleName(user, area.getReactionModuleName());
        IService                    actionService = AreaReflector.instanciateService(area.getActionModuleName());
        IService                    reactionService = AreaReflector.instanciateService(area.getReactionModuleName());
        IAction                     action = AreaReflector.instanciateAction(area.getActionName(), actionToken);

        if (action.run() == ErrorCode.SUCCESS && action.getData() != null) {
            IReaction reaction = AreaReflector.instanciateReaction(area.getReactionName(), reactionToken, action.getData());
            reaction.run(action.getData());
        }
        Logger.logInfo("Finished Area !");
    }

    /**
     * The main thread.
     */
    @Override
    public void                     run() {
        List<User>                  users;

        AreaWorker.lastCheck = new Date();

        while (this.isRunning) {
            users = this.userRepository.findAll();

            Logger.logSuccess("[WORKER] Checking for %d users last check time %s", users.size(), AreaWorker.lastCheck.toString());
            for (User user : users) {
                List<Area> areas = user.getAreas();
                for (Area area : areas) {
                    Logger.logInfo("Processing area : %s", area.toString());
                    this.processArea(user, area);
                }
            }
            AreaWorker.lastCheck = new Date();
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
