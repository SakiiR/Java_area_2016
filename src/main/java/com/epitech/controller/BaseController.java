package com.epitech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by sakiir on 09/12/16.
 */
@Controller
public class BaseController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String   index(ModelMap model) {
        return "index.html";
    }
}
