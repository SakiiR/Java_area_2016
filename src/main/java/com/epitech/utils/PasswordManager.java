package com.epitech.utils;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by sakiir on 14/12/16.
 */

/**
 * This class is used to encode or check hashed password
 * with bcrypt crypto library.
 *
 * @see BCryptPasswordEncoder
 */
public class                        PasswordManager {
    public                          PasswordManager() {  }

    /**
     * This method is used to encode/hash password.
     *
     * @param text the text to encode/hash
     * @return the encoded/hashed text;
     */
    private String                  hash(String text) {
        ShaPasswordEncoder          shaPasswordEncoder = new ShaPasswordEncoder(512);
        String                      ret;

        ret = shaPasswordEncoder.encodePassword(text, "");
        return ret;
    }

    /**
     * Encode the password with bcrypt alogrythms.
     *
     * @param password the password to encode/hash
     * @return a String array with the salt and
     * the hashed password respectively.
     */
    public PasswordContainer        encode(String password) {
        String                      salt;
        String                      hash;

        SecureRandom random = new SecureRandom();
        salt = new BigInteger(130, random).toString(32);
        hash = this.hash(salt + password);
        return new PasswordContainer(hash, salt);
    }

    /**
     * This method is checking if the password given
     * is parameter is valid relative to the hash and the salt.
     *
     * @param password the clear password to check.
     * @param salt the password salt.
     * @param hash the hashed password.
     * @return a boolean that tell if the password is valid.
     */
    public boolean                  check(String password, String salt, String hash) {
        Logger.logInfo("Checking hash(%s, %s){%s} == %s", salt, password, this.hash(salt + password), hash);
        return (this.hash(salt + password).equals(hash));
    }
}
