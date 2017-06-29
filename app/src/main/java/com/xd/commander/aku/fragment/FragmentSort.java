package com.xd.commander.aku.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.xd.commander.aku.R;
import com.xd.commander.aku.adapter.AdapterItem_sort;
import com.xd.commander.aku.adapter.AdapterItem_scroll;
import com.xd.commander.aku.base.BaseFragment;
import com.xd.commander.aku.constants.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import cdflynn.android.library.scroller.BubbleScroller;
import cdflynn.android.library.scroller.ScrollerListener;

public class FragmentSort extends BaseFragment {

    @BindView(R.id.bubble_scroller)
    BubbleScroller bubbleScroller;
    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    private AdapterItem_scroll mContactScrollerAdapter;
    private LinearLayoutManager mLayoutManager;
    private boolean mProgrammaticScroll = true;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sort;
    }

    public FragmentSort newInstance() {
        return new FragmentSort();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        List<String> name = new ArrayList<>();
        Collections.addAll(name, Constants.data);
        List<String> han = new ArrayList<>();
        Collections.addAll(han,Constants.data1);
        mContactScrollerAdapter = new AdapterItem_scroll(name);
        AdapterItem_sort mContactAdapter = new AdapterItem_sort(getContext(), name,han, mContactScrollerAdapter);
        mLayoutManager = new LinearLayoutManager(getContext());
        bubbleScroller.setScrollerListener(mScrollerListener);
        bubbleScroller.setSectionScrollAdapter(mContactScrollerAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mContactAdapter);
        bubbleScroller.showSectionHighlight(0);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mProgrammaticScroll) {
                    mProgrammaticScroll = false;
                    return;
                }
                final int firstVisibleItemPosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
                bubbleScroller.showSectionHighlight(
                        mContactScrollerAdapter.sectionFromPosition(firstVisibleItemPosition));
            }
        });
    }
    private final ScrollerListener mScrollerListener = new ScrollerListener() {
        @Override
        public void onSectionClicked(int sectionPosition) {
            mRecyclerView.smoothScrollToPosition(
                    mContactScrollerAdapter.positionFromSection(sectionPosition));
            mProgrammaticScroll = true;
        }

        @Override
        public void onScrollPositionChanged(float percentage, int sectionPosition) {
            mRecyclerView.smoothScrollToPosition(
                    mContactScrollerAdapter.positionFromSection(sectionPosition));
            mProgrammaticScroll = true;
        }
    };
}

