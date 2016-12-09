package com.epitech.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sakiir on 09/12/16.
 */
@RestController
public class BaseController {

    @RequestMapping("/")
    public String   index() {
        return "you are on /";
    }
}
