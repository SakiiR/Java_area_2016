package com.epitech.utils;

/**
 * This class is used to store
 * password and salt in the same entity.
 *
 * @see PasswordManager
 */
public class                    PasswordContainer {

    private String              password;
    private String              salt;

    /**
     * Default constructor
     */
    public                      PasswordContainer() {  }

    /**
     * Constructor
     *
     * @param password the password to store.
     * @param salt the salt to store.
     */
    public                      PasswordContainer(String password, String salt) {
        this.password = password;
        this.salt = salt;
    }

    /**
     * Getter
     *
     * @return password
     */
    public String               getPassword() {
        return password;
    }

    /**
     * Setter
     *
     * @param password the password to set.
     * @return an instance of PasswordContainer.
     */
    public PasswordContainer    setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * Getter
     *
     * @return the salt
     */
    public String               getSalt() {
        return salt;
    }

    /**
     * Setter
     *
     * @param salt the salt to set.
     * @return an instance of PasswordContainer.
     */
    public PasswordContainer    setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    /**
     * this function returns a string information about the
     * PasswordContainer
     * @return the string information
     */
    @Override
    public String toString() {
        return "PasswordContainer{" +
                "password='" + this.password + '\'' +
                ", salt='" + this.salt + '\'' +
                '}';
    }
}
