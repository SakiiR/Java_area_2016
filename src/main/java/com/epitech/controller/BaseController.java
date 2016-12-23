package com.epitech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * This controller only serve the index.
 */
@Controller
public class BaseController {
    /**
     * Index action
     *
     * @return a view name
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String   index(HttpSession httpSession, ModelMap modelMap) {
        modelMap.addAttribute("username", httpSession.getAttribute("username"));
        return "base/index.html";
    }
}
