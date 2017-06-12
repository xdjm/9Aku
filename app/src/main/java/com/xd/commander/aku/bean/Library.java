package com.xd.commander.aku.bean;

/**
 * Created by Administrator on 2017/4/29.
 */

public class Library {
    private String http;
    private String projectName;

    public Library(String author, String projectName, String http, String desc) {
        this.http = http;
        this.projectName = projectName;
        this.author = author;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public String getAuthor() {
        return author;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getHttp() {
        return http;
    }

    private String author;
    private String desc;
}

