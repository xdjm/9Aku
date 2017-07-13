package com.xd.commander.aku.fragment.third_collect;
import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
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
import com.synnapps.carouselview.ImageClickListener;
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
    private final String[] title = {"可展开和收起的LinearLayout","原创 Android中常用的4中线程池","Android倒计时控件实现", "ConstraintLayout属性详解和Chain的使用", "智能语音输入查询天气app"};
    private final String[] imgID = {"http://mmbiz.qpic.cn/mmbiz_png/v1LbPPWiaSt4PWoJND1N6WASs01L4UK4qLCnvCRoTes6YQkxoGXFCp60eKwbzwqBXrpXY2icbMcYdqTbMbBJR3ag/0?wx_fmt=png",
            "http://mmbiz.qpic.cn/mmbiz_png/v1LbPPWiaSt5WbhwA0jxJw40vASpTibkJfvB3feJSR5ialhM4058a1yt2IxXFgSrrwfebrnZREmCQiaUY1qFWrZ7Eg/0?wx_fmt=png",
            "http://mmbiz.qpic.cn/mmbiz_jpg/v1LbPPWiaSt55PHdXMdJ44aB0yWfdqicG5X0Ile9TXs5aYO4O5kNPl6AKgAicZNYwJlRiaV2mN6FEjK8pKUIhpAtUg/0?wx_fmt=jpeg"
            , "http://mmbiz.qpic.cn/mmbiz_png/v1LbPPWiaSt79C3NyucAOmzzXzrUOEwG9CbNicklbEnmNRRhUHk8Ss5iaiaae5IgoZEYbzH89PrmJEf9DRK35aWqcQ/0?wx_fmt=png"
            , "http://mmbiz.qpic.cn/mmbiz_jpg/v1LbPPWiaSt5IwHRBbdlrkd6p0pJYIFRpokfhHyLy8uUpibMhdU43w6HeWRBR8n1iaINzM4ib19O9sCHV749AahkzA/0?wx_fmt=jpeg"};

    private final String[] bannerNet={"https://mp.weixin.qq.com/s?timestamp=1499943387&src=3&ver=1&signature=d4Ji9q4Wdqup9TIui745LkOyFtyruu5mwrDdntFjG1nRch9eCr15aHbyjvV1IM7CG8*hOSPrOlxF1dZPlCjN8ZuffyQPwyjVyIn2SzMcgmC*fHQO5g-mt2x3-NUxFIPuRgVmPFODmwPhHeMtxMQzFXmNfLkKwIci-LyUI51jCfQ=",
            "https://mp.weixin.qq.com/s?timestamp=1499943039&src=3&ver=1&signature=d4Ji9q4Wdqup9TIui745LkkAaUKTF7Xmq-VQRkMe*Bc2P4-Qwmtafe5yfHGvr7JTVk8r4lVO2xlqXOe5YaF11gCMpWPZQ0Lps1zKKN9E0ku2fkb*ql-aQTHCztJ*QWUzVrm7SWZXCGGBHKh-T8i5fJ0Rk5klDAfnMNUO6fW5fZg=",
            "https://mp.weixin.qq.com/s?timestamp=1499943387&src=3&ver=1&signature=d4Ji9q4Wdqup9TIui745LkOyFtyruu5mwrDdntFjG1nRch9eCr15aHbyjvV1IM7CG8*hOSPrOlxF1dZPlCjN8Vb5RaK5aVmFqRn6CggXX0A1NMkgitXuXK649B-j6HatsXr-7fFWgi8Hh7tsXCt2wjHWkTwMgLe5gnXtxXCbvWc=",
            "https://mp.weixin.qq.com/s?timestamp=1499943387&src=3&ver=1&signature=d4Ji9q4Wdqup9TIui745LkOyFtyruu5mwrDdntFjG1nRch9eCr15aHbyjvV1IM7CG8*hOSPrOlxF1dZPlCjN8XyLtHUYWs1BrS6ey4dBgNmtQ4S5Ht1qadJVkVDT*m5tq9nY3rrDYw7YAeysaMlZHjSprY3Wo7zjHqTud-Srsqg=",
            "https://mp.weixin.qq.com/s?timestamp=1499943387&src=3&ver=1&signature=d4Ji9q4Wdqup9TIui745LkOyFtyruu5mwrDdntFjG1nRch9eCr15aHbyjvV1IM7CG8*hOSPrOlxF1dZPlCjN8e7Sula1IAn-EzBbOWfoU2LdC0I6Lx*Ki90VQn7oNjnVV96t*lRjqGxLKgVx9EL6ZT3Z8UG9H4AEcrzO9aOc2Jk="};
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
        setSwipeDrag();
        list = DataSupport.findAll(Project.class);
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
                onFresh();
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
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(bannerNet[position]);
                intent.setData(uri);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
       onFresh();
    }
}
