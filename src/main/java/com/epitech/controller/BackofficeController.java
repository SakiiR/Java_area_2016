package com.epitech.controller;

import com.epitech.model.BackofficeUser;
import com.epitech.repository.BackofficeUserRepository;
import com.epitech.repository.ModuleRepository;
import com.epitech.utils.BodyParser;
import com.epitech.utils.PasswordManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

}
