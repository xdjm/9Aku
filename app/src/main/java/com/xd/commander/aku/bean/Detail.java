package com.xd.commander.aku.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/3/25.
 */
//TODO 详细内容
public class Detail extends DataSupport {

    /**
     * @param dt_0 General 一般的标题
     * @param dd_0 General一般的内容
     * @param dt_1 Additional 额外的标题
     * @param dd_1 Additional 额外的内容
     * @param markdown Markdown readme文档
     * @param http 网址
     * @param star 评级
     * @param watch 阅读
     * @param fork 复刻
     * @param issue 问题
     */
    public Detail(String dt_0, String dd_0, String dt_1, String dd_1, String markdown, String http, int star, int watch, int fork, int issue) {
        this.dt_0 = dt_0;
        this.dd_0 = dd_0;
        this.dt_1 = dt_1;
        this.dd_1 = dd_1;
        this.markdown = markdown;
        this.http = http;
        this.star = star;
        this.watch = watch;
        this.fork = fork;
        this.issue = issue;

    }

    public String getDt_0() {
        return dt_0;
    }

    public String getDd_0() {
        return dd_0;
    }

    public String getDt_1() {
        return dt_1;
    }

    public String getDd_1() {
        return dd_1;
    }

    private final String dt_0;
    private final String dd_0;
    private final String dt_1;
    private final String dd_1;
    private final String http;

    public int getStar() {
        return star;
    }

    public int getIssue() {
        return issue;
    }

    public int getFork() {
        return fork;
    }

    public int getWatch() {
        return watch;
    }

    private final int star;
    private final int watch;
    private final int fork;
    private final int issue;
    private final String markdown;

    @Override
    public String toString() {
        return "Detail{" +
                "dd_0='" + dd_0 + '\'' +
                '}';
    }

    public String getHttp() {
        return http;
    }
}
