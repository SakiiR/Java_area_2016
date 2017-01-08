package com.epitech.worker;

import com.epitech.action.IAction;
import com.epitech.model.Area;
import com.epitech.model.User;
import com.epitech.model.UserModule;
import com.epitech.reaction.IReaction;
import com.epitech.repository.AreaRepository;
import com.epitech.repository.UserModuleRepository;
import com.epitech.repository.UserRepository;
import com.epitech.service.IService;
import com.epitech.utils.AreaReflector;
import com.epitech.utils.ErrorCode;
import com.epitech.utils.Logger;
import com.epitech.utils.NotificationService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is a Thread. It is used to
 * check for all user Area and execute
 * Actions and Reactions.
 */
public class                        AreaWorker implements Runnable {

    private UserRepository          userRepository;
    private AreaRepository          areaRepository;
    private NotificationService     notificationService;
    private UserModuleRepository    userModuleRepository;

    public static Date              lastCheck;

    private boolean                 isRunning = true;

    private List<Area>              areaToRemove = new ArrayList<>();
    private List<UserModule>        userModuleToRemove = new ArrayList<>();

    /**
     * Contructor
     *
     * @param userRepository user repository
     * @param areaRepository area repository
     */
    public                          AreaWorker(UserRepository userRepository, AreaRepository areaRepository, NotificationService notificationService, UserModuleRepository userModuleRepository) {
        this.userRepository = userRepository;
        this.areaRepository = areaRepository;
        this.notificationService = notificationService;
        this.userModuleRepository = userModuleRepository;
    }

    /**
     * Check if the date in going after the stored one.
     *
     * @param date the date to check
     * @return if the date is valid
     */
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
            if (userModule != null && userModule.getModule().getName().equals(moduleName)) {
                return userModule.getToken();
            }
        }
        return null;
    }

    private void                    handleAuthError(User user, String moduleName) {
        for (Area a : user.getAreas()) {
            if (a.getActionModuleName().equals(moduleName) || a.getReactionModuleName().equals(moduleName)) {
                a.setTmpUser(user);
                this.areaToRemove.add(a);
                this.notificationService.send(user, String.format("you have been disconnected from Area %s <-> %s : %s", a.getActionName(), a.getReactionName(), a.getDescription()));
            }
        }
        for (UserModule userModule : user.getModules()) {
            if (userModule.getModule().getName().equals(moduleName)) {
                this.userModuleToRemove.add(userModule);
                this.notificationService.send(user, String.format("You have been disconnected from %s", userModule.getModule().getName()));
            }
        }
        Logger.logSuccess("Removed and sent notification for updating invalid userModule And Area ( BAD_TOKEN )");
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
        ErrorCode                   actionStatus = action.run();
        ErrorCode                   reactionStatus;

        if (actionStatus == ErrorCode.AUTH) {
            this.handleAuthError(user, area.getActionModuleName());
            return;
        }
        if (actionStatus == ErrorCode.SUCCESS && action.getData() != null) {
            IReaction reaction = AreaReflector.instanciateReaction(area.getReactionName(), reactionToken, action.getData());
            reactionStatus = reaction.run(action.getData());
            if (reactionStatus == ErrorCode.AUTH) {
                this.handleAuthError(user, area.getReactionModuleName());
            }
            return;
        }
        Logger.logInfo("Finished Area !");
    }

    /**
     * Remove all unused area and usermodule
     * with bad token.
     */
    private void                    removeUselessAreaAndModule() {
        User                        user;

        if (this.areaToRemove.size() > 0) {
            user = this.areaToRemove.get(0).getTmpUser();
            user.getAreas().removeAll(this.areaToRemove);
            this.userRepository.save(user);
        }

        if (this.userModuleToRemove.size() > 0) {
            user = this.userModuleToRemove.get(0).getUser();
            user.getModules().removeAll(this.userModuleToRemove);
            this.userModuleRepository.delete(this.userModuleToRemove);
            this.userRepository.save(user);
        }
        this.areaToRemove.clear();
        this.userModuleToRemove.clear();
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
                this.removeUselessAreaAndModule();
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
