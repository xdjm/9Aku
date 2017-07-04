package com.xd.commander.aku.bean;

/**
 * Created by Administrator on 2017/5/7
 */
public class Other {
    private int other_imageId;
    private String other_tv;
    public String getOther_tag() {
        return other_tag;
    }
    private String other_tag;
    public Other(int other_imageId, String other_tv,String other_tag) {
        this.other_imageId = other_imageId;
        this.other_tv = other_tv;
        this.other_tag = other_tag;
    }
    public int getOther_imageId() {
        return other_imageId;
    }
    public String getOther_tv() {
        return other_tv;
    }

}
