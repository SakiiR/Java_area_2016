package com.epitech.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class is the representation
 * of a Module.
 */
@Document(collection = "module")
public class                Module {

    @Id
    private String          id;

    private String          name;
    private String          imageUrl;
    private String          description;
    private String          loginUrl;
    private String          tokenUrl;

    /**
     * the constructor for Module
     */
    public                  Module() {

    }

    /**
     * the constructor for Module
     * @param name the module name
     * @param imageUrl the image url
     * @param description the description
     */
    public                  Module(String name, String imageUrl, String description) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.loginUrl = null;
    }

    /**
     * the constructor for Module
     * @param name the module name
     * @param imageUrl the image url
     * @param description the description
     * @param loginUrl the login url
     * @param tokenUrl the token url
     */
    public                  Module(String name, String imageUrl, String description, String loginUrl, String tokenUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.loginUrl = loginUrl;
        this.tokenUrl = tokenUrl;
    }

    /**
     * the getter for the module name
     * @return a string name
     */
    public String           getName() {
        return name;
    }

    /**
     * the setter for the module name
     * @param name
     * @return a Module this
     */
    public Module           setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * the getter for the image url
     * @return a string image url
     */
    public String           getImageUrl() {
        return imageUrl;
    }

    /**
     * the setter for the image url
     * @param imageUrl
     * @return a Module this
     */
    public Module           setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    /**
     * the getter for the description
     * @return a string description
     */
    public String           getDescription() {
        return description;
    }

    /**
     * the setter for the description
     * @param description
     * @return a Module this
     */
    public Module           setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * the getter for the login url
     * @return a string login url
     */
    public String           getLoginUrl() {
        return loginUrl;
    }

    /**
     * the setter for the login url
     * @param loginUrl
     * @return a Module this
     */
    public Module           setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
        return this;
    }

    /**
     * the getter for the id
     * @return a string id
     */
    public String           getId() {
        return id;
    }

    /**
     * the setter for the id
     * @param id
     * @return a Module this
     */
    public Module           setId(String id) {
        this.id = id;
        return this;
    }

    /**
     * the getter for the token url
     * @return a string token url
     */
    public String           getTokenUrl() {
        return tokenUrl;
    }

    /**
     * the setter for the token url
     * @param tokenUrl
     * @return a Module this
     */
    public Module           setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
        return this;
    }

    /**
     * this function return all the informations about a module
     * @return a string informations
     */
    @Override
    public String           toString() {
        return "Module{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
