package com.epitech.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.KeyGenerator;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

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
     * Encode the password with bcrypt alogrythms.
     *
     * @param password the password to encode/hash
     * @return a String array with the salt and
     * the hashed password respectively.
     */
    public String[]                 encode(String password) {
        String[]                    ret = new String[2];
        BCryptPasswordEncoder       bcryptEncoder = new BCryptPasswordEncoder();

        SecureRandom random = new SecureRandom();
        ret[0] = new BigInteger(130, random).toString(32);
        ret[1] = bcryptEncoder.encode((ret[0] + password));
        return ret;
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
        BCryptPasswordEncoder       bcryptEncoder = new BCryptPasswordEncoder();

        return (bcryptEncoder.encode(salt + password).equals(hash));
    }
}
