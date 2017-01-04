package com.epitech.reaction;

import com.epitech.utils.ErrorCode;
import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GitHub;

import java.util.ArrayList;

/**
 * Created by Segmev on 04/01/17.
 */
public class GithubCreateRepositoriesReaction implements IReaction {
    private String          token;

    public                  GithubCreateRepositoriesReaction(String token) { this.token = token ; }

    /* data should be a list of String */
    @Override
    public ErrorCode        run(Object data) {
        try {
            ArrayList<String> repositories = (ArrayList<String>) data;
            GitHub hub = GitHub.connectUsingOAuth(this.token);
            for (String name: repositories) {
                hub.createRepository(name).create();
            }
        } catch (Exception e) {
            return ErrorCode.UNKNOWN;
        }
        return ErrorCode.SUCCESS;
    }
}
