package com.xd.commander.aku.adapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xd.commander.aku.R;
import com.xd.commander.aku.bean.Library;
import java.util.List;

/**
 * Created by Administrator on 2017/4/11.
 */

/**
 * 基于BRVAH的RecyclerView适配器
 */
public class AdapterItem_library extends BaseQuickAdapter<Library, BaseViewHolder> {
    /**
     * @param item 所需的数据
     * */
    public AdapterItem_library(List<Library> item) {
        super(R.layout.item_library,item);
    }
    /**
     * @param helper 各种工具方法集合
     * @param item 对应位置实体
     */
    @Override
    protected void convert(BaseViewHolder helper, Library item) {

        helper.setText(R.id.projectname,item.getProjectName())
                .setText(R.id.detail,item.getDesc())
                .setText(R.id.author,item.getAuthor());
    }
}