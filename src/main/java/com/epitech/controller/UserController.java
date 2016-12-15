package com.epitech.controller;

import com.epitech.model.User;
import com.epitech.repository.UserRepository;
import com.epitech.utils.Logger;
import com.epitech.utils.PasswordContainer;
import com.epitech.utils.PasswordManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class                    UserController {

    @Autowired
    private UserRepository      userRepository;

    @RequestMapping(value  = "/login", method = RequestMethod.GET)
    public String               login() {
        return "login.html";
    }

    @RequestMapping(value  = "/login", method = RequestMethod.POST)
    public String               login(@ModelAttribute("user") User user, ModelMap modelMap) {
        Logger.logInfo("user = %s && password = %s", user.getUsername(), user.getPassword());
        if (!(user.getUsername().equals(null) ||
                user.getUsername().length() == 0 ||
                user.getPassword().equals(null) ||
                user.getPassword().length() == 0)) {
            User exist = userRepository.findByUsername(user.getUsername());
            if (!exist.equals(null)) {
                PasswordManager passwordManager = new PasswordManager();
                if (passwordManager.check(user.getPassword(), exist.getSalt(), exist.getPassword()) == true) {
                    // add session bag (HttpSession)
                    modelMap.addAttribute("success", true);
                    modelMap.addAttribute("message", String.format("User %s successfully logged !"));
                } else modelMap.addAttribute("message", "Bad username / password !");
            } else modelMap.addAttribute("message", "Bad username / password !");
        } else modelMap.addAttribute("message", "Missing field(s) !");
        return "login.html";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String               register() {
        return "register.html";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String               register(@ModelAttribute("user") User user, ModelMap modelMap) {
        if (!(user.getUsername().equals(null) ||
                user.getUsername().length() == 0 ||
                user.getPassword().equals(null) ||
                user.getPassword().length() == 0)) {
            User exist = userRepository.findByUsername(user.getUsername());
            if (exist == null) {
                PasswordManager passwordManager = new PasswordManager();
                PasswordContainer passwordContainer = passwordManager.encode(user.getPassword());
                user.setPassword(passwordContainer.getPassword())
                        .setSalt(passwordContainer.getSalt());
                userRepository.save(user);
                Logger.logSuccess("User %s Created !", user.getUsername());
                modelMap.addAttribute("success", true);
                modelMap.addAttribute("message", String.format("User %s successfully created", user.getUsername()));

            } else modelMap.addAttribute("message", String.format("User %s already exists", user.getUsername()));
        } else modelMap.addAttribute("message", "Missing field !");

        return "register.html";
    }
}
