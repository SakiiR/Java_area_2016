package com.epitech.controller;

import com.epitech.model.Module;
import com.epitech.model.User;
import com.epitech.model.UserModule;
import com.epitech.repository.ModuleRepository;
import com.epitech.repository.UserModuleRepository;
import com.epitech.repository.UserRepository;
import com.epitech.service.IService;
import com.epitech.utils.AreaReflector;
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
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * This controller is used to connect to an API but
 * the OAuth Type.
 *
 * @see ModuleController
 */
@RestController
public class OAuthRestController {
    @Autowired
    private ModuleRepository        moduleRepository;

    @Autowired
    private UserModuleRepository    userModuleRepository;

    @Autowired
    private UserRepository          userRepository;

    private ResponseObject          registerUserModule(Module module, User user, String token) {
        ResponseObject responseObject = new ResponseObject();

        if (token == null) {
            responseObject.message = "Failed to login ..";
            responseObject.success = false;
            return responseObject;
        }
        UserModule userModule = new UserModule();
        userModule.setToken(token)
                .setUser(user)
                .setModule(module);
        user.addModule(userModule);
        boolean found = false;
        for (UserModule um : user.getModules()) {
            if (um.getModule().getName().equals(module.getName())) {
                found = true;
            }
        }
        if (!found) {
            this.userModuleRepository.save(userModule);
            this.userRepository.save(user);
            responseObject.message = String.format("Successfully added %s to your modules !", module.getName());
            responseObject.success = true;
            return responseObject;
        }
        responseObject.message = String.format("You are already connected to %s", module.getName());
        responseObject.success = false;
        return responseObject;
    }

    /**
     * This route connect a user with an OAuth API.
     *
     * @param httpSession the session parameter object
     * @param body the POST parameters Object
     * @return a JSON String
     */
    @RequestMapping(value = "/module/oauth", method = RequestMethod.POST, produces = "application/json")
    public String                   oauth(HttpSession httpSession, @RequestBody String body) throws Exception {
        BodyParser                  bodyParser = new BodyParser(body);
        ResponseObject              responseObject = new ResponseObject();

        responseObject.success = false;
        String code = UriUtils.decode(bodyParser.get("code"), "utf-8");
        String state = UriUtils.decode(bodyParser.get("state"), "utf-8");
        if (!(code == null || state == null)) {
            BodyParser                  stateBodyParser = new BodyParser(new String(Base64.getDecoder().decode(state)));
            String                      stateType = stateBodyParser.get("type");
            String                      stateUsername = stateBodyParser.get("username");
            String                      connectedUser = (String) httpSession.getAttribute("username");

            if (null == connectedUser) {
                return "redirect:../login";
            }

            if (!(stateType == null || stateUsername == null)) {
                User user = userRepository.findByUsername(connectedUser);
                if (!(user == null)) {
                    Module module = this.moduleRepository.findByName(stateType);
                    if (module != null) {
                        IService service = AreaReflector.instanciateService(module.getName());
                        if (service != null) {
                            responseObject = this.registerUserModule(module, user, service.login(code, module));
                        } else responseObject.message = "Failed to instanciate service";
                    } else responseObject.message = "Bad state module name";
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
