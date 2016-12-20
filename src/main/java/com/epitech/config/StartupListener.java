package com.epitech.config;

import com.epitech.fixture.ModuleFixture;
import com.epitech.model.Module;
import com.epitech.repository.ModuleRepository;
import com.epitech.utils.Logger;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by sakiir on 20/12/16.
 */
@Component
public class                    StartupListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ModuleRepository    moduleRepository;

    @Override
    public void                 onApplicationEvent(final ContextRefreshedEvent event) {
        ModuleFixture           moduleFixture = new ModuleFixture(this.moduleRepository);
        moduleFixture.init();
    }
}
