package com.xd.commander.aku.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xd.commander.aku.fragment.first_sort.FragmentFirstAll;
import com.xd.commander.aku.interf.SnackBarShow;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Administrator on 2017/4/17.
 */

public abstract class BaseFragment extends SupportFragment {
    protected OnBackToFirstListener _mBackToFirstListener;
    private Unbinder unbinder;
    private SnackBarShow snackbarShow;
    private int messageShowCount = 0;
    protected abstract int getLayoutId();

    protected abstract void initView(View view, Bundle savedInstanceState);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initView(view, savedInstanceState);
        return view;
    }
    public void count() {
        messageShowCount++;
        if (messageShowCount >= 5) {
            System.gc();
            messageShowCount = 0;
        }
    }
    public void show(String s) {if (snackbarShow !=null)
        snackbarShow.show(s);}
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        snackbarShow = null;
    }
    @Override
    public boolean onBackPressedSupport() {
        if (getChildFragmentManager().getBackStackEntryCount() > 1) {
            popChild();
        } else {
            if (this instanceof FragmentFirstAll) {   // 如果是 第一个Fragment 则退出app
                _mActivity.finish();
            } else {                                    // 如果不是,则回到第一个Fragment
                _mBackToFirstListener.onBackToFirstFragment();
            }
        }
        return true;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SnackBarShow) {
            snackbarShow = (SnackBarShow)context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement BaseExampleFragmentCallbacks");
        }
        if (context instanceof OnBackToFirstListener) {
            _mBackToFirstListener = (OnBackToFirstListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBackToFirstListener");
        }
    }
    public interface OnBackToFirstListener {
        void onBackToFirstFragment();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        _mBackToFirstListener = null;
    }

}
