package com.epitech;

import com.epitech.utils.BodyParser;
import com.epitech.utils.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sakiir on 27/12/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class                BodyParserTest {

    @Test
    public void             bodyParsingTest() {
        String              body = "foo=bar&name=toto";
        BodyParser          bodyParser = new BodyParser(body);

        assert (bodyParser.get("foo").equals("bar"));
        assert (bodyParser.get("name").equals("toto"));
    }

    @Test
    public void             destinationTest() {
        List<String>        destinations = BodyParser.getDestinations("to=SakiiR&to=Karine&to=Toto");

        Logger.logInfo("[TEST] destinations : %s", destinations);
        assert (destinations.get(0).equals("SakiiR"));
        assert (destinations.get(1).equals("Karine"));
        assert (destinations.get(2).equals("Toto"));
    }
}
