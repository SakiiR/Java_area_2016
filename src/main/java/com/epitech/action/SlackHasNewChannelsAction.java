package com.epitech.action;

import allbegray.slack.SlackClientFactory;
import allbegray.slack.type.Channel;
import allbegray.slack.webapi.SlackWebApiClient;
import com.epitech.utils.ErrorCode;
import com.epitech.utils.Logger;
import com.epitech.worker.AreaWorker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karraz_s on 05/01/17.
 */

/**
 * Action class to get any new channel on Slack
 */
public class SlackHasNewChannelsAction implements IAction {

    private Object data = null;
    private String token;

    public SlackHasNewChannelsAction(String token) {
        this.token = token;
    }

    /**
     * Function called by worker to get user's new slack channels
     * @return an ErrorCode status
     */
    @Override
    public ErrorCode run() {
        List<String> newChannels = new ArrayList<>();
        try {
            SlackWebApiClient client = SlackClientFactory.createWebApiClient(this.token);
            List<Channel> channels = client.getChannelList();
            for (Channel chan : channels) {
                Logger.logWarning(chan.toString());
                java.util.Date date = new java.util.Date(chan.getCreated()*1000);
                if (AreaWorker.isNewEntity(date)) {
                    newChannels.add(chan.getName());
                    Logger.logSuccess("New channel found: " + chan.getName());
                }
            }
        } catch (Exception e) {
            Logger.logError("Slack: " + e.toString());
            return ErrorCode.AUTH;
        }
        data = newChannels;
        if (!(newChannels.size() > 0)) {
            this.data = null;
        }
        return ErrorCode.SUCCESS;
    }

    /**
     * @return collected channels
     */
    @Override
    public Object getData() {
        return this.data;
    }
}
