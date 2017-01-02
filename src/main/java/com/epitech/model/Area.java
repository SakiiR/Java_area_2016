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

    public                      Area() {}

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

    public String               getId() {
        return id;
    }

    public Area                 setId(String id) {
        this.id = id;
        return this;
    }

    public String               getActionName() {
        return actionName;
    }

    public Area                 setActionName(String actionName) {
        this.actionName = actionName;
        return this;
    }

    public String               getReactionName() {
        return reactionName;
    }

    public Area                 setReactionName(String reactionName) {
        this.reactionName = reactionName;
        return this;
    }

    public String               getActionModuleName() {
        return actionModuleName;
    }

    public Area                 setActionModuleName(String actionModuleName) {
        this.actionModuleName = actionModuleName;
        return this;
    }

    public String               getReactionModuleName() {
        return reactionModuleName;
    }

    public Area                 setReactionModuleName(String reactionModuleName) {
        this.reactionModuleName = reactionModuleName;
        return this;
    }

    public String               getDescription() {
        return description;
    }

    public Area                 setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return String.format("%s AND %s", this.actionName, this.reactionName);
    }
}
