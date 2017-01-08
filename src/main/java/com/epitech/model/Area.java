package com.epitech.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class represent an area
 * inside the database.
 */
@Document(collection = "area")
public class                    Area {
    @Id
    private String              id;

    private String              actionName;
    private String              reactionName;
    private String              actionModuleName;
    private String              reactionModuleName;
    private String              description;
    private User                tmpUser;

    /**
     * the Constructor for Area
     */
    public                      Area() {}

    /**
     * the constructor for Area
     *
     * @param actionName the action name
     * @param reactionName the reaction name
     * @param actionModuleName the actionModule name
     * @param reactionModuleName the reactionModule name
     * @param description the description of the area
     */
    public                      Area(String actionName,
                                     String reactionName,
                                     String actionModuleName,
                                     String reactionModuleName,
                                     String description) {
        this.actionName = actionName;
        this.reactionName = reactionName;
        this.actionModuleName = actionModuleName;
        this.reactionModuleName = reactionModuleName;
        this.description = description;
    }

    /**
     * the getter for the id
     *
     * @return a string id
     */
    public String               getId() {
        return id;
    }

    /**
     * the setter for the Id
     *
     * @param id the new Id to set
     * @return this
     */
    public Area                 setId(String id) {
        this.id = id;
        return this;
    }

    /**
     * the getter for the action name
     *
     * @return a string action name
     */
    public String               getActionName() {
        return actionName;
    }

    /**
     * the setter for the action name
     *
     * @param actionName the String action name.
     * @return an Area this
     */
    public Area                 setActionName(String actionName) {
        this.actionName = actionName;
        return this;
    }

    /**
     * the getter for the reaction name
     *
     * @return a string reaction name
     */
    public String               getReactionName() {
        return reactionName;
    }

    /**
     * the setter for the reaction name
     *
     * @param reactionName the string reaction name
     * @return an Area this
     */
    public Area                 setReactionName(String reactionName) {
        this.reactionName = reactionName;
        return this;
    }

    /**
     * the getter for the action module name
     *
     * @return a string action module name
     */
    public String               getActionModuleName() {
        return actionModuleName;
    }

    /**
     * the setter for the action module name
     *
     * @param actionModuleName the string action module name.
     * @return an Area this
     */
    public Area                 setActionModuleName(String actionModuleName) {
        this.actionModuleName = actionModuleName;
        return this;
    }

    /**
     * the getter for the reaction module name
     *
     * @return a string reaction module name
     */
    public String               getReactionModuleName() {
        return reactionModuleName;
    }

    /**
     * the setter for the reaction module name
     *
     * @param reactionModuleName the String reaction module name.
     * @return an Area this
     */
    public Area                 setReactionModuleName(String reactionModuleName) {
        this.reactionModuleName = reactionModuleName;
        return this;
    }

    /**
     * the getter for the description of the area
     *
     * @return a string description
     */
    public String               getDescription() {
        return description;
    }

    /**
     * the setter for the description of the area.
     *
     * @param description The string description.
     * @return an Area this
     */
    public Area                 setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * One assigned user to get.
     *
     * @return the user.
     */
    public User                 getTmpUser() {
        return tmpUser;
    }

    /**
     * One assigned user to set.
     *
     * @param tmpUser the user to set.
     * @return a user instance.
     */
    public Area                 setTmpUser(User tmpUser) {
        this.tmpUser = tmpUser;
        return this;
    }

    /**
     * this function returns a string with all information
     * about the area.
     *
     * @return a string information
     */
    @Override
    public String toString() {
        return String.format("%s AND %s", this.actionName, this.reactionName);
    }
}
