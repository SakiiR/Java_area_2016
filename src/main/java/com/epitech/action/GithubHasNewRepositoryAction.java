package com.epitech.action;

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
        List<GHRepository>          newRepositories = new ArrayList<>();
        try {
            GitHub hub = GitHub.connectUsingOAuth(this.token);
            PagedIterable<GHRepository> repos = hub.getMyself().listRepositories();
            for (GHRepository repo : repos) {
                if (AreaWorker.isNewEntity(repo.getCreatedAt())) {
                    Logger.logSuccess("New repository found!" + repo.getName() + " -> " + repo.getCreatedAt().toString());
                    newRepositories.add(repo);
                }
            }
        }  catch (Exception exception) {
            Logger.logError("GithubAction: Can't connect or get repositories.");
            return ErrorCode.UNKNOWN;
        }
        this.data = newRepositories;
        if (!(newRepositories.size() > 0)) {
            this.data = null;
        }
        return ErrorCode.SUCCESS;
    }

    public Object                   getData() {
        return this.data;
    }

}