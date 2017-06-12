package com.xd.commander.aku.adapter;

import android.content.Context;
import android.content.Intent;

import android.view.View;

import android.widget.TextView;


import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;
import com.xd.commander.aku.ActivtyCategory;
import com.xd.commander.aku.R;
import com.xd.commander.aku.bean.ItemInfo;
import com.xd.commander.aku.bean.Sort0Item;
import com.xd.commander.aku.bean.Sort1Item;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import butterknife.ButterKnife;

import static org.litepal.LitePalApplication.getContext;

/**
 * Created by Administrator on 2017/4/18.
 */

public class AdapterExpandedItem extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private String s =loadJson(getContext());
    private ItemInfo[] itemInfos=new ItemInfo[]{};
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    public AdapterExpandedItem(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_expandable_lv0);
        addItemType(TYPE_LEVEL_1, R.layout.item_expandable_lv1);
    }
    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        ButterKnife.bind(this,holder.getConvertView());
        Gson gson = new Gson();
        itemInfos = gson.fromJson(s,ItemInfo[].class);
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_0:
                final Sort0Item lv0 = (Sort0Item)item;
                holder.setText(R.id.tv0, lv0.title);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        if (lv0.isExpanded())
                            collapse(pos);
                        else expand(pos);
                    }
                });
                break;
            case TYPE_LEVEL_1:
                final Sort1Item lv1 = (Sort1Item)item;
                holder.setText(R.id.tv1, lv1.title);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView v = (TextView)view.findViewById(R.id.tv1);
                        String[]list=v.getText().toString().split(" \\(\\d+\\)");
                        String sss = "";
                        for(int i=0;i<=280;i++)
                        {
                           if(Objects.equals(itemInfos[i].getProject(), list[0])){
                               sss=itemInfos[i].getHttp();
                            break;}
                        }
                        Intent intent = new Intent(view.getContext(), ActivtyCategory.class);
                        intent.putExtra("url",sss+list[1]);
                        intent.putExtra("category",list[0]);
                        intent.putExtra("what","分类");
                        view.getContext().startActivity(intent);
                    }
                });
                break;
        }
    }
    private static String loadJson(Context context) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open("http.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return jsonString;
    }
}
