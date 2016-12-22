package com.epitech.fixture;

import com.epitech.model.Module;
import com.epitech.repository.ModuleRepository;

public class                ModuleFixture {
    ModuleRepository        moduleRepository;

    public                  ModuleFixture(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public void             init() {
        this.clear();
        this.moduleRepository.save(new Module("facebook", "/img/modules/facebook.png", "Description for facebook", "https://www.facebook.com/v2.8/dialog/oauth?client_id=760127370809959&redirect_uri=http://localhost:8080/module/oauth&response_type=token"));
        this.moduleRepository.save(new Module("twitter", "/img/modules/twitter.png", "Description for twitter", ""));
        this.moduleRepository.save(new Module("epitech","/img/modules/intra-epitech.png","Description for intra epitech", ""));
        this.moduleRepository.save(new Module("office","/img/modules/office.png","Description for office 365", ""));
        this.moduleRepository.save(new Module("gmail","/img/modules/gmail.png","Description for gmail/inbox", ""));
        this.moduleRepository.save(new Module("yammer","/img/modules/yammer.png","Description for yammer", ""));
        this.moduleRepository.save(new Module("onedrive","/img/modules/onedrive.png","Description for onedrive", ""));
        this.moduleRepository.save(new Module("googledrive", "/img/modules/googledrive.png", "Description for googledrive", ""));
        this.moduleRepository.save(new Module("slack", "/img/modules/slack.png", "Description for slack", ""));
        this.moduleRepository.save(new Module("github", "/img/modules/github.png", "Description for github", ""));
        this.moduleRepository.save(new Module("youtube", "/img/modules/youtube.png", "Description for youtube", ""));
    }

    public void             clear() {
        this.moduleRepository.deleteAll();
    }
}
