package com.epitech.reaction;

import allbegray.slack.webapi.SlackWebApiClient;
import allbegray.slack.SlackClientFactory;
import com.epitech.utils.ErrorCode;
import com.epitech.utils.Logger;

import java.util.List;


/**
 * This class is used to create pastebin on slack.
 */
public class SlackPostPastebinReaction implements IReaction {

    private String token;

    /**
     * the constructor for GithubCreateRepositoriesReaction
     * @param token the token from the oauth2 connexion
     */
    public SlackPostPastebinReaction(String token) {
        this.token = token;
    }

    /**
     * this function run executes the GithubCreateRepositoriesReaction
     * @param data the data for creating new repositories
     * @return an ErrorCode status
     */
    @Override
    public ErrorCode run(Object data) {
        if (data == null) {
            return ErrorCode.SUCCESS;
        }
        List<String> urls = (List<String>) data;
        try {
            SlackWebApiClient webApiClient = SlackClientFactory.createWebApiClient(token);
            for (String url : urls) {
                webApiClient.postMessage("general", url);
            }
        } catch (Exception e) {
            Logger.logError("Slack: " + e.toString());
            return ErrorCode.AUTH;
        }
        return ErrorCode.SUCCESS;
    }
}
