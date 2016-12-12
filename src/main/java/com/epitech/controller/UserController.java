package com.epitech.controller;

import com.epitech.utils.BodyParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by sakiir on 10/12/16.
 */
@Controller
public class            UserController {
    @RequestMapping(
            value  = "/login",
            method = RequestMethod.GET
    )
    public String       login() {
        return "login";
    }

    @RequestMapping(
            value  = "/login",
            method = RequestMethod.POST
    )
    public String       login(@RequestBody String body) {
        // parse body and find user in database :)
        BodyParser      bodyParser = new BodyParser(body);

        // bodyParser.get("foo") == "bar"

        return "login";
    }

    @RequestMapping("/register")
    public String       register() {
        return "register";
    }
}
