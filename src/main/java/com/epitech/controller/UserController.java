package com.epitech.controller;

import com.epitech.model.Notification;
import com.epitech.model.User;
import com.epitech.repository.NotificationRepository;
import com.epitech.repository.UserRepository;
import com.epitech.utils.Logger;
import com.epitech.utils.PasswordContainer;
import com.epitech.utils.PasswordManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * This controller is used to manage
 * user of the database.
 */
@Controller
public class                        UserController {

    @Autowired
    private NotificationRepository  notificationRepository;

    @Autowired
    private UserRepository          userRepository;

    /**
     * This route simply return the login form.
     *
     * @return a view name
     */
    @RequestMapping(value  = "/login", method = RequestMethod.GET)
    public String                   login(HttpSession httpSession, ModelMap modelMap) {

        if (null != httpSession.getAttribute("username")) {
            return "redirect:/module/list";
        }

        modelMap.addAttribute("username", httpSession.getAttribute("username"));
        return "user/login.html";
    }

    /**
     * This route is used when a non-connected
     * user try to connect to the server.
     *
     * @param user The POSTed used
     * @param modelMap The view parameters Object.
     * @param httpSession The session parameters Object
     * @return a view name.
     */
    @RequestMapping(value  = "/login", method = RequestMethod.POST)
    public String                   login(@ModelAttribute("user") User user, ModelMap modelMap, HttpSession httpSession) {
        if (null != httpSession.getAttribute("username")) {
            return "redirect:/module/list";
        }
        if (!(user.getUsername() == null                ||
                user.getUsername().length() == 0        ||
                user.getPassword() == null              ||
                user.getPassword().length() == 0)) {
            User exist = userRepository.findByUsername(user.getUsername());
            if (!(exist == null)) {
                PasswordManager passwordManager = new PasswordManager();
                if (passwordManager.check(user.getPassword(), exist.getSalt(), exist.getPassword())) {
                    httpSession.setAttribute("username", user.getUsername());
                    modelMap.addAttribute("success", true);
                    modelMap.addAttribute("redirectUrl", "/module/list");
                    modelMap.addAttribute("message", String.format("User %s successfully logged !", user.getUsername()));
                } else modelMap.addAttribute("message", "Bad username / password !");
            } else modelMap.addAttribute("message", "Bad username / password !");
        } else modelMap.addAttribute("message", "Missing field(s) !");
        modelMap.addAttribute("username", httpSession.getAttribute("username"));
        return "user/login.html";
    }

    /**
     * This route simply return the Register
     * Form.
     *
     * @return a view name.
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String                   register(HttpSession httpSession, ModelMap modelMap) {
        if (null != httpSession.getAttribute("username")) {
            return "redirect:/module/list";
        }
        modelMap.addAttribute("username", httpSession.getAttribute("username"));
        return "user/register.html";
    }

    /**
     * This route is used to add a new user to
     * the database.
     *
     * @param user The POSTed user.
     * @param modelMap The view parameter Object.
     * @return a view name.
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String                   register(@ModelAttribute("user") User user, ModelMap modelMap, HttpSession httpSession) {
        if (null != httpSession.getAttribute("username")) {
            return "redirect:/module/list";
        }
        if (!(user.getUsername() == null            ||
                user.getUsername().length() == 0    ||
                user.getPassword() == null          ||
                user.getPassword().length() == 0)) {
            User exist = userRepository.findByUsername(user.getUsername());
            if (exist == null) {
                PasswordManager passwordManager = new PasswordManager();
                PasswordContainer passwordContainer = passwordManager.encode(user.getPassword());
                user.setPassword(passwordContainer.getPassword())
                        .setSalt(passwordContainer.getSalt())
                        .setNotifications(new ArrayList<>())
                        .setAreas(new ArrayList<>())
                        .setModules(new ArrayList<>());
                userRepository.save(user);
                Logger.logSuccess("User %s Created !", user.getUsername());
                modelMap.addAttribute("success", true);
                modelMap.addAttribute("message", String.format("User %s successfully created", user.getUsername()));
                modelMap.addAttribute("redirectUrl", "/login");
            } else modelMap.addAttribute("message", String.format("User %s already exists", user.getUsername()));
        } else modelMap.addAttribute("message", "Missing field !");
        modelMap.addAttribute("username", httpSession.getAttribute("username"));
        return "user/register.html";
    }

    /**
     * This route is used to logout a user.
     *
     * @param httpSession the session parameter object.
     * @return a view name.
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String                   logout(HttpSession httpSession, ModelMap modelMap) {
        if (null == httpSession.getAttribute("username")) {
            return "redirect:/login";
        }
        httpSession.removeAttribute("username");
        return "user/logout.html";
    }

    /**
     * This route is used to display all the user notifications.
     *
     * @param httpSession the session parameters object.
     * @param modelMap the view parameters object.
     * @return a view name.
     */
    @RequestMapping(value = "/notifications", method = RequestMethod.GET)
    public String                   notifications(HttpSession httpSession, ModelMap modelMap) {
        String                      username = (String) httpSession.getAttribute("username");
        User                        user = userRepository.findByUsername(username);

        if (null == user) {
            return "redirect:/login";
        }

        List<Notification>          notifications = user.getNotifications();
        List<Notification>          toRemove = new ArrayList<>();

        for (Notification notification : notifications) {
            if (notification.isReaded()) {
                toRemove.add(notification);
            }
        }

        for (Notification notification : notifications) {
            notification.setReaded(true);
            notificationRepository.save(notification);
        }

        user.getNotifications().removeAll(toRemove);
        this.userRepository.save(user);
        this.notificationRepository.delete(toRemove);

        modelMap.addAttribute("notifications", user.getNotifications());
        modelMap.addAttribute("username", user.getUsername());
        return "user/notifications.html";
    }
}
