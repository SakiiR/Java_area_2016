package com.epitech;

import com.epitech.utils.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by sakiir on 14/12/16.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class            LoggerTest {

    @Test
    public void         infoLogTest() {
        System.out.println("[TEST] Testing logInfo without format ..");
        Logger.logInfo("INFO !");
        System.out.println("[TEST] Testing logInfo with format ..");
        Logger.logInfo("INFO ! %s", "foo");
    }

    @Test
    public void         successLogTest() {
        System.out.println("[TEST] Testing logSuccess without format ..");
        Logger.logSuccess("SUCCESS !");
        System.out.println("[TEST] Testing logSuccess with format ..");
        Logger.logSuccess("SUCCESS ! %s", "foo");
    }

    @Test
    public void         warningLogTest() {
        System.out.println("[TEST] Testing logWarning without format ..");
        Logger.logWarning("WARNING !");
        System.out.println("[TEST] Testing logWarning with format ..");
        Logger.logWarning("WARNING ! %s", "foo");
    }

    @Test
    public void         errorLogTest() {
        System.out.println("[TEST] Testing logError without format ..");
        Logger.logError("ERROR !");
        System.out.println("[TEST] Testing logError with format ..");
        Logger.logError("ERROR ! %s", "foo");
    }
}
