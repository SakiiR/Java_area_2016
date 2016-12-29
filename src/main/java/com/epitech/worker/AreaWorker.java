package com.epitech.worker;

import com.epitech.model.User;
import com.epitech.repository.AreaRepository;
import com.epitech.repository.UserRepository;
import com.epitech.utils.Logger;

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

    private boolean                 isRunning = true;

    public AreaWorker(UserRepository userRepository, AreaRepository areaRepository) {
        this.userRepository = userRepository;
        this.areaRepository = areaRepository;
    }

    @Override
    public void                     run() {
        List<User>                  users;

        while (this.isRunning) {
            users = this.userRepository.findAll();

            Logger.logSuccess("[WORKER] Checking for %d users", users.size());

            try {
             Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
