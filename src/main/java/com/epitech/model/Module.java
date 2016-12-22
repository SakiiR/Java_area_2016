package com.epitech.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by sakiir on 20/12/16.
 */
@Document(collection = "module")
public class            Module {

    @Id
    private String       id;

    private String       name;
    private String       imageUrl;
    private String       description;
    private String       loginUrl;

    public              Module() {

    }

    public              Module(String name, String imageUrl, String description) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.loginUrl = null;
    }

    public              Module(String name, String imageUrl, String description, String loginUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.loginUrl = loginUrl;
    }

    public String       getId() {
        return id;
    }

    public Module       getId(String id) {
        this.id = id;
        return this;
    }

    public String       getName() {
        return name;
    }

    public Module       setName(String name) {
        this.name = name;
        return this;
    }

    public String       getImageUrl() {
        return imageUrl;
    }

    public Module       setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String       getDescription() {
        return description;
    }

    public Module       setDescription(String description) {
        this.description = description;
        return this;
    }

    public String       getLoginUrl() {
        return loginUrl;
    }

    public Module       setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
        return this;
    }

    @Override
    public String toString() {
        return "Module{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
