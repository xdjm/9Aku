package com.xd.commander.aku.bean;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/4/15.
 */

public class GitHubUserInfo extends DataSupport{
    public GitHubUserInfo(GitHubUserInfo gitHubUserInfo)
    {
        this.login = gitHubUserInfo.getLogin();
        this.authorid = gitHubUserInfo.getAuthorid();
        this.avatar_url = gitHubUserInfo.getAvatar_url();
        this.gravatar_id = gitHubUserInfo.getGravatar_id();
        this.url = gitHubUserInfo.getUrl();
        this.html_url = gitHubUserInfo.getHtml_url();
        this.followers_url = gitHubUserInfo.getFollowers_url();
        this.following_url = gitHubUserInfo.getFollowing_url();
        this.gists_url = gitHubUserInfo.getGists_url();
        this.starred_url = gitHubUserInfo.getStarred_url();
        this.subscriptions_url = gitHubUserInfo.getSubscriptions_url();
        this.organizations_url = gitHubUserInfo.getOrganizations_url();
        this.repos_url = gitHubUserInfo.getRepos_url();
        this.events_url = gitHubUserInfo.getEvents_url();
        this.received_events_url = gitHubUserInfo.getReceived_events_url();
        this.type = gitHubUserInfo.getType();
        this.site_admin = gitHubUserInfo.isSite_admin();
        this.name = gitHubUserInfo.getName();
        this.company = gitHubUserInfo.getCompany();
        this.blog = gitHubUserInfo.getBlog();
        this.location = gitHubUserInfo.getLocation();
        this.email = gitHubUserInfo.getEmail();
        this.hireable = gitHubUserInfo.getHireable();
        this.bio = gitHubUserInfo.getBio();
        this.public_repos = gitHubUserInfo.getPublic_repos();
        this.public_gists = gitHubUserInfo.getPublic_gists();
        this.followers = gitHubUserInfo.getFollowers();
        this.following = gitHubUserInfo.getFollowing();
        this.created_at = gitHubUserInfo.getCreated_at();
        this.updated_at = gitHubUserInfo.getUpdated_at();
    }


    public int getAuthorid() {
        return authorid;
    }

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

    public String getLogin() {
        return login;
    }


    public String getAvatar_url() {
        return avatar_url;
    }

    public String getGravatar_id() {
        return gravatar_id;
    }

    public String getUrl() {
        return url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public String getFollowers_url() {
        return followers_url;
    }

    public String getFollowing_url() {
        return following_url;
    }

    public String getGists_url() {
        return gists_url;
    }

    public String getStarred_url() {
        return starred_url;
    }

    public String getSubscriptions_url() {
        return subscriptions_url;
    }

    public String getOrganizations_url() {
        return organizations_url;
    }

    public String getRepos_url() {
        return repos_url;
    }

    public String getEvents_url() {
        return events_url;
    }

    public String getReceived_events_url() {
        return received_events_url;
    }

    public String getType() {
        return type;
    }

    public boolean isSite_admin() {
        return site_admin;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getBlog() {
        return blog;
    }

    public String getLocation() {
        return location;
    }

    public String getEmail() {
        return email;
    }

    public String getHireable() {
        return hireable;
    }

    public String getBio() {
        return bio;
    }

    public int getPublic_repos() {
        return public_repos;
    }

    public int getPublic_gists() {
        return public_gists;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
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
