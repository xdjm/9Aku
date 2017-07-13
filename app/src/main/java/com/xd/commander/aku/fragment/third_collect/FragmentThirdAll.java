package com.xd.commander.aku.fragment.third_collect;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.xd.commander.aku.R;
import com.xd.commander.aku.base.BaseFragment;

public class FragmentThirdAll extends BaseFragment{
    public static FragmentThirdAll newInstance() {

        Bundle args = new Bundle();

        FragmentThirdAll fragment = new FragmentThirdAll();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_third;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (findChildFragment(FragmentCollect.class) == null) {
            // ShopFragment是flow包里的
            loadRootFragment(R.id.fl_container_third, FragmentCollect.newInstance());
        }
    }
}
