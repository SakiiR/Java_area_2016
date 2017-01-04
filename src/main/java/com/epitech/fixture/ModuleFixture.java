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
    public void             init() {
        this.add(new Module("facebook", "/img/modules/facebook.png", "Description for facebook", "https://www.facebook.com/v2.8/dialog/oauth?client_id=760127370809959&redirect_uri=http://localhost:8080/module/oauth&response_type=token"));
        this.add(new Module("gmail","/img/modules/gmail.png","Description for gmail/inbox", "https://accounts.google.com/o/oauth2/v2/auth?client_id=801969793224-jh02oeqpfvi2ocjrebbp21dks178qav5.apps.googleusercontent.com&redirect_uri=http://localhost:8080/module/oauth&response_type=token&scope=https://mail.google.com/"));
        this.add(new Module("yammer","/img/modules/yammer.png","Description for yammer", "https://www.yammer.com/oauth2/authorize?client_id=zPX7lhZerhGVn9beAAfGg&redirect_uri=http://localhost:8080/module/oauth&response_type=token"));
        this.add(new Module("dropbox","/img/modules/dropbox.png","Description for dropbox", "https://www.dropbox.com/1/oauth2/authorize?response_type=token&client_id=whfnl4mepngbvsb&redirect_uri=http://localhost:8080/module/oauth"));
        this.add(new Module("drive", "/img/modules/drive.png", "Description for drive", "https://accounts.google.com/o/oauth2/v2/auth?client_id=801969793224-jh02oeqpfvi2ocjrebbp21dks178qav5.apps.googleusercontent.com&redirect_uri=http://localhost:8080/module/oauth&response_type=token&scope=https://www.googleapis.com/auth/drive"));
        this.add(new Module("slack", "/img/modules/slack.png", "Description for slack", ""));
        this.add(new Module("github", "/img/modules/github.png", "Description for github", "https://github.com/login/oauth/authorize?client_id=d6b6716e70d77f94200e&response_type=token&redirect_uri=http://localhost:8080/module/oauth&scope=repo"));
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
