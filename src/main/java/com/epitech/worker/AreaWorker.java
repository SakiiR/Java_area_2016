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

import java.lang.reflect.Constructor;
import java.security.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * This class is a Thread. It is used to
 * check for all user Area and execute
 * Actions and Reactions.
 */
public class AreaWorker implements Runnable {

    private UserRepository          userRepository;
    private AreaRepository          areaRepository;
    // etc ..

    public static long              timestamp;

    private boolean                 isRunning = true;

    public AreaWorker(UserRepository userRepository, AreaRepository areaRepository) {
        this.userRepository = userRepository;
        this.areaRepository = areaRepository;
    }

    private String                  getTokenByModuleName(User user, String moduleName) {
        for (UserModule userModule : user.getModules()) {
            if (userModule.getModule().getName().equals(moduleName)) {
                return userModule.getToken();
            }
        }
        return null;
    }

    private void                    processArea(User user, Area area) {
        String                      actionToken = this.getTokenByModuleName(user, area.getActionModuleName());
        String                      reactionToken = this.getTokenByModuleName(user, area.getReactionModuleName());
        IService                    actionService = AreaReflector.instanciateService(area.getActionModuleName());
        IService                    reactionService = AreaReflector.instanciateService(area.getReactionModuleName());
        IAction                     action = AreaReflector.instanciateAction(area.getActionName(), actionToken, actionService);

        if (action.run() == ErrorCode.SUCCESS) {
            IReaction reaction = AreaReflector.instanciateReaction(area.getReactionName(), reactionToken, reactionService, action.getData());
        }
    }

    @Override
    public void                     run() {
        List<User>                  users;

        AreaWorker.timestamp = new Date().getTime();

        while (this.isRunning) {
            users = this.userRepository.findAll();

            Logger.logSuccess("[WORKER] Checking for %d users last check time %d", users.size(), AreaWorker.timestamp);

            for (User user : users) {
                List<Area> areas = user.getAreas();
                for (Area area : areas) {
                    this.processArea(user, area);
                }
            }

            AreaWorker.timestamp = new Date().getTime();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
