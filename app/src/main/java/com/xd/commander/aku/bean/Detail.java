package com.xd.commander.aku.bean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Administrator on 2017/3/25.
 */
//TODO 详细内容
public class Detail extends DataSupport {

    public Detail(List<String> list1, List<String> list2) {
        this.list1 = list1;
        this.list2 = list2;
    }

    private List<String> list1;
    //TODO
    private List<String> list2;
    public List<String> getList1() {
        return list1;
    }
    public List<String> getList2() {
        return list2;
    }
}
