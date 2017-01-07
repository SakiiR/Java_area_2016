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

    public SlackPostPastebinReaction(String token) {
        this.token = token;
    }

    @Override
    public ErrorCode run(Object data) {
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
