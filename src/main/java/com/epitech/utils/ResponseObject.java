package com.epitech.utils;

/**
 * This class is used to return a serialized object
 * to the user (for rest controllers).
 *
 * @see org.springframework.web.bind.annotation.RestController
 * @see com.epitech.controller.OAuthRestController
 */
public class        ResponseObject {
    public boolean  success;
    public String   message;
}
