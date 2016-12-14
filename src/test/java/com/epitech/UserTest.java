package com.epitech;

import com.epitech.model.User;
import com.epitech.repository.UserRepository;
import com.epitech.utils.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by sakiir on 14/12/16.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class                    UserTest {

    @Autowired
    private UserRepository      userRepository;
    @Test
    public void                 createUserTest() {
        User                    user = new User();

        user.setUsername("Test")
                .setPassword("foo")
                .setSalt("bar");
        userRepository.save(user);
        User seek = userRepository.findByUsername("Test");
        Logger.logInfo("[TEST] %s != null : %s", seek, !seek.equals(null));
        assert(!seek.equals(null));

        Logger.logInfo("[TEST] %s == Test : %s", seek.getUsername(), seek.getUsername().equals("Test"));
        assert(seek.getUsername().equals("Test"));

        Logger.logInfo("[TEST] %s == foo : %s", seek.getPassword(), seek.getPassword().equals("foo"));
        assert(seek.getPassword().equals("foo"));

        Logger.logInfo("[TEST] %s == bar : %s", seek.getSalt(), seek.getSalt().equals("bar"));
        assert(seek.getSalt().equals("bar"));
    }
}
