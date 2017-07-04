package com.xd.commander.aku.fragment.first_sort;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xd.commander.aku.ActivtyCategory;
import com.xd.commander.aku.R;
import com.xd.commander.aku.base.BaseFragment;
import com.xd.commander.aku.constants.Constants;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import butterknife.BindView;
import cc.solart.wave.WaveSideBarView;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class FragmentSort extends BaseFragment {
    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.side_view)
    WaveSideBarView sideView;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sort;
    }

    public static FragmentSort newInstance() {
        return new FragmentSort();
    }
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        final List<String> name = new ArrayList<>();
        String[]s = new String[231];
        for(int i=0;i<231;i++){
            s[i]=Constants.data[i]+" "+Constants.hanhua[i];
        }
        Collections.addAll(name,s);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        final BaseQuickAdapter<String, BaseViewHolder> baseViewHolderBaseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_sort, name) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                SpannableStringBuilder ss = new SpannableStringBuilder(item);
                ColorStateList colorStateList = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    colorStateList = getContext().getColorStateList(R.color.colorPrimaryDark);
                } else {
                    try {
                        colorStateList = ColorStateList.createFromXml(getContext().getResources(), getContext().getResources().getXml(R.xml.colors));
                    } catch (XmlPullParserException | IOException e) {
                        e.printStackTrace();
                    }
                }
                ss.setSpan(new TextAppearanceSpan("serif", Typeface.BOLD_ITALIC, getContext().getResources().getDimensionPixelSize(R.dimen.textSize_xxx), colorStateList, colorStateList), 0, 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                ss.setSpan(new RelativeSizeSpan(1.5f), 0, 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                helper.setText(R.id.tv0, ss);
            }
        };
        mRecyclerView.setAdapter(baseViewHolderBaseQuickAdapter);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                int childCount = parent.getChildCount();
                int left = parent.getPaddingLeft();
                int right = parent.getWidth() - parent.getPaddingRight();
                Paint dividerPaint = new Paint();
                dividerPaint.setColor(ContextCompat.getColor(getContext(), android.R.color.darker_gray));
                for (int i = 0; i < childCount - 1; i++) {
                    if (i != 0) {
                        View view = parent.getChildAt(i);
                        float top = view.getBottom()+10;
                        float bottom = view.getBottom() + 11;
                        c.drawRect(left, top, right-100, bottom, dividerPaint);
                    }
                }
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 1;
            }
        });
        baseViewHolderBaseQuickAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        View view_head = getLayoutInflater(savedInstanceState).inflate(R.layout.fragment_sort_head,(ViewGroup)mRecyclerView.getParent(),false);
        ImageView i =(ImageView)view_head.findViewById(R.id.img);
        Glide.with(getContext()).load("https://raw.githubusercontent.com/vbauer/android-arsenal.com/master/misc/android-arsenal-logo.png").into(i);
        baseViewHolderBaseQuickAdapter.addHeaderView(view_head);
        baseViewHolderBaseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
               goToSearchActivity(Constants.site[position],position);
            }
        });
        sideView.setOnTouchLetterChangeListener(new WaveSideBarView.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                mRecyclerView.stopScroll();
                for(int i=0;i<27;i++){
                    if(Constants.letter[i].equals(letter))
                        mLayoutManager.scrollToPositionWithOffset(Constants.shunxu[i]+1,0);
                }
            }
        });
        OverScrollDecoratorHelper.setUpOverScroll(mRecyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
    }
    private void goToSearchActivity(int site,int pos) {
        Intent intent = new Intent(getContext(), ActivtyCategory.class);
        intent.putExtra("url", "tag/" + site);
        intent.putExtra("category", Constants.hanhua[pos]);
        intent.putExtra("what", "分类");
        startActivity(intent);
    }
}

