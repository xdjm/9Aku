package com.xd.commander.aku;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.xd.commander.aku.base.BaseFragment;
import com.xd.commander.aku.fragment.FragmentOther;

/**
 * Copyright (C) 2017 By yjm at 14:00
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class FragmentFourthAll extends BaseFragment{

    public static FragmentFourthAll newInstance() {
        Bundle args = new Bundle();
        FragmentFourthAll fragment = new FragmentFourthAll();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fourth;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        if (findChildFragment(FragmentOther.class) == null) {
            loadRootFragment(R.id.fl_fourth_container, FragmentOther.newInstance());
        }
    }
}
