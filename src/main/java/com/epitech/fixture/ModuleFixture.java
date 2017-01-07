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
        this.add(new Module("gmail","/img/modules/gmail.png","Description for gmail/inbox", "https://accounts.google.com/o/oauth2/v2/auth?client_id=801969793224-jh02oeqpfvi2ocjrebbp21dks178qav5.apps.googleusercontent.com&redirect_uri=http://localhost:8080/module/oauth&response_type=code&scope=https://mail.google.com/", "https://www.googleapis.com/oauth2/v4/token?client_id=801969793224-jh02oeqpfvi2ocjrebbp21dks178qav5.apps.googleusercontent.com&client_secret=fFap7KHXvC_c9zK7RA4h2NkJ&redirect_uri=http://localhost:8080/module/oauth&grant_type=authorization_code"));
        this.add(new Module("yammer","/img/modules/yammer.png","Description for yammer", "https://www.yammer.com/oauth2/authorize?client_id=zPX7lhZerhGVn9beAAfGg&redirect_uri=http://localhost:8080/module/oauth&response_type=code", "https://www.yammer.com/oauth2/access_token?client_id=zPX7lhZerhGVn9beAAfGg&client_secret=F5AWOGTQcnJvhwaEGvZuvm9PZDlygnVu8ETbA5sbdk&grant_type=authorization_code"));
        this.add(new Module("dropbox","/img/modules/dropbox.png","Description for dropbox", "https://www.dropbox.com/1/oauth2/authorize?response_type=code&client_id=whfnl4mepngbvsb&redirect_uri=http://localhost:8080/module/oauth", "https://api.dropboxapi.com/oauth2/token?client_id=whfnl4mepngbvsb&client_secret=du47e6b6457lgm8&redirect_uri=http://localhost:8080/module/oauth&grant_type=authorization_code"));
        this.add(new Module("drive", "/img/modules/drive.png", "Description for drive", "https://accounts.google.com/o/oauth2/v2/auth?client_id=801969793224-jh02oeqpfvi2ocjrebbp21dks178qav5.apps.googleusercontent.com&redirect_uri=http://localhost:8080/module/oauth&response_type=code&scope=https://www.googleapis.com/auth/drive", "https://www.googleapis.com/oauth2/v4/token?client_id=801969793224-jh02oeqpfvi2ocjrebbp21dks178qav5.apps.googleusercontent.com&client_secret=fFap7KHXvC_c9zK7RA4h2NkJ&redirect_uri=http://localhost:8080/module/oauth&grant_type=authorization_code"));
        this.add(new Module("slack", "/img/modules/slack.png", "Description for slack", "https://slack.com/oauth/authorize?client_id=93289188645.119710639747&redirect_uri=http://localhost:8080/module/oauth&scope=users%3aread+channels%3aread+team%3aread+chat%3awrite%3abot", "https://slack.com/api/oauth.access?client_id=93289188645.119710639747&client_secret=11547e7f4bdabb38ba00ff1be69de930&redirect_uri=http://localhost:8080/module/oauth"));
        this.add(new Module("github", "/img/modules/github.png", "Description for github", "https://github.com/login/oauth/authorize?client_id=d6b6716e70d77f94200e&redirect_uri=http://localhost:8080/module/oauth&scope=repo", "https://github.com/login/oauth/access_token?client_id=d6b6716e70d77f94200e&client_secret=de0ba6a6eda22391deb257c4b21c8fd7eb637f1a&redirect_uri=http://localhost:8080/module/oauth"));
        this.add(new Module("pastebin", "/img/modules/pastebin.png", "Description for pastebin", "", ""));
    }

    /**
     * This method clear the modules.
     */
    public void                 clear() {
        this.moduleRepository.deleteAll();
    }
}
