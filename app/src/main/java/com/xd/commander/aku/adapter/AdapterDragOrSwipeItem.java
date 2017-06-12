package com.xd.commander.aku.adapter;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xd.commander.aku.R;
import com.xd.commander.aku.bean.Project;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

/**
 * Created by Administrator on 2017/4/26.
 */

public class AdapterDragOrSwipeItem  extends BaseItemDraggableAdapter<Project, BaseViewHolder> {

    /**
     * @param item 所需的数据
     * */
    public AdapterDragOrSwipeItem(List<Project> item) {
        super(R.layout.item_project,item);
    }

    /**
     * @param helper 各种工具方法集合
     * @param item 对应位置实体
     */
    @Override
    protected void convert(BaseViewHolder helper, Project item) {
        /*
         * 装载数据
         */
        helper.setText(R.id.tag,item.getTag())
                .addOnClickListener(R.id.tag)
                .setText(R.id.time,item.getTime())
                .setText(R.id.project,item.getProjectName())
                .setText(R.id.author,item.getAuthor())
                .setText(R.id.tagnew,item.getNewinfo())
                .setText(R.id.tagcategory,item.getCategory());
        /*
         * category 颜色选择
         */
        switch (item.getCategory()){
            case "免费":
                helper.setBackgroundRes(R.id.tagcategory,R.drawable.shape_item_free);
                break;
            case "付费":
                helper.setBackgroundRes(R.id.tagcategory,R.drawable.shape_item_paid);
                break;
            case "演示":
                helper.setBackgroundRes(R.id.tagcategory,R.drawable.shape_item_demo);
                break;
        }
        if(item.getNewinfo().equals(""))
            helper.setBackgroundRes(R.id.tagnew,R.drawable.shape_item_old);
        /*
         * 装载概略描述
         */
        ((HtmlTextView)helper.getView(R.id.desc)).setHtml(item.getDesc()
                ,new HtmlHttpImageGetter(((HtmlTextView)helper.getView(R.id.desc))));
    }
}
