package com.epitech.utils;

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
    public static final void    logInfo(String fmt, Object ...params) {
        System.out.println(String.format("[\033[34m^\033[0m] " + "\033[34m" + fmt + "\033[0m", params));
    }

    /**
     * Log a blue message to the console.
     *
     * @param str the string to display
     */
    public static final void    logInfo(String str) {
        System.out.println("[\033[34m^\033[0m] " + "\033[34m" + str + "\033[0m");
    }

    /**
     * Log a green message to the console
     *
     * @param fmt the format string
     * @param params the var args params
     */
    public static final void    logSuccess(String fmt, Object ...params) {
        System.out.println(String.format("[\033[32m+\033[0m] " + "\033[32m" + fmt + "\033[0m", params));
    }

    /**
     * Log a green message to the console
     *
     * @param str the string to display
     */
    public static final void    logSuccess(String str) {
        System.out.println("[\033[32m+\033[0m] " + "\033[32m" + str + "\033[0m");
    }

    /**
     * Log an orange message to the console.
     *
     * @param fmt the format string
     * @param params the var args params
     */
    public static final void    logWarning(String fmt, Object ...params) {
        System.out.println(String.format("[\033[33m!\033[0m] " + "\033[33m" + fmt + "\033[0m", params));
    }

    /**
     * Log an orange message to the console.
     *
     * @param str the string to display
     */
    public static final void    logWarning(String str) {
        System.out.println("[\033[33m!\033[0m] " + "\033[33m" + str + "\033[0m");
    }

    /**
     * Log a red message to the console.
     *
     * @param fmt the format string
     * @param params the var args params
     */
    public static final void    logError(String fmt, Object ...params) {
        System.out.println(String.format("[\033[31m-\033[0m] " + "\033[31m" + fmt + "\033[0m", params));
    }

    /**
     * Log a red message to the console.
     *
     * @param str the string to display
     */
    public static final void    logError(String str) {
        System.out.println("[\033[31m-\033[0m] " + "\033[31m" + str + "\033[0m");
    }

}
