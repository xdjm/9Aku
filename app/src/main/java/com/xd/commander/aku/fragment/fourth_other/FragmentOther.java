package com.xd.commander.aku.fragment.fourth_other;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.feedback.PgyFeedback;
import com.pgyersdk.views.PgyerDialog;
import com.tencent.bugly.beta.Beta;
import com.xd.commander.aku.ActivityLibrary;
import com.xd.commander.aku.R;
import com.xd.commander.aku.adapter.AdapterItem_other;
import com.xd.commander.aku.base.BaseFragment;
import com.xd.commander.aku.bean.Other;
import com.xd.commander.aku.interf.OnNetChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * Created by Administrator on 2017/4/27.
 */
public class FragmentOther extends BaseFragment {
    @BindView(R.id.changetheme)
    SwitchCompat aSwitch;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private OnNetChangeListener onNetChangeListener;
    private final String[] other_tv = { "清除缓存","开源相关","意见反馈","检查更新","关于作者"};
    private final int[] other_iv = {
             R.drawable.ic_delete_black_24dp,R.drawable.vector_navi_openedcode,R.drawable.vector_navi_crash,R.drawable.ic_system_update_black_24dp, R.drawable.ic_person_black_24dp};
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_other;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        preferences = getContext().getSharedPreferences("theme",Context.MODE_ENABLE_WRITE_AHEAD_LOGGING);
        editor =preferences.edit();
        PgyCrashManager.register(getContext());
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        AdapterItem_other adapteritem_other = new AdapterItem_other(getList());
        mRecyclerView.setAdapter(adapteritem_other);
        adapteritem_other.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                switch (position){
                    case 0:
                        cleanCache();
                        break;
                    case 1:
                        ActivityOptionsCompat compat = ActivityOptionsCompat.makeCustomAnimation(getContext(),
                        R.anim.activity_in, R.anim.activity_out);
                        ActivityCompat.startActivity(getContext(), new Intent(getContext(), ActivityLibrary.class), compat.toBundle());
                        break;
                    case 2:
                        PgyerDialog.setDialogTitleBackgroundColor("#1e89e7");
                        PgyerDialog.setDialogTitleTextColor("#ffffff");
                        PgyFeedback.getInstance().showDialog(getContext());
                        break;
                    case 3:
                        Beta.checkUpgrade();
                        break;
                    case 4:
                        //TODO 介绍自己

                        break;
                }
            }
        });
        aSwitch.setChecked(preferences.getInt("theme",1)==2);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putInt("theme",preferences.getInt("theme",1)==1?2:1);
                editor.apply();
                onNetChangeListener.onSync(isChecked);
            }
        });
        mRecyclerView.stopNestedScroll();
        mRecyclerView.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
    }
    private final String[] other_tag = {"", "","","当前版本1.2",""};
    private List<Other> getList() {
        List<Other> list = new ArrayList<>();
        for (int i = 0; i <5; i++) {
            Other other = new Other(other_iv[i],other_tv[i],other_tag[i]);
            list.add(other);
        }
        return list;
    }
    //计算文件夹大小
    private long getTotalSizeOfFilesInDir(final File file) {
        if (file.isFile())
            return file.length();
        final File[] children = file.listFiles();
        long total = 0;
        if (children != null)
            for (final File child : children)
                total += getTotalSizeOfFilesInDir(child);
        return total;
    }
    private  void cleanCache(){
        final File f = new File(getContext().getExternalCacheDir(), "akucache");
        AlertDialog.Builder a = new AlertDialog.Builder(getContext());
        a.setTitle("缓存有"+getTotalSizeOfFilesInDir(f)/1024+"K")
                .setNegativeButton("算了",null)
                .setPositiveButton("清除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteFile(f);
                            }
                        }
                ).show();
    }
    private void deleteFile(File file) {
        if (file.exists()) {//判断文件是否存在
            if (file.isFile()) {//判断是否是文件
                file.delete();//删除文件
            } else if (file.isDirectory()) {//否则如果它是一个目录
                File[] files = file.listFiles();//声明目录下所有的文件 files[];
                for (File file1 : files) {//遍历目录下所有的文件
                    this.deleteFile(file1);//把每个文件用这个方法进行迭代
                }
                file.delete();//删除文件夹
            }
            show("已清除");
        } else {
            show("所删除的文件不存在");
        }
    }

    public static FragmentOther newInstance() {
        Bundle args = new Bundle();
        FragmentOther fragment = new FragmentOther();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onNetChangeListener =(OnNetChangeListener)activity;
    }
}
