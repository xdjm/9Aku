package com.xd.commander.aku.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xd.commander.aku.R;
import com.xd.commander.aku.bean.Other;

import java.util.List;

public class AdapterItem_other extends BaseQuickAdapter<Other, BaseViewHolder> {

    public AdapterItem_other(List<Other> item) {
        super(R.layout.item_other,item);
    }
    /**
     * @param helper 各种工具方法集合
     * @param item 对应位置实体
     */
    @Override
    protected void convert(BaseViewHolder helper, Other item) {
        helper.setText(R.id.item_other_tv,item.getOther_tv())
                .setImageResource(R.id.item_other_iv,item.getOther_imageId())
                .setText(R.id.item_other_tag,item.getOther_tag());

    }

}
