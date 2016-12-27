package com.epitech;

import com.epitech.service.PastebinService;
import com.epitech.utils.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class            PastebinServiceTest {
    @Test
    public void         loginTest() {
        String username = "sakiir";
        String password = "123";
        String result;

        PastebinService pastebinService = new PastebinService();
        result = pastebinService.login(username, password);
        Logger.logInfo("[TEST] Pastebin Result : %s", result);
        assert (result == null);
    }
}
