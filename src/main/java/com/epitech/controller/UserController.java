package com.epitech.controller;

import com.epitech.utils.BodyParser;
import com.epitech.utils.Logger;
import com.epitech.utils.PasswordManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;

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
        PasswordManager p = new PasswordManager();
        p.encode("lol");
        return "login.html";
    }

    @RequestMapping(
            value  = "/login",
            method = RequestMethod.POST
    )
    public String       login(@RequestBody String body) {
        // parse body and find user in database :)
        BodyParser      bodyParser = new BodyParser(body);

        String          username = bodyParser.get("username");
        String          password = bodyParser.get("password");

        return "login.html";
    }

    @RequestMapping(
            value = "/register",
            method = RequestMethod.GET
    )
    public String       register() {
        return "register.html";
    }

    @RequestMapping(
            value = "/register",
            method = RequestMethod.POST
    )
    public String       register(@RequestBody String body, ModelMap modelMap) {
        BodyParser      bodyParser = new BodyParser(body);
        String[]        form = new String[3];
        String          error = "";

        form[0] = bodyParser.get("username");
        form[1] = bodyParser.get("password");
        form[2] = bodyParser.get("password2");

        for(String e : form) {
            if (e == null) {
                error = "Missing field !";
            }
        }

        if (!(form[1].equals(form[2]))) {
            error = "Password must be equals !";
        }

        if (error.length() == 0) {
            /**  Register ok ! adding user to db etc ..  */

            error = "You successfully registered ! you will be redirected in 3 seconds !";
            modelMap.addAttribute("success", true);
        }

        modelMap.addAttribute("error", error);

        return "register.html";
    }
}
