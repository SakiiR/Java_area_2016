package com.epitech.controller;

import com.epitech.model.User;
import com.epitech.model.UserModule;
import com.epitech.repository.UserModuleRepository;
import com.epitech.repository.UserRepository;
import com.epitech.service.FacebookService;
import com.epitech.service.IService;
import com.epitech.utils.BodyParser;
import com.epitech.utils.Logger;
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
public class                        ModuleController {

    @Autowired
    private ModuleRepository        moduleRepository;

    @Autowired
    private UserRepository          userRepository;

    @Autowired
    private UserModuleRepository    userModuleRepository;

    @RequestMapping(value = "/module/list", method = RequestMethod.GET)
    public String                   list(HttpSession httpSession, ModelMap modelMap) {
        List<Module>                availableModules;

        if (httpSession.getAttribute("username") == null) {
            return "redirect:login";
        }
        availableModules = moduleRepository.findAll();
        modelMap.addAttribute("modules", availableModules);
        return "module/list.html";
    }

    @RequestMapping(value = "/module/manage", method = RequestMethod.POST)
    public String                   manage(HttpSession httpSession, ModelMap modelMap, @RequestBody String body) {
        String                      username = (String) httpSession.getAttribute("username");
        BodyParser                  bodyParser = new BodyParser(body);

        if (username == null) {
            return "redirect:login";
        }
        String bodyType = bodyParser.get("type");
        String moduleName = bodyType;
        String bodyUsername = bodyParser.get("username");
        String bodyPassword = bodyParser.get("password");
        Logger.logInfo("type = %s username = %s password = %s", bodyType, bodyUsername, bodyPassword);
        if (!(bodyType == null || bodyUsername == null || bodyPassword == null))
        {
            bodyType = "com.epitech.service." + Character.toUpperCase(bodyType.charAt(0)) + bodyType.substring(1) + "Service";
            try {
                IService service = (IService)Class.forName(bodyType).getConstructor().newInstance();
                User    user = userRepository.findByUsername(username);
                Logger.logInfo("username = %s", username);
                if (user == null) {
                    return "redirect:login";
                }
                /** Create new module  */
                UserModule userModule = new UserModule();
                userModule.setModule(moduleRepository.findByName(moduleName))
                        .setToken(service.login(bodyUsername, bodyPassword));
                userModuleRepository.save(userModule);
                user.addModule(userModule);
                userRepository.save(user);
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