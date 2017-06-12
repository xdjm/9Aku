package com.xd.commander.aku.bean;
import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xd.commander.aku.adapter.AdapterExpandedItem;

/**
 * Created by Administrator on 2017/4/18.
 */

public class Sort0Item extends AbstractExpandableItem<Sort1Item> implements MultiItemEntity {
    public final String title;
    public Sort0Item(String title) {
        this.title = title;
    }
    @Override
    public int getItemType() {
        return AdapterExpandedItem.TYPE_LEVEL_0;
    }
    @Override
    public int getLevel() {
        return 0;
    }
}
