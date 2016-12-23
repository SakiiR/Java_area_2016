package com.epitech.utils;

import java.util.HashMap;

/**
 * This class is used to parse body parameters.
 * ex : "foo=bar&bar=foo" => {"foo" : "bar", "bar" : "foo"}
 *
 * @see HashMap
 */
public class                            BodyParser {
    private HashMap<String, String>     _map;

    /**
     * Constructor (parse the string and fill the hashmap)
     *
     * @param body the string to parse
     */
    public                          BodyParser(String body) {
        this._map = new HashMap<>();

        for (String keyVal : body.split("&")) {
            String[]                keyValArray = keyVal.split("=");

            if (keyValArray.length == 2) {
                this._map.put(keyValArray[0], keyValArray[1]);
            }
        }
    }

    /**
     * Return a string by the key "key"
     *
     * @param key the key to seek.
     * @return a String value from the key.
     */
    public String                   get(String key) {
        return this._map.get(key);
    }
}
