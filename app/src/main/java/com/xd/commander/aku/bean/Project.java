package com.xd.commander.aku.bean;
import org.litepal.crud.DataSupport;

/**
 * Created by yjm on 2017/3/8.
 */

public class Project extends DataSupport{
    /**
     *
     * @param projectName 开源库的名称
     * @param http        开源库的网址
     * @param author      开源库的作者
     * @param time        开源库的发布时间
     * @param tag         开源库的标签类别
     * @param desc        开源库的大概描述
     */
    public Project(String projectName, String http, String author, String time, String tag,String desc,String newinfo,String category) {
        this.projectName = projectName;
        this.http = http;
        this.author = author;
        this.time = time;
        this.tag = tag;
		this.desc =desc;
        this.newinfo= newinfo;
        this.category= category;
    }
	public String getDesc() {
        return desc;
    }//TODO 简介
    public String getTag() {
        return tag;
    }//TODO 标签
    public String getProjectName() {
        return projectName;
    }//TODO 项目
    public String getTime() {
        return time;
    }//TODO 时间
    public String getAuthor() {
        return author;
    }//TODO 作者
    public String getHttp() {
        return http;
    }//TODO 网址
    public String getNewinfo(){return newinfo;}//TODO 新
    public String getCategory() {
        return category;
    }//TODO 类别
    private final String http;
    private final String projectName;
    private final String tag;
    private final String time;
    private final String author;
	private final String desc;
    private final String newinfo;
    private final String category;

    public Project(Project project) {
        this.http = project.getHttp();
        this.projectName = project.getProjectName();
        this.tag = project.getTag();
        this.time = project.getTime();
        this.author = project.getAuthor();
        this.desc = project.getDesc();
        this.newinfo = project.getNewinfo();
        this.category = project.getCategory();
    }
}
