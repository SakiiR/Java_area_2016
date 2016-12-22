package com.epitech.controller;

import com.epitech.model.User;
import com.epitech.model.UserModule;
import com.epitech.repository.UserModuleRepository;
import com.epitech.repository.UserRepository;
import com.epitech.service.FacebookService;
import com.epitech.service.IService;
import com.epitech.utils.BodyParser;
import com.epitech.utils.Logger;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.epitech.model.Module;
import com.epitech.repository.ModuleRepository;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpSession;
import java.util.Base64;
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

        Logger.logInfo("username : %s", httpSession.getAttribute("username"));
        if (httpSession.getAttribute("username") == null) {
            return "redirect:/login";
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
            return "redirect:/login";
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
                    return "redirect:/login";
                }
                /** Create new module  */
                UserModule userModule = new UserModule();
                userModule.setModule(moduleRepository.findByName(moduleName))
                        .setToken(service.login(bodyUsername, bodyPassword));
                userModule.setUser(user);
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

    @RequestMapping(value = "/module/oauth", method = RequestMethod.GET, params = {"access_token", "state"})
    public String                   oauth(HttpSession httpSession, ModelMap modelMap, @RequestParam(value = "access_token") String access_token, @RequestParam(value = "state") String state) {
        BodyParser                  bodyParser = new BodyParser(new String(Base64.getDecoder().decode(state)));
        String                      stateType = bodyParser.get("type");
        String                      stateUsername = bodyParser.get("username");
        String                      connectedUser = (String) httpSession.getAttribute("username");

        if (null == connectedUser) {
            return "redirect:../login";
        }

        if (!(stateType == null || stateUsername == null)) {
            User user = userRepository.findByUsername(connectedUser);
            if (!(user == null)) {
                UserModule userModule = new UserModule();
                Module module = moduleRepository.findByName(stateType);

                if (!(module == null)) {
                    userModule.setModule(module)
                            .setUser(user)
                            .setToken(access_token);
                    userModuleRepository.save(userModule);
                    user.addModule(userModule);
                    userRepository.save(user);
                    modelMap.addAttribute("success", true);
                    modelMap.addAttribute("message", String.format("You have been connected to %s", stateType));
                } else modelMap.addAttribute("message", "State's Type is unknown !");
            } else modelMap.addAttribute("message", "Who are you ?!!");
        } else modelMap.addAttribute("message", "Missing type or username in callback state");

        return "module/oauth.html";
    }
}