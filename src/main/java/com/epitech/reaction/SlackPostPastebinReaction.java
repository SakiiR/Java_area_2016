package com.epitech.reaction;

import allbegray.slack.webapi.SlackWebApiClient;
import allbegray.slack.SlackClientFactory;
import allbegray.slack.type.Channel;
import allbegray.slack.webapi.SlackWebApiClient;
import com.epitech.utils.ErrorCode;
import com.epitech.utils.Logger;

import java.util.List;

/**
 * Created by terrea_l on 06/01/17.
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
