package com.xd.commander.aku.fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tencent.bugly.beta.Beta;
import com.xd.commander.aku.R;
import com.xd.commander.aku.adapter.AdapterItem_other;
import com.xd.commander.aku.base.BaseFragment;
import com.xd.commander.aku.bean.Other;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
/**
 * Created by Administrator on 2017/4/27.
 */
public class FragmentOther extends BaseFragment {
    @BindView(R.id.authorIcon)
    ImageView authorIcon;
    @BindView(R.id.authorName)
    TextView authorName;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private String[] other_tv = { "清除离线缓存","检查更新"};
    private int[] other_iv = {
             R.drawable.vector_bmnavi_about, R.drawable.vector_bmnavi_about};
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_other;
    }

    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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
                        Beta.checkUpgrade();
                        break;
                }
            }
        });
    }
    private String[] other_tag = {"", "当前版本1.1"};
    private List<Other> getList() {
        List<Other> list = new ArrayList<>();
        for (int i = 0; i <2; i++) {
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
}
