package com.epitech.config;

import com.epitech.fixture.AreaFixture;
import com.epitech.fixture.BackofficeUserFixture;
import com.epitech.fixture.ModuleFixture;
import com.epitech.repository.AreaRepository;
import com.epitech.repository.BackofficeUserRepository;
import com.epitech.repository.ModuleRepository;
import com.epitech.repository.UserRepository;
import com.epitech.worker.AreaWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * This class is used to listen to some
 * application event like "startup"
 */
@Component
public class                            StartupListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ModuleRepository            moduleRepository;

    @Autowired
    private BackofficeUserRepository    backofficeUserRepository;

    @Autowired
    private AreaRepository              areaRepository;

    @Autowired
    private UserRepository              userRepository;

    /**
     * This method is called when the application
     * start.
     *
     * @param event the event context object.
     */
    @Override
    public void                 onApplicationEvent(final ContextRefreshedEvent event) {
        ModuleFixture           moduleFixture = new ModuleFixture(this.moduleRepository);
        BackofficeUserFixture   backofficeUserFixture = new BackofficeUserFixture(this.backofficeUserRepository);
        AreaFixture             areaFixture = new AreaFixture(this.areaRepository);
        AreaWorker              areaWorker = new AreaWorker(this.userRepository, this.areaRepository);

        backofficeUserFixture.init();
        moduleFixture.init();
        areaFixture.init();
        new Thread(areaWorker).start();
    }
}
