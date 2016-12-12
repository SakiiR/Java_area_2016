package com.epitech.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sakiir on 12/12/16.
 */
public class                            BodyParser {
    private HashMap<String, String>     _map;

    public                          BodyParser(String body) {
        this._map = new HashMap<>();

        for (String keyVal : body.split("&")) {
            String[]                keyValArray = keyVal.split("=");

            if (keyValArray.length == 2) {
                this._map.put(keyValArray[0], keyValArray[1]);
            }
        }
    }

    public String                   get(String key) {
        return this._map.get(key);
    }
}
