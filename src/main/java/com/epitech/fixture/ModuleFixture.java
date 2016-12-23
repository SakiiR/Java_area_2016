package com.epitech.fixture;

import com.epitech.model.Module;
import com.epitech.repository.ModuleRepository;

/**
 * This class is used to add default data
 * to the database when the server is starting.
 */
public class                    ModuleFixture {
    private ModuleRepository    moduleRepository;

    /**
     * Constructor
     *
     * @param moduleRepository the ModuleRepository
     *                         to interact with modules.
     */
    public                      ModuleFixture(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public void                 add(Module module) {

        if (null == this.moduleRepository.findByName(module.getName())) {
            this.moduleRepository.save(module);
        }
    }

    /**
     * This method add modules.
     */
    public void                 init() {
        this.add(new Module("facebook", "/img/modules/facebook.png", "Description for facebook", "https://www.facebook.com/v2.8/dialog/oauth?client_id=760127370809959&redirect_uri=http://localhost:8080/module/oauth&response_type=token"));
        this.add(new Module("twitter", "/img/modules/twitter.png", "Description for twitter", ""));
        this.add(new Module("epitech","/img/modules/intra-epitech.png","Description for intra epitech", ""));
        this.add(new Module("office","/img/modules/office.png","Description for office 365", ""));
        this.add(new Module("gmail","/img/modules/gmail.png","Description for gmail/inbox", ""));
        this.add(new Module("yammer","/img/modules/yammer.png","Description for yammer", ""));
        this.add(new Module("onedrive","/img/modules/onedrive.png","Description for onedrive", ""));
        this.add(new Module("googledrive", "/img/modules/googledrive.png", "Description for googledrive", ""));
        this.add(new Module("slack", "/img/modules/slack.png", "Description for slack", ""));
        this.add(new Module("github", "/img/modules/github.png", "Description for github", ""));
        this.add(new Module("youtube", "/img/modules/youtube.png", "Description for youtube", ""));
        this.add(new Module("pastebin", "/img/modules/pastebin.png", "Description for pastebin", ""));
    }

    /**
     * This method clear the modules.
     */
    public void                 clear() {
        this.moduleRepository.deleteAll();
    }
}
