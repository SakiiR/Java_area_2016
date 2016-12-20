package com.epitech.fixture;

import com.epitech.model.Module;
import com.epitech.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by sakiir on 20/12/16.
 */
@Component
public class                ModuleFixture {

    @Autowired
    ModuleRepository        moduleRepository;

    public void             init() {
        this.clear();
        this.moduleRepository.save(new Module("facebook", "/img/modules/facebook.png"));
        this.moduleRepository.save(new Module("twitter", "/img/modules/twitter.png"));
    }

    public void             clear() {
        this.moduleRepository.deleteAll();
    }
}
