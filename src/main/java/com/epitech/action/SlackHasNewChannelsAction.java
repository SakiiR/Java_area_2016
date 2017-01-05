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
public class SlackHasNewChannelsAction implements IAction {

    private Object data = null;
    private String token;

    public SlackHasNewChannelsAction(String token) {
        this.token = token;
    }

    @Override
    public ErrorCode run() {
        List<String> newChannels = new ArrayList<>();
        try {
            SlackWebApiClient client = SlackClientFactory.createWebApiClient(this.token);
            List<Channel> channels = client.getChannelList();
            Logger.logInfo("Got there " + channels.toString());
            for (Channel chan : channels) {
                Logger.logWarning(chan.toString());
                java.util.Date date = new java.util.Date(chan.getCreated()*1000);
                Logger.logInfo(date.toString());
                Logger.logInfo(String.valueOf(AreaWorker.isNewEntity(date)));
                if (AreaWorker.isNewEntity(date)) {
                    newChannels.add(chan.getName());
                    Logger.logError("New channel found: " + chan.getName());
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

    @Override
    public Object getData() {
        return this.data;
    }
}
