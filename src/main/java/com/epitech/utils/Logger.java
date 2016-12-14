package com.epitech.utils;

import java.util.Objects;

/**
 * Created by sakiir on 14/12/16.
 */

/**
 * This class is used to log information
 * on the console.
 */
public class                    Logger {
    /**
     * Log a blue message to the console.
     *
     * @param fmt the format string
     * @param params the var args params
     */
    public static final void    logInfo(String fmt, Objects ...params) {
        System.out.println(String.format("[\033[34m!\033[0m] " + fmt, params));
    }

    /**
     * Log a blue message to the console.
     *
     * @param str
     */
    public static final void    logInfo(String str) {
        System.out.println("[\033[34m!\033[0m] " + str);
    }
}
