package com.epitech.controller;

import com.epitech.model.Module;
import com.epitech.model.User;
import com.epitech.model.UserModule;
import com.epitech.repository.ModuleRepository;
import com.epitech.repository.UserModuleRepository;
import com.epitech.repository.UserRepository;
import com.epitech.utils.BodyParser;
import com.epitech.utils.Logger;
import com.epitech.utils.ResponseObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Base64;

/**
 * Created by sakiir on 22/12/16.
 */
@RestController
public class                        OAuthController {
    @Autowired
    private ModuleRepository        moduleRepository;

    @Autowired
    private UserModuleRepository    userModuleRepository;

    @Autowired
    private UserRepository          userRepository;

    @RequestMapping(value = "/module/oauth", method = RequestMethod.POST, produces = "application/json")
    public String                   oauth(HttpSession httpSession, @RequestBody String body) {
        BodyParser                  bodyParser = new BodyParser(body);
        ResponseObject              responseObject = new ResponseObject();

        responseObject.success = false;
        String access_token = bodyParser.get("access_token");
        String state = bodyParser.get("state");
        if (!(access_token == null || state == null)) {
            BodyParser                  stateBodyParser = new BodyParser(new String(Base64.getDecoder().decode(state.replace("%3D", "="))));
            String                      stateType = stateBodyParser.get("type");
            String                      stateUsername = stateBodyParser.get("username");
            String                      connectedUser = (String) httpSession.getAttribute("username");

            if (null == connectedUser) {
                return "redirect:../login";
            }

            if (!(stateType == null || stateUsername == null)) {
                User user = userRepository.findByUsername(connectedUser);
                if (!(user == null)) {

                    /**  Check if user already have this module */
                    boolean found = false;
                    for (UserModule m : user.getModules()) {
                        if (m.getModule() != null && m.getModule().getName().equals(stateType)) {
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        UserModule userModule = new UserModule();
                        Module module = moduleRepository.findByName(stateType);
                        if (!(module == null)) {
                            userModule.setModule(module)
                                    .setUser(user)
                                    .setToken(access_token);
                            userModuleRepository.save(userModule);
                            user.addModule(userModule);
                            userRepository.save(user);
                            responseObject.success = true;
                            responseObject.message = String.format("You have been connected to %s", stateType);
                        } else responseObject.message = "State's Type is unknown !";
                    } else responseObject.message = "You already have this module !";
                } else responseObject.message = "Who are you ?!!";
            } else responseObject.message = "Missing type or username in callback state";
        } else responseObject.message = "Missing Field";

        ObjectMapper objectMapper = new ObjectMapper();
        String ret = "{\"success\" : false, \"message\" : \"Internal Error !\"}";
        try {
            ret = objectMapper.writeValueAsString(responseObject);
        } catch (JsonProcessingException e) {
            Logger.logWarning("Failed to serialize to json :(");
        }
        return ret;
    }
}
