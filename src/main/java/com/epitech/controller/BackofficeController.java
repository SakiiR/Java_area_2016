package com.epitech.controller;

import com.epitech.model.BackofficeUser;
import com.epitech.model.Module;
import com.epitech.repository.BackofficeUserRepository;
import com.epitech.repository.ModuleRepository;
import com.epitech.utils.BodyParser;
import com.epitech.utils.PasswordManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * This controller is used to administrate
 * Area database and fixtures.
 */
@Controller
public class                            BackofficeController {

    @Autowired
    private ModuleRepository            moduleRepository;

    @Autowired
    private BackofficeUserRepository    backofficeUserRepository;

    /**
     * This route simply return
     * the login form.
     *
     * @param httpSession the session parameters object.
     * @param modelMap the view parameters object.
     * @return a view name.
     */
    @RequestMapping(value = "/backoffice/login", method = RequestMethod.GET)
    public String                       backofficeLogin(HttpSession httpSession, ModelMap modelMap) {
        if (null != httpSession.getAttribute("backoffice_username")) {
            return "redirect:/backoffice/modules";
        }

        modelMap.addAttribute("backoffice_username", httpSession.getAttribute("backoffice_username"));
        return "backoffice/login.html";
    }

    /**
     * This route is used to login
     * with a user and a password.
     *
     * @param backofficeUser The user who try to connect.
     * @param modelMap The view parameters object.
     * @param httpSession The session parameters object.
     * @return a view name.
     */
    @RequestMapping(value = "/backoffice/login", method = RequestMethod.POST)
    public String                       backofficeLogin(@ModelAttribute("backoffice_user") BackofficeUser backofficeUser, ModelMap modelMap, HttpSession httpSession) {
        if (null != httpSession.getAttribute("backoffice_username")) {
            return "redirect:/backoffice/modules";
        }
        String username = backofficeUser.getUsername();
        String password = backofficeUser.getPassword();

        if (username != null && password != null) {
            BackofficeUser exist = this.backofficeUserRepository.findByUsername(username);
            if (backofficeUser != null) {
                PasswordManager passwordManager = new PasswordManager();
                if (passwordManager.check(password, exist.getSalt(), exist.getPassword())) {
                    httpSession.setAttribute("backoffice_username", username);
                    modelMap.addAttribute("success", true);
                    modelMap.addAttribute("message", "Success fully logged in ! Redirecting ..");
                    modelMap.addAttribute("redirectUrl", "/backoffice/modules");
                } else modelMap.addAttribute("message", "Bad username or bad password");
            } else modelMap.addAttribute("message", "Bad username or bad password");
        } else modelMap.addAttribute("message", "Missing Fields");


        modelMap.addAttribute("backoffice_username", httpSession.getAttribute("backoffice_username"));
        return "backoffice/login.html";
    }

    @RequestMapping(value = "/backoffice/modules", method = RequestMethod.GET)
    public String                       backofficeModules(ModelMap modelMap, HttpSession httpSession) {
        if (null == httpSession.getAttribute("backoffice_username")) {
            return "redirect:/backoffice/login";
        }
        modelMap.addAttribute("backoffice_username", httpSession.getAttribute("backoffice_username"));
        modelMap.addAttribute("modules", moduleRepository.findAll());
        return "backoffice/modules.html";
    }

    @RequestMapping(value = "/backoffice/module/{id}/remove", method = RequestMethod.POST)
    public String                       backofficeRemoveModule(HttpSession httpSession, @PathVariable(value="id") String id) {
        if (null == httpSession.getAttribute("backoffice_username")) {
            return "redirect:/backoffice/login";
        }
        moduleRepository.delete(id);
        return "redirect:/backoffice/modules";
    }

    @RequestMapping(value = "/backoffice/module/new", method = RequestMethod.POST)
    public String                       backofficeAddModule(HttpSession httpSession, @RequestBody String body) {
        BodyParser                      bodyParser = new BodyParser(body);
        String                          name = bodyParser.get("name");
        String                          description = bodyParser.get("description");
        String                          imageUrl = bodyParser.get("image_url");
        String                          callback = bodyParser.get("callback");

        if (null == httpSession.getAttribute("backoffice_username")) {
            return "redirect:/backoffice/login";
        }

        if (name != null &&
                imageUrl != null &&
                description != null &&
                callback != null &&
                name.length() != 0 &&
                description.length() != 0 &&
                imageUrl.length() != 0) {
            Module module = new Module();
            try {
                module.setName(java.net.URLDecoder.decode(name, "UTF-8"))
                        .setDescription(java.net.URLDecoder.decode(description, "UTF-8"))
                        .setImageUrl(java.net.URLDecoder.decode(imageUrl, "UTF-8"))
                        .setLoginUrl(java.net.URLDecoder.decode(callback, "UTF-8"));
                moduleRepository.save(module);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/backoffice/modules";
    }

}
