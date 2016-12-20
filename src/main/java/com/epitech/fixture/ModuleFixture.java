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
        this.moduleRepository.save(new Module("facebook", "/img/modules/facebook.png", "Description for facebook"));
        this.moduleRepository.save(new Module("twitter", "/img/modules/twitter.png", "Description for twitter"));
    }

    public void             clear() {
        this.moduleRepository.deleteAll();
    }
}
