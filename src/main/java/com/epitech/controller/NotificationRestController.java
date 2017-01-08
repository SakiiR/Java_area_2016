package com.epitech.controller;

import com.epitech.model.User;
import com.epitech.repository.UserRepository;
import com.epitech.utils.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * This controller is used to retrieve
 * information about notification.
 */
@RestController
public class                                        NotificationRestController {

    @Autowired
    private NotificationService                     notificationService;

    @Autowired
    private UserRepository                          userRepository;

    /**
     * This action is used to retrieve
     * notification count.
     *
     * @param httpSession the session parameters object?
     * @return a json object.
     */
    @RequestMapping(value = "/notification/count", method = RequestMethod.GET, produces = "application/json")
    public NotificationService.NotificationResponse countNotifications(HttpSession httpSession) {
        User                                        user = this.userRepository.findByUsername((String) httpSession.getAttribute("username"));
        NotificationService.NotificationResponse    notificationResponse = new NotificationService.NotificationResponse();

        if (null == user) {
            notificationResponse.message = "You should be connected to access this route ..";
            notificationResponse.success = false;
            return notificationResponse;
        }

        return this.notificationService.getNotificationsCount(user);
    }
}
