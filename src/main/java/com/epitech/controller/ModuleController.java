package com.epitech.controller;

import com.epitech.service.FacebookService;
import com.epitech.utils.BodyParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.epitech.model.Module;
import com.epitech.repository.ModuleRepository;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.lang.reflect.Constructor;

/**
 * Created by anakin on 20/12/16.
 */

@Controller
public class ModuleController {

    @Autowired
    private ModuleRepository    moduleRepository;

    @RequestMapping(value = "/module/list", method = RequestMethod.GET)
    public String               list(HttpSession httpSession, ModelMap modelMap) {
        List<Module>            availableModules;

        if (httpSession.getAttribute("username") == null) {
            return "redirect:login";
        }
        availableModules = moduleRepository.findAll();
        modelMap.addAttribute("modules", availableModules);
        return "module/list.html";
    }

    @RequestMapping(value = "/module/manage", method = RequestMethod.POST)
    public String               manage(HttpSession httpSession, ModelMap modelMap, @RequestBody String body) {
        String                  username = (String) httpSession.getAttribute("username");
        BodyParser              bodyParser = new BodyParser(body);

        if (username == null) {
            return "redirect:login";
        }
        String bodyType = bodyParser.get("type");
        String bodyUsername = bodyParser.get("username");
        String bodyPassword = bodyParser.get("password");
        System.out.println("type = " + bodyType + " username = " + bodyUsername + " password = " + bodyPassword);
        if (!(bodyType == null || bodyUsername == null || bodyPassword == null))
        {
            bodyType = "com.epitech.service." + Character.toUpperCase(bodyType.charAt(0)) + bodyType.substring(1) + "Service";
            System.out.println("new string class = " + bodyType);
            try {
                Object instanceOfMyClass = Class.forName(bodyType).getConstructor().newInstance();
                modelMap.addAttribute("message", "Success");
            } catch (Exception e) {
                e.printStackTrace();
                modelMap.addAttribute("message", "Invalid Type");
            }
        } else modelMap.addAttribute("message", "Missing fields");
        modelMap.addAttribute("redirectUrl", "/module/list");
        return "module/manage.html";
    }
}
