package com.epitech;

import com.epitech.utils.Logger;
import com.epitech.utils.PasswordContainer;
import com.epitech.utils.PasswordManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by sakiir on 15/12/16.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class            PasswordManagerTest {

    @Test
    public void             checkHashTest() {
        PasswordManager     passwordManager = new PasswordManager();
        PasswordContainer   result;
        String              password = "foo";

        Logger.logInfo("[TEST] Hashing %s", password);
        result = passwordManager.encode(password);
        Logger.logInfo("[TEST] Encode result : %s", result.toString());
        Logger.logInfo("[TEST] Checking password");
        Logger.logInfo("[TEST] Result : %s",
                passwordManager.check(
                        password,
                        result.getSalt(),
                        result.getPassword()
                )
        );
        assert (passwordManager.check(password, result.getSalt(), result.getPassword()));
    }
}
