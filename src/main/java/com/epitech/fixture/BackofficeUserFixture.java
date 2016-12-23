package com.epitech.fixture;

import com.epitech.model.BackofficeUser;
import com.epitech.repository.BackofficeUserRepository;
import com.epitech.utils.PasswordContainer;
import com.epitech.utils.PasswordManager;

/**
 * This class is used to add default data
 * to the database when the server is starting.
 */
public class                            BackofficeUserFixture {
    static private String               secure_password = "123";
    static private String               secure_username = "SakiiR";

    private BackofficeUserRepository    backofficeUserRepository;

    /**
     * Constructor.
     *
     * @param backofficeUserRepository the repository object.
     */
    public                              BackofficeUserFixture(BackofficeUserRepository backofficeUserRepository) {
        this.backofficeUserRepository = backofficeUserRepository;
    }

    /**
     * Add a user to the database with existing
     * verification.
     *
     * @param user The user to add.
     */
    public void                         add(BackofficeUser user) {
        if (null == backofficeUserRepository.findByUsername(user.getUsername())) {
            this.backofficeUserRepository.save(user);
        }
    }

    /**
     * Init the database with user(s).
     */
    public void                         init() {
        PasswordContainer               passwordContainer;
        PasswordManager                 passwordManager = new PasswordManager();
        BackofficeUser                  backofficeUser = new BackofficeUser();

        passwordContainer = passwordManager.encode(BackofficeUserFixture.secure_password);
        backofficeUser
                .setPassword(passwordContainer.getPassword())
                .setSalt(passwordContainer.getSalt())
                .setUsername(BackofficeUserFixture.secure_username);
        this.add(backofficeUser);
    }

    /**
     * Totally clean all the users.
     */
    public void                         clear() {
        this.backofficeUserRepository.deleteAll();
    }
}
