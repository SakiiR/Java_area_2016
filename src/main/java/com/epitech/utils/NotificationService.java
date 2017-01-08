package com.epitech.utils;

import com.epitech.model.Notification;
import com.epitech.model.User;
import com.epitech.repository.NotificationRepository;
import com.epitech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * This class is used to manage
 * Notifications.
 */
@Service
public class                        NotificationService {
    @Autowired
    private NotificationRepository  notificationRepository;

    @Autowired
    private UserRepository          userRepository;


    /**
     * JSON Object.
     */
    public static class             NotificationResponse extends ResponseObject {
        public int                  count;
    }

    /**
     * This method send a notification
     * to one user by User Object.
     *
     * @param user The user object to send notification.
     * @param message The message to send.
     */
    public void                    send(User user, String message) {
        if (user != null && message != null && message.length() != 0) {
            Notification notification = new Notification();
            notification
                    .setMessage(message)
                    .setReaded(false)
                    .setCreated(new Date())
                    .setUser(user);
            this.notificationRepository.save(notification);
            user.addNotification(notification.setMessage(message).setUser(user));
            this.userRepository.save(user);
        }
    }

    /**
     * This method send a notification
     * to one user by username string .
     *
     * @param username The User that receive notification.
     * @param message THe message to send notification.
     */
    public void                     send(String username, String message) {
        User user = this.userRepository.findByUsername(username);
        this.send(user, message);
    }

    /**
     * Tjis method send a notification
     * to a user array.
     *
     * @param users The uner lsit to send notification.
     * @param message the message to send as notification.
     */
    public void                     send(List<User> users, String message) {
        for (User user : users) {
            this.send(user, message);
        }
    }

    /**
     * Fuck this overload fail lol
     *
     * @param users The list of user to send notification.
     * @param message thr message to send.
     */
    public void                     send2(List<String> users, String message) {
        for (String username : users) {
            this.send(username, message);
        }
    }

    /**
     * Send a notification to all users.
     *
     * @param message the message to send.
     */
    public void                     broadcast(String message) {
        List<User>                  users = this.userRepository.findAll();

        for (User user : users) {
            this.send(user, message);
        }
    }

    /**
     * this function get the notification count for a
     * specific user
     * @param user the user
     * @return a NotificationResponse
     */
    public NotificationResponse     getNotificationsCount(User user) {
        ArrayList<Notification>     notifications = new ArrayList<>();
        NotificationResponse        notificationResponse = new NotificationResponse();


        for (Notification notification : user.getNotifications()) {
            if (!notification.isReaded()) {
                notifications.add(notification);
            }
        }

        notificationResponse.message = "Success !";
        notificationResponse.success = true;
        notificationResponse.count = notifications.size();
        return notificationResponse;
    }
}
