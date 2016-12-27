package com.epitech;


import com.epitech.model.User;
import com.epitech.repository.UserRepository;
import com.epitech.utils.Logger;
import com.epitech.utils.NotificationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class                        NotificationTest {

    @Autowired
    private NotificationService     notificationService;

    @Autowired
    private UserRepository          userRepository;


    @Test
    public void             sendNotificationTest() {
        User                newUser = new User();
        User exist = this.userRepository.findByUsername("Toto");

        Logger.logInfo("[TEST] Creating user ..");
        if (exist != null) {
            this.userRepository.delete(exist);
        }
        newUser.setUsername("Toto")
                .setPassword("toto")
                .setSalt("toto")
                .setModules(new ArrayList<>())
                .setNotifications(new ArrayList<>());
        this.userRepository.save(newUser);

        Logger.logInfo("[TEST] User %s created !\nSending Notifications ..");
        this.notificationService.send(newUser, "[TEST]");
        newUser = this.userRepository.findByUsername("Toto");
        Logger.logInfo("[TEST] Asserting ..");
        assert (newUser.getNotifications().size() == 1);
        assert (newUser.getNotifications().get(0).getMessage().equals("[TEST]"));
        Logger.logInfo("[TEST] Cleaning User ..");
        this.userRepository.delete(newUser);
    }
}
