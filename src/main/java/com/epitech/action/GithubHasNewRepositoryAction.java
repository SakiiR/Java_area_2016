package com.epitech.action;

import com.epitech.reaction.YammerPostToGroupReaction;
import com.epitech.worker.AreaWorker;
import com.epitech.utils.ErrorCode;
import com.epitech.utils.Logger;
import org.kohsuke.github.*;
import java.util.List;
import java.util.ArrayList;


public class                        GithubHasNewRepositoryAction implements IAction {

    private Object data = null;
    private String token;

    public                          GithubHasNewRepositoryAction(String token) { this.token = token; }

    public ErrorCode                run() {
        List<YammerPostToGroupReaction.PostRequest> repoPost = new ArrayList<>();
        try {
            GitHub hub = GitHub.connectUsingOAuth(this.token);
            PagedIterable<GHRepository> repos = hub.getMyself().listRepositories();
            for (GHRepository repo : repos) {
                if (AreaWorker.isNewEntity(repo.getCreatedAt())) {
                    Logger.logSuccess("New repository found!" + repo.getName() + " -> " + repo.getCreatedAt().toString());
                    YammerPostToGroupReaction.PostRequest post = new YammerPostToGroupReaction.PostRequest();
                    String body = repo.getName() + ".\n\n"  +
                            "Checkout my new Github repository at: " + repo.getHtmlUrl();
                    post.setBody(body);
                    post.setGroupName("TestArea");
                    repoPost.add(post);
                }
            }
        }  catch (Exception exception) {
            Logger.logError("GithubAction: Can't connect or get repositories.");
            return ErrorCode.UNKNOWN;
        }
        this.data = repoPost;
        if (!(repoPost.size() > 0)) {
            Logger.logError("GithubAction: No new repository.");
            this.data = null;
        }
        return ErrorCode.SUCCESS;
    }

    public Object                   getData() {
        return this.data;
    }

}