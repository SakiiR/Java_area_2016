package com.epitech.reaction;

import com.epitech.utils.ErrorCode;
import com.epitech.utils.Logger;
import org.kohsuke.github.GitHub;

import java.util.ArrayList;

/**
 * this class is a reaction creating new github repository
 */
public class GithubCreateRepositoriesReaction implements IReaction {
    private String          token;

    /**
     * the constructor for GithubCreateRepositoriesReaction
     * @param token the token from the oauth2 connexion
     */
    public                  GithubCreateRepositoriesReaction(String token) { this.token = token ; }

    /* data should be a list of String */

    /**
     * this function run executes the GithubCreateRepositoriesReaction
     * @param data the data for creating new repositories
     * @return an ErrorCode status
     */
    @Override
    public ErrorCode        run(Object data) {
        try {
            ArrayList<String> repositories = (ArrayList<String>) data;
            GitHub hub = GitHub.connectUsingOAuth(this.token);
            for (String name: repositories) {
                hub.createRepository(name).create();
            }
        } catch (Exception e) {
            Logger.logError("Error while creating github repository : %s", e.getMessage());
            return ErrorCode.AUTH;
        }
        return ErrorCode.SUCCESS;
    }
}
