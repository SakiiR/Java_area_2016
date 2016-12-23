package com.epitech.fixture;

import com.epitech.model.Module;
import com.epitech.repository.ModuleRepository;

/**
 * This class is used to add default data
 * to the database when the server is starting.
 */
public class                ModuleFixture {
    ModuleRepository        moduleRepository;

    /**
     * Constructor
     *
     * @param moduleRepository the ModuleRepository
     *                         to interact with modules.
     */
    public                  ModuleFixture(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    /**
     * This method add modules.
     */
    public void             init() {
        this.clear();
        this.moduleRepository.save(new Module("facebook", "/img/modules/facebook.png", "Description for facebook", "https://www.facebook.com/v2.8/dialog/oauth?client_id=760127370809959&redirect_uri=http://localhost:8080/module/oauth&response_type=token"));
        this.moduleRepository.save(new Module("twitter", "/img/modules/twitter.png", "Description for twitter", ""));
        this.moduleRepository.save(new Module("epitech","/img/modules/intra-epitech.png","Description for intra epitech", ""));
        this.moduleRepository.save(new Module("office","/img/modules/office.png","Description for office 365", ""));
        this.moduleRepository.save(new Module("gmail","/img/modules/gmail.png","Description for gmail/inbox", "https://accounts.google.com/o/oauth2/v2/auth?client_id=801969793224-jh02oeqpfvi2ocjrebbp21dks178qav5.apps.googleusercontent.com&redirect_uri=http://localhost:8080/module/oauth&response_type=token&scope=email"));
        this.moduleRepository.save(new Module("yammer","/img/modules/yammer.png","Description for yammer", "https://www.yammer.com/oauth2/authorize?client_id=zPX7lhZerhGVn9beAAfGg&redirect_uri=http://localhost:8080/module/oauth&response_type=token"));
        this.moduleRepository.save(new Module("onedrive","/img/modules/onedrive.png","Description for onedrive", ""));
        this.moduleRepository.save(new Module("googledrive", "/img/modules/googledrive.png", "Description for googledrive", "https://accounts.google.com/o/oauth2/v2/auth?client_id=801969793224-jh02oeqpfvi2ocjrebbp21dks178qav5.apps.googleusercontent.com&redirect_uri=http://localhost:8080/module/oauth&response_type=token&scope=https://www.googleapis.com/auth/drive"));
        this.moduleRepository.save(new Module("slack", "/img/modules/slack.png", "Description for slack", ""));
        this.moduleRepository.save(new Module("github", "/img/modules/github.png", "Description for github", "https://github.com/login/oauth/authorize?client_id=d6b6716e70d77f94200e&redirect_uri=http://localhost:8080/module/oauth&response_type=token&scope=repo"));
        this.moduleRepository.save(new Module("youtube", "/img/modules/youtube.png", "Description for youtube", ""));
        this.moduleRepository.save(new Module("pastebin", "/img/modules/pastebin.png", "Description for pastebin", ""));
    }

    /**
     * This method clear the modules.
     */
    public void             clear() {
        this.moduleRepository.deleteAll();
    }
}
