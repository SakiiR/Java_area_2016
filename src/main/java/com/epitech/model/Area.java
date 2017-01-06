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

    /**
     * the Constructor for Area
     */
    public                      Area() {}

    /**
     * the constructor for Area
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
     * @return
     */
    public String               getId() {
        return id;
    }

    /**
     * the setter for the Id
     * @param id the new Id to set
     * @return
     */
    public Area                 setId(String id) {
        this.id = id;
        return this;
    }

    /**
     * the getter for the action name
     * @return
     */
    public String               getActionName() {
        return actionName;
    }

    /**
     * the setter for the action name
     * @param actionName
     * @return
     */
    public Area                 setActionName(String actionName) {
        this.actionName = actionName;
        return this;
    }

    /**
     * the getter for the reaction name
     * @return
     */
    public String               getReactionName() {
        return reactionName;
    }

    /**
     * the setter for the reaction name
     * @param reactionName
     * @return
     */
    public Area                 setReactionName(String reactionName) {
        this.reactionName = reactionName;
        return this;
    }

    /**
     * the getter for the action module name
     * @return
     */
    public String               getActionModuleName() {
        return actionModuleName;
    }

    /**
     * the setter for the action module name
     * @param actionModuleName
     * @return
     */
    public Area                 setActionModuleName(String actionModuleName) {
        this.actionModuleName = actionModuleName;
        return this;
    }

    /**
     * the getter for the reaction module name
     * @return
     */
    public String               getReactionModuleName() {
        return reactionModuleName;
    }

    /**
     * the setter for the reaction module name
     * @param reactionModuleName
     * @return
     */
    public Area                 setReactionModuleName(String reactionModuleName) {
        this.reactionModuleName = reactionModuleName;
        return this;
    }

    /**
     * the getter for the description of the area
     * @return
     */
    public String               getDescription() {
        return description;
    }

    /**
     * the setter for the description of the area
     * @param description
     * @return
     */
    public Area                 setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return String.format("%s AND %s", this.actionName, this.reactionName);
    }
}
