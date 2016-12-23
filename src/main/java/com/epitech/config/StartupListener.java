package com.epitech.config;

import com.epitech.fixture.ModuleFixture;
import com.epitech.model.Module;
import com.epitech.repository.ModuleRepository;
import com.epitech.utils.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * This class is used to listen to some
 * application event like "startup"
 */
@Component
public class                    StartupListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ModuleRepository    moduleRepository;

    /**
     * This method is called when the application
     * start.
     *
     * @param event the event context object.
     */
    @Override
    public void                 onApplicationEvent(final ContextRefreshedEvent event) {
        ModuleFixture           moduleFixture = new ModuleFixture(this.moduleRepository);
        moduleFixture.init();
    }
}
