package com.xd.commander.aku.fragment;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
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

public class FragmentSort extends BaseFragment {
    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.rv1)
    RecyclerView mRecyclerView1;
    @BindView(R.id.side_view)
    WaveSideBarView sideView;
    private Toast toast;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sort;
    }

    public FragmentSort newInstance() {
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
        baseViewHolderBaseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
               goToSearchActivity(Constants.site[position],position);
            }
        });
        baseViewHolderBaseQuickAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        sideView.setOnTouchLetterChangeListener(new WaveSideBarView.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                mRecyclerView.stopScroll();
                for(int i=0;i<27;i++){
                    if(Constants.letter[i].equals(letter))
                        mLayoutManager.scrollToPositionWithOffset(Constants.shunxu[i],0);
                }
            }
        });
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
                        c.drawRect(left, top, right, bottom, dividerPaint);
                    }
                }
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 1;
            }
        });
    }
    private void showToast(String info){
        if (toast==null) {
            toast = Toast.makeText(getContext(), info, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            LinearLayout layout = (LinearLayout) toast.getView();
            layout.setBackgroundColor(ContextCompat.getColor(getContext(),android.R.color.darker_gray));
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
            layoutParams.height=200;
            layoutParams.width=200;
            v.setLayoutParams(layoutParams);
            v.setGravity(Gravity.CENTER);
            v.setTextColor(Color.WHITE);
            v.setTextSize(50);
        }else {
            toast.setText(info);
        }
        toast.show();
    }
    private void goToSearchActivity(int site,int pos) {
        Intent intent = new Intent(getContext(), ActivtyCategory.class);
        intent.putExtra("url", "tag/" + site);
        intent.putExtra("category", Constants.hanhua[pos]);
        intent.putExtra("what", "分类");
        startActivity(intent);
    }
}

