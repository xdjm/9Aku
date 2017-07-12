package com.xd.commander.aku.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright (C) 2017 By yjm at 13:13
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class Repos {

    private int id;
    private String name;
    private String full_name;
    private OwnerBean owner;
    @SerializedName("private")
    private boolean privateX;
    private String html_url;
    private String description;
    private boolean fork;
    private String url;
    private String forks_url;
    private String keys_url;
    private String collaborators_url;
    private String teams_url;
    private String hooks_url;
    private String issue_events_url;
    private String events_url;
    private String assignees_url;
    private String branches_url;
    private String tags_url;
    private String blobs_url;
    private String git_tags_url;
    private String git_refs_url;
    private String trees_url;
    private String statuses_url;
    private String languages_url;
    private String stargazers_url;
    private String contributors_url;
    private String subscribers_url;
    private String subscription_url;
    private String commits_url;
    private String git_commits_url;
    private String comments_url;
    private String issue_comment_url;
    private String contents_url;
    private String compare_url;
    private String merges_url;
    private String archive_url;
    private String downloads_url;
    private String issues_url;
    private String pulls_url;
    private String milestones_url;
    private String notifications_url;
    private String labels_url;
    private String releases_url;
    private String deployments_url;
    private String created_at;
    private String updated_at;
    private String pushed_at;
    private String git_url;
    private String ssh_url;
    private String clone_url;
    private String svn_url;
    private Object homepage;
    private int size;
    private int stargazers_count;
    private int watchers_count;
    private String language;
    private boolean has_issues;
    private boolean has_projects;
    private boolean has_downloads;
    private boolean has_wiki;
    private boolean has_pages;
    private int forks_count;
    private Object mirror_url;
    private int open_issues_count;
    private int forks;
    private int open_issues;
    private int watchers;
    private String default_branch;
    private int network_count;
    private int subscribers_count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public int getStargazers_count() {
        return stargazers_count;
    }





    public int getForks_count() {
        return forks_count;
    }


    public int getOpen_issues_count() {
        return open_issues_count;
    }




    public int getWatchers() {
        return watchers;
    }

    public static class OwnerBean {
        /**
         * login : alshell7
         * id : 14362492
         * avatar_url : https://avatars1.githubusercontent.com/u/14362492?v=3
         * gravatar_id :
         * url : https://api.github.com/users/alshell7
         * html_url : https://github.com/alshell7
         * followers_url : https://api.github.com/users/alshell7/followers
         * following_url : https://api.github.com/users/alshell7/following{/other_user}
         * gists_url : https://api.github.com/users/alshell7/gists{/gist_id}
         * starred_url : https://api.github.com/users/alshell7/starred{/owner}{/repo}
         * subscriptions_url : https://api.github.com/users/alshell7/subscriptions
         * organizations_url : https://api.github.com/users/alshell7/orgs
         * repos_url : https://api.github.com/users/alshell7/repos
         * events_url : https://api.github.com/users/alshell7/events{/privacy}
         * received_events_url : https://api.github.com/users/alshell7/received_events
         * type : User
         * site_admin : false
         */

        private String login;
        private int id;
        private String avatar_url;
        private String gravatar_id;
        private String url;
        private String html_url;
        private String followers_url;
        private String following_url;
        private String gists_url;
        private String starred_url;
        private String subscriptions_url;
        private String organizations_url;
        private String repos_url;
        private String events_url;
        private String received_events_url;
        private String type;
        private boolean site_admin;
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
