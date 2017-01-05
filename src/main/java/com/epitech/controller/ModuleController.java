package com.epitech.controller;

import com.epitech.model.Area;
import com.epitech.model.User;
import com.epitech.model.UserModule;
import com.epitech.repository.UserModuleRepository;
import com.epitech.repository.UserRepository;
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
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * This controller is relative to modules
 * and it is used to interact with them.
 */
@Controller
public class                        ModuleController {

    @Autowired
    private ModuleRepository        moduleRepository;

    @Autowired
    private UserRepository          userRepository;

    @Autowired
    private UserModuleRepository    userModuleRepository;


    /**
     * This route return the list of all module
     * available and user relative ones.
     *
     * @param httpSession The session parameter object
     * @param modelMap The view parameter object
     * @return a view name
     */
    @RequestMapping(value = "/module/list", method = RequestMethod.GET)
    public String                   list(HttpSession httpSession, ModelMap modelMap) {
        List<Module>                allModules;
        ArrayList<Module>           userModules = new ArrayList<>();
        ArrayList<Module>           availableModules = new ArrayList<>();

        if (httpSession.getAttribute("username") == null) {
            return "redirect:/login";
        }
        User user = userRepository.findByUsername((String)httpSession.getAttribute("username"));
        if (user == null) {
            return "redirect:/login";
        }
        allModules = moduleRepository.findAll();
        for (UserModule m : user.getModules()) {
            userModules.add(m.getModule());
        }
       boolean find;
        for (Module m : allModules) {
            find = false;
            for (Module um : userModules) {
                if (um.getName().equals(m.getName())) {
                    find = true;
                }
            }
            if (!find) {
                availableModules.add(m);
            }
        }
        modelMap.addAttribute("modules", availableModules);
        modelMap.addAttribute("usermodules", userModules);
        modelMap.addAttribute("username", httpSession.getAttribute("username"));
        return "module/list.html";
    }

    /**
     * This route is used to connect with a module.
     *
     * @param httpSession The session parameter object
     * @param modelMap The view parameter object
     * @param body The body request parameters
     * @return a view name
     */
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
        if (!(bodyType == null)) {
            bodyType = "com.epitech.service." + Character.toUpperCase(bodyType.charAt(0)) + bodyType.substring(1) + "Service";

            IService service = null;
            try {
                service = (IService)Class.forName(bodyType).getConstructor().newInstance();
            } catch (Exception e) {
                Logger.logWarning("Failed to instanciate service");
                e.printStackTrace();
            }
            if (!(service == null)) {
                User    user = userRepository.findByUsername(username);
                if (user == null) {
                    return "redirect:/login";
                }
                Module module = moduleRepository.findByName(moduleName);
                if (!(module == null)) {
                    /** if module required oauth callback url */
                    if (!(module.getLoginUrl().length() == 0)) {
                        String toEncode = String.format("type=%s&username=%s", moduleName, user.getUsername());
                        String encoded = Base64.getEncoder().encodeToString(toEncode.getBytes());
                        return "redirect:" + module.getLoginUrl() + "&state=" + encoded;
                    }
                    if (!(bodyUsername == null || bodyPassword == null)) {
                        /** simple API authenticate  */

                        String token = service.login(bodyUsername, bodyPassword);
                        if (!(token == null)) {
                            Boolean foundModule = false;
                            for (UserModule m : user.getModules()) {
                                if (m.getModule() != null && m.getModule()
                                        .getName()
                                        .equals(moduleName)) {
                                    foundModule = true;
                                    break;
                                }
                            }
                            if (!foundModule) {
                                UserModule userModule = new UserModule();
                                userModule.setModule(module)
                                        .setUser(user)
                                        .setToken(token);
                                user.addModule(userModule);
                                userModuleRepository.save(userModule);
                                userRepository.save(user);
                                modelMap.addAttribute("message", String.format("%s added to your modules !", moduleName));
                                modelMap.addAttribute("success", true);
                            } else modelMap.addAttribute("message", "You are already connected to this module");
                        } else modelMap.addAttribute("message", String.format("Can't login to %s with theses credz !", moduleName));
                    } else modelMap.addAttribute("message", "Missing fields !");
                } else modelMap.addAttribute("message", String.format("Cannot find module : %s", moduleName));
            } else modelMap.addAttribute("message", "Invalid Type");
        }
        modelMap.addAttribute("username", httpSession.getAttribute("username"));
        modelMap.addAttribute("redirectUrl", "/module/list");
        return "module/manage.html";
    }

    /**
     * This route simply return a view with a
     * jquery/ajax call to a route from OAuthController
     *
     * @see OAuthRestController
     * @param httpSession The session parameter object
     * @return a view name.
     */
    @RequestMapping(value = "/module/oauth", method = RequestMethod.GET)
    public String                   oauth(HttpSession httpSession, ModelMap modelMap) {
        if (null == httpSession.getAttribute("username")) {
            return "redirect:/login";
        }

        modelMap.addAttribute("username", httpSession.getAttribute("username"));
        return "module/oauth.html";
    }

    /**
     * This route is used to remove UserModule from
     * the user module array.
     *
     * @param httpSession The session parameters object.
     * @param modelMap The view parameters object.
     * @param id the id given is the url.
     * @return a view name.
     */
    @RequestMapping(value = "/module/{id}/disconnect", method = RequestMethod.POST)
    public String                   moduleDisconnect(HttpSession httpSession, ModelMap modelMap, @PathVariable(value="id") String id) {
        String                      username = (String) httpSession.getAttribute("username");
        Module                      module;
        User                        user;
        ArrayList<Area>             areaToRemove = new ArrayList<>();

        if (null == username) {
            return "redirect:/login";
        }

        module = moduleRepository.findById(id);
        if (!(null == module)) {
            user = userRepository.findByUsername(username);
            if (!(user == null)) {
                boolean found = false;
                for(UserModule m : user.getModules()) {
                    if (m.getModule() != null && m.getModule().getName().equals(module.getName())) {
                        found = true;
                        userModuleRepository.delete(m.getId());
                        user.removeModule(m);
                        userRepository.save(user);
                        break;
                    }
                }
                if (found) {
                    for (Area a : user.getAreas()) {
                        if (a.getActionModuleName().equals(module.getName())
                                || a.getReactionModuleName().equals(module.getName())) {
                            areaToRemove.add(a);
                        }
                    }
                    if (areaToRemove.size() > 0) {
                        for (Area a : areaToRemove) {
                            user.getAreas().remove(a);
                        }
                        this.userRepository.save(user);
                    }
                    modelMap.addAttribute("message", String.format("Successfully disconnected from %s !", module.getName().toUpperCase()));
                } else modelMap.addAttribute("message", "You are not connected to this module !");
            } else modelMap.addAttribute("message", "Who are you ??");
        } else modelMap.addAttribute("message", String.format("Unknown module id %s", id));

        modelMap.addAttribute("redirectUrl", "/module/list");
        modelMap.addAttribute("username", httpSession.getAttribute("username"));
        return "module/disconnect.html";
    }
}
