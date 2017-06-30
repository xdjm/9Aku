package com.xd.commander.aku.fragment;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.xd.commander.aku.ActivityDetail;
import com.xd.commander.aku.R;
import com.xd.commander.aku.adapter.AdapterDragOrSwipeItem;
import com.xd.commander.aku.base.BaseFragment;
import com.xd.commander.aku.bean.Project;
import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.UpdateOrDeleteCallback;
import java.util.List;
import butterknife.BindView;

/**
 * Created by Administrator on 2017/4/25.
 */

public class FragmentCollect extends BaseFragment{
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private int[] imgID ={R.drawable.pic1,R.drawable.pic2,R.drawable.pic3};
    @Override
    protected int getLayoutId() {
        return R.layout.item_recycelerview;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        final List<Project> list = DataSupport.findAll(Project.class);
        AdapterDragOrSwipeItem adapterItem = new AdapterDragOrSwipeItem(list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //上下滑动监听
        OnItemDragListener onItemDragListener = new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {

            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
            }
        };
        //左右滑动监听
        OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener(){
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            /**
             *
             * @param viewHolder 监听完成后的逻辑
             * @param pos 位置
             */
            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, final int pos) {
                BaseViewHolder baseViewHolder =(BaseViewHolder)viewHolder;
                final String author =((TextView)baseViewHolder.getView(R.id.author)).getText().toString();
                final String project = ((TextView)baseViewHolder.getView(R.id.project)).getText().toString();
                DataSupport.deleteAllAsync(Project.class,"author = ? and projectName = ?",author,project).listen(new UpdateOrDeleteCallback() {
                    @Override
                    public void onFinish(int rowsAffected) {
                        show(author+"的"+project+"已删除");
                        show(list.size()+"");
                    }
                });
            }
            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
            }
        };
        ItemDragAndSwipeCallback mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adapterItem);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
        adapterItem.enableSwipeItem();
        adapterItem.setOnItemSwipeListener(onItemSwipeListener);
        adapterItem.enableDragItem(mItemTouchHelper);
        adapterItem.setOnItemDragListener(onItemDragListener);
        mRecyclerView.setAdapter(adapterItem);
        View v = getLayoutInflater(savedInstanceState).inflate(R.layout.item_banner,(ViewGroup)mRecyclerView.getParent(),false);
        CarouselView carouselView=(CarouselView) v.findViewById(R.id.carouselView);
        carouselView.setPageCount(imgID.length);
        carouselView.setImageListener(imageListener);
        adapterItem.addHeaderView(v);
        adapterItem.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(view.getContext(), ActivityDetail.class);
                Bundle bundle = new Bundle();
                bundle.putString("http",list.get(position).getHttp());
                bundle.putString("newinfo", list.get(position).getNewinfo());
                bundle.putString("project", list.get(position).getProjectName());
                bundle.putString("author", list.get(position).getAuthor());
                bundle.putString("time", list.get(position).getTime());
                bundle.putString("tag", list.get(position).getTag());
                bundle.putString("desc", list.get(position).getDesc());
                bundle.putString("category", list.get(position).getCategory());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    private ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            Glide.with(getContext()).load(imgID[position]).into(imageView);
        }
    };
}
