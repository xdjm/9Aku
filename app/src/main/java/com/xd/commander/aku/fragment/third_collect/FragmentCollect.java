package com.xd.commander.aku.fragment.third_collect;
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
import com.synnapps.carouselview.ViewListener;
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

public class FragmentCollect extends BaseFragment {
    public static FragmentCollect newInstance() {
        return new FragmentCollect();
    }

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.carouselView)
    CarouselView carouselView;
    private AdapterDragOrSwipeItem adapterItem;
    private OnItemSwipeListener onItemSwipeListener;
    private OnItemDragListener listener;
    private List<Project> list;
    private Bundle bundle;
    private final String[] title = {"实现一个清新美观的加载按钮", "Android热修复——Tinker", "自定义进度条PictureProgressBar"};
    private final String[] imgID = {"http://mmbiz.qpic.cn/mmbiz_jpg/v1LbPPWiaSt7ouJwsjCmXx11tWqNxX9ERSMs9uA85DKRq4ZULot8z8MbwTUq2a69VIciaRmfGsLZ6T4yYGkWxTnw/0?wx_fmt=jpeg"
            , "http://mmbiz.qpic.cn/mmbiz_png/v1LbPPWiaSt79QTAYwuGj1IySOhRedYYacX545tJW90g69nauhnrl15ImMnibdV97BWTBibQ7ZicPlmvRXJQ9HeBrA/0?wx_fmt=png"
            , "http://mmbiz.qpic.cn/mmbiz_jpg/v1LbPPWiaSt5y8xhrxkshbia47BglCKkibD23WB5edSL9fItX7aPRcKYGic94bsOdddd8dI1HnIUVPuAyNunBzhJ3w/0?wx_fmt=jpeg"};

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collect_banner;
    }

    @Override
    protected void initView(View view, final Bundle savedInstanceState) {
        setBanner(savedInstanceState);
        bundle =savedInstanceState;
        onFresh();
    }

    private void onFresh() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list = DataSupport.findAll(Project.class);
        setSwipeDrag();
        adapterItem = new AdapterDragOrSwipeItem(list);
        if (list.size()==0){
            adapterItem.addHeaderView(getEmptyLayout(bundle));
        }
        bindSwipDrag();
        mRecyclerView.setAdapter(adapterItem);
        adapterItem.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(view.getContext(), ActivityDetail.class);
                Bundle bundle = new Bundle();
                bundle.putString("http", list.get(position).getHttp());
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

    private void bindSwipDrag() {
        ItemDragAndSwipeCallback mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adapterItem);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
        adapterItem.enableSwipeItem();
        adapterItem.setOnItemSwipeListener(onItemSwipeListener);
        adapterItem.enableDragItem(mItemTouchHelper);
        adapterItem.setOnItemDragListener(listener);
    }

    private View getEmptyLayout(Bundle bundle) {
        return getLayoutInflater(bundle).inflate(R.layout.activity_collect_empty, (ViewGroup) mRecyclerView.getParent(), false);
    }

    private void setSwipeDrag() {

            listener = new OnItemDragListener() {
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
        onItemSwipeListener = new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {}

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {}

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder,  int pos) {
                BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
                final String author = ((TextView) baseViewHolder.getView(R.id.author)).getText().toString();
                final String project = ((TextView) baseViewHolder.getView(R.id.project)).getText().toString();
                DataSupport.deleteAllAsync(Project.class, "author = ? and projectName = ?", author, project).listen(new UpdateOrDeleteCallback() {
                    @Override
                    public void onFinish(int rowsAffected) {
                        show(DataSupport.findAll(Project.class).size() + "");
                    }
                });
                if (list.size()==0){
                    adapterItem.addHeaderView(getEmptyLayout(bundle));
                }
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
            }
        };

    }

    private void setBanner(final Bundle bundle) {
        carouselView.setPageCount(imgID.length);
        ViewListener viewListener = new ViewListener() {
            @Override
            public View setViewForPosition(int position) {
                View customView = getLayoutInflater(bundle).inflate(R.layout.item_banner, null);
                TextView labelTextView = (TextView) customView.findViewById(R.id.labelTextView);
                ImageView fruitImageView = (ImageView) customView.findViewById(R.id.fruitImageView);
                Glide.with(getContext()).load(imgID[position]).into(fruitImageView);
                labelTextView.setText(title[position]);
                return customView;
            }
        };
        carouselView.setViewListener(viewListener);
    }

    @Override
    public void onResume() {
        super.onResume();
       onFresh();
    }
}
