package com.xd.commander.aku.bean;

/**
 * Created by Administrator on 2017/4/30.
 */

public class Banner {
    private String url;
    private String subtitle;
    private String http;

    public Banner(String url, String subtitle, String http) {
        this.url = url;
        this.subtitle = subtitle;
        this.http = http;
    }

    public String getUrl() {
        return url;
    }

    public Banner setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getHttp() {
        return http;
    }

    public Banner setHttp(String http) {
        this.http = http;
        return this;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public Banner setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }
}
