package com.epitech.controller;

import com.epitech.model.Area;
import com.epitech.model.User;
import com.epitech.repository.AreaRepository;
import com.epitech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;


/**
 * This controller is used to manage Areas
 */
@Controller
public class                    AreaController {

    @Autowired
    private UserRepository      userRepository;

    @Autowired
    private AreaRepository      areaRepository;

    /**
     * This action return the list of
     * available and user Areas.
     *
     * @param modelMap the view parameters object.
     * @param httpSession the session parameters object.
     * @return a view name.
     */
    @RequestMapping(value = "/area/list", method = RequestMethod.GET)
    public String               list(ModelMap modelMap, HttpSession httpSession) {
        if (null == httpSession.getAttribute("username")) {
            return "redirect:/login";
        }

        modelMap.addAttribute("areas", this.areaRepository.findAll());
        modelMap.addAttribute("userareas", this.userRepository.findByUsername((String) httpSession.getAttribute("username")).getAreas());
        modelMap.addAttribute("username ", httpSession.getAttribute("username"));
        return "area/list.html";
    }

    /**
     * This action add an area to the user list.
     *
     * @param httpSession the session parameters object.
     * @param id the area id to add.
     * @return a view name.
     */
    @RequestMapping(value = "/area/{id}/add", method = RequestMethod.POST)
    public String               add(HttpSession httpSession, @PathVariable(value = "id") String id) {
        if (null == httpSession.getAttribute("username")) {
            return "redirect:/login";
        }
        User user = this.userRepository.findByUsername((String) httpSession.getAttribute("username"));
        if (user != null) {
            Area area = this.areaRepository.findOne(id);
            if (area != null) {
                boolean found = false;
                for (Area a : user.getAreas()) {
                    if (a.getId().equals(area.getId())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    user.addArea(area);
                    this.userRepository.save(user);
                }
            }
        }
        return "redirect:/area/list";
    }

    /**
     * This action remove an area from the user list.
     *
     * @param httpSession the session parameters object.
     * @param id the area id to remove.
     * @return a view name.
     */
    @RequestMapping(value = "/area/{id}/remove", method = RequestMethod.POST)
    public String               remove(HttpSession httpSession, @PathVariable(value = "id") String id) {
        if (null == httpSession.getAttribute("username")) {
            return "redirect:/login";
        }
        User user = this.userRepository.findByUsername((String) httpSession.getAttribute("username"));
        if (user != null) {
            Area toRemove = null;
            for (Area area : user.getAreas()) {
                if (area.getId().equals(id)) {
                    toRemove = area;
                    break;
                }
            }
            user.getAreas().remove(toRemove);
            this.userRepository.save(user);
        }
        return "redirect:/area/list";
    }
}
