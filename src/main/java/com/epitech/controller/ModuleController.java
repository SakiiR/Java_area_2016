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
import javassist.bytecode.ByteArray;
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
        if (!(bodyType == null || bodyUsername == null || bodyPassword == null)) {
            bodyType = "com.epitech.service." + Character.toUpperCase(bodyType.charAt(0)) + bodyType.substring(1) + "Service";
            try {
                IService service = (IService)Class.forName(bodyType).getConstructor().newInstance();
                User    user = userRepository.findByUsername(username);
                if (user == null) {
                    return "redirect:/login";
                }
                Module module = moduleRepository.findByName(moduleName);
                if (!(module == null)) {
                    /** if module required oauth callback url */
                    if (!(module.getLoginUrl() == null)) {
                        String toEncode = String.format("type=%s&username=%s", moduleName, user.getUsername());
                        String encoded = Base64.getEncoder().encodeToString(toEncode.getBytes());
                        return "redirect:" + module.getLoginUrl() + "&state=" + encoded;
                    }
                    /** simple API authenticate  */
                    UserModule userModule = new UserModule();
                    userModule.setModule(module).setUser(user);
                    String token = service.login(bodyUsername, bodyPassword);

                    /** is login success  */
                    if (!(token == null)) {
                        Boolean foundModule = false;
                        for (UserModule m : user.getModules()) {
                            if (m.getModule().getName().equals(moduleName)) {
                                foundModule = true;
                                break;
                            }
                        }
                        /** if the module is already existing in the user module array  */
                        if (!foundModule) {
                            userModule.setToken(token);
                            user.addModule(userModule);
                            userModuleRepository.save(userModule);
                            userRepository.save(user);
                            modelMap.addAttribute("message", String.format("%s added to your modules !", moduleName));
                            modelMap.addAttribute("success", true);
                        } else modelMap.addAttribute("message", "You are already connected to this module");
                    } else modelMap.addAttribute("message", "Can't login with theses credz !");
                } else modelMap.addAttribute("message", String.format("Cannot find module : %s", moduleName));
            } catch (Exception e) {
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