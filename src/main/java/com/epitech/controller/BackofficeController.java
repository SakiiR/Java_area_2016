package com.epitech.controller;

import com.epitech.model.BackofficeUser;
import com.epitech.model.Module;
import com.epitech.model.User;
import com.epitech.repository.BackofficeUserRepository;
import com.epitech.repository.ModuleRepository;
import com.epitech.repository.NotificationRepository;
import com.epitech.repository.UserRepository;
import com.epitech.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * This controller is used to administrate
 * Area database and fixtures.
 */
@Controller
public class                            BackofficeController {

    @Autowired
    private ModuleRepository            moduleRepository;

    @Autowired
    private UserRepository              userRepository;

    @Autowired
    private BackofficeUserRepository    backofficeUserRepository;

    @Autowired
    private NotificationRepository      notificationRepository;

    @Autowired
    private NotificationService         notificationService;

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

    /**
     * This route return the list of modules.
     *
     * @param modelMap the view parameters object.
     * @param httpSession the session parameters object.
     * @return a view name.
     */
    @RequestMapping(value = "/backoffice/modules", method = RequestMethod.GET)
    public String                       backofficeModules(ModelMap modelMap, HttpSession httpSession) {
        if (null == httpSession.getAttribute("backoffice_username")) {
            return "redirect:/backoffice/login";
        }
        modelMap.addAttribute("backoffice_username", httpSession.getAttribute("backoffice_username"));
        modelMap.addAttribute("modules", moduleRepository.findAll());
        return "backoffice/modules.html";
    }

    /**
     * This route remove a module.
     *
     * @param httpSession The session parameters object.
     * @param id The module id.
     * @return a view name.
     */
    @RequestMapping(value = "/backoffice/module/{id}/remove", method = RequestMethod.POST)
    public String                       backofficeRemoveModule(HttpSession httpSession, @PathVariable(value="id") String id) {
        if (null == httpSession.getAttribute("backoffice_username")) {
            return "redirect:/backoffice/login";
        }
        moduleRepository.delete(id);
        return "redirect:/backoffice/modules";
    }

    /**
     * THis route create a new module.
     *
     * @param httpSession the session parameters object.
     * @param body the request body.
     * @return a view name.
     */
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

    /**
     * This route display the user list.
     *
     * @param httpSession the session parameters object.
     * @param modelMap the view parameters object.
     * @return a view name.
     */
    @RequestMapping(value = "/backoffice/users", method = RequestMethod.GET)
    public String                       backofficeUsers(HttpSession httpSession, ModelMap modelMap) {
        if (null == httpSession.getAttribute("backoffice_username")) {
            return "redirect:/backoffice/login";
        }

        modelMap.addAttribute("users", userRepository.findAll());
        modelMap.addAttribute("backoffice_username", httpSession.getAttribute("backoffice_username"));
        return "backoffice/users.html";
    }

    /**
     * This route remove a user.
     *
     * @param httpSession The session parameters object.
     * @param id The user id.
     * @return a view name.
     */
    @RequestMapping(value = "/backoffice/user/{id}/delete", method = RequestMethod.POST)
    public String                       backofficeRemoveUser(HttpSession httpSession, @PathVariable(value = "id") String id) {
        if (null == httpSession.getAttribute("backoffice_username")) {
            return "redirect:/backoffice/login";
        }

        User                            user = userRepository.findOne(id);
        if (null != user) {
            userRepository.delete(user);
        }

        return "redirect:/backoffice/users";
    }

    /**
     * This route create a new user.
     *
     * @param httpSession the session parameters object.
     * @param body the requaest body.
     * @return a view name.
     */
    @RequestMapping(value = "/backoffice/user/new", method = RequestMethod.POST)
    public String                       backofficeAddUser(HttpSession httpSession, @RequestBody String body) {
        BodyParser                      bodyParser = new BodyParser(body);
        String                          username = bodyParser.get("username");
        String                          password = bodyParser.get("password");

        if (null == httpSession.getAttribute("backoffice_username")) {
            return "redirect:/backoffice/login";
        }

        if (username != null && username.length() != 0 && password != null && password.length() != 0 && userRepository.findByUsername(username) == null) {
            User user = new User();
            PasswordManager passwordManager = new PasswordManager();
            PasswordContainer passwordContainer;
            passwordContainer = passwordManager.encode(password);
            user.setUsername(username)
                    .setPassword(passwordContainer.getPassword())
                    .setSalt(passwordContainer.getSalt())
                    .setModules(new ArrayList<>());
            userRepository.save(user);
        }
        return "redirect:/backoffice/users";
    }

    @RequestMapping(value = "/backoffice/notifications", method = RequestMethod.GET)
    public String                       backofficeNotifications(HttpSession httpSession, ModelMap modelMap) {
        if (null == httpSession.getAttribute("backoffice_username")) {
            return "redirect:/backoffice/login";
        }

        modelMap.addAttribute("users", this.userRepository.findAll());
        modelMap.addAttribute("backoffice_username", httpSession.getAttribute("backoffice_username"));
        return "backoffice/notifications.html";
    }

    @RequestMapping(value = "/backoffice/notification/new", method = RequestMethod.POST)
    public String                       backofficeAddNotification(HttpSession httpSession, ModelMap modelMap, @RequestBody String body) {
        BodyParser                      bodyParser = new BodyParser(body);
        List<String>                    to = BodyParser.getDestinations(body);
        String                          message = bodyParser.get("message");

        if (null == httpSession.getAttribute("backoffice_username")) {
            return "redirect:/backoffice/login";
        }

        this.notificationService.send2(to, message);

        modelMap.addAttribute("backoffice_username", httpSession.getAttribute("backoffice_username"));
        return "backoffice/notifications.html";
    }
}
