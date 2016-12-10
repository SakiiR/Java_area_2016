package com.epitech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by sakiir on 10/12/16.
 */
@Controller
public class            UserController {
    @RequestMapping("/login")
    public String       login() {
        return "login";
    }

    @RequestMapping("/register")
    public String       register() {
        return "register";
    }
}
