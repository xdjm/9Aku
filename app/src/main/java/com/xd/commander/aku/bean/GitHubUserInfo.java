package com.xd.commander.aku.bean;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/4/15.
 */

public class GitHubUserInfo extends DataSupport{
    private String login;
    @SerializedName(value = "id")
    private int authorid;
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
    private String name;
    private String company;
    private String blog;
    private String location;
    private String email;
    private String hireable;
    private String bio;
    private int public_repos;
    private int public_gists;
    private int followers;
    private int following;
    private String created_at;
    private String updated_at;
    public String getAvatar_url() {
        return avatar_url;
    }
    public String getName() {
        return name;
    }
    public GitHubUserInfo() {
        this.login =null;
        this.authorid = 0;
        this.avatar_url = null;
        this.gravatar_id = null;
        this.url = null;
        this.html_url = null;
        this.followers_url = null;
        this.following_url = null;
        this.gists_url = null;
        this.starred_url = null;
        this.subscriptions_url = null;
        this.organizations_url = null;
        this.repos_url = null;
        this.events_url = null;
        this.received_events_url = null;
        this.type = null;
        this.site_admin = true;
        this.name = null;
        this.company = null;
        this.blog = null;
        this.location = null;
        this.email = null;
        this.hireable = null;
        this.bio = null;
        this.public_repos = 0;
        this.public_gists = 0;
        this.followers = 0;
        this.following = 0;
        this.created_at = null;
        this.updated_at = null;
    }
}
