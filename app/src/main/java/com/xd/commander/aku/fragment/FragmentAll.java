package com.xd.commander.aku.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xd.commander.aku.ActivityDetail;
import com.xd.commander.aku.R;
import com.xd.commander.aku.adapter.AdapterItem;
import com.xd.commander.aku.base.BaseFragment;
import com.xd.commander.aku.bean.Project;
import com.xd.commander.aku.constants.Constants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.xd.commander.aku.api.RetrofitHttp.createService;
import static com.xd.commander.aku.util.HanHua.toHanHua;

/**
 * Created by Administrator on 2017/4/15.
 */
public class FragmentAll extends BaseFragment {
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    private AdapterItem adapterItem;
    private int category;
    private List<Project> list_back;
    private String categoryname;
    public FragmentAll newInstance(int category) {
        FragmentAll newFragment = new FragmentAll();
        Bundle bundle = new Bundle();
        bundle.putInt("category", category);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            category = args.getInt("category");
            getData(category);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_all;
    }

    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        try {
            swipeRefreshLayout.setProgressViewEndTarget(true, 150);
            swipeRefreshLayout.setRefreshing(true);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    getData(category);
                }
            });
        } catch (Exception e) {
         show(e.toString());
        }

    }
    public void getData(final int category) {
        createService(getContext(), Constants.URL).getHtml(1, "created", category)
                .map(new Func1<ResponseBody, List<Project>>() {
                    @Override
                    public List<Project> call(ResponseBody mResponseBody) {
                        list_back = new ArrayList<>();
                        try {
                            Document doc = Jsoup.parse(mResponseBody.string());
                            Elements project = doc.select("div[class*=project]");
                            for (Element mElement : project) {
                                /*
                                 * 过滤广告，标题长度小于15的直接跳出循环
                                 */
                                if (mElement.text().length() < 15)
                                    continue;
                                // 提取名称 标签 时间 网址 简介 作者
                                String projectName = mElement.
                                        select("a[href^=/details]").get(0).text();
                                String projectTag = mElement.
                                        select("a[class*=tags]").text();
                                String projectTime = mElement.
                                        select("div[class*=ftr l]").text();
                                String http = mElement.
                                        select("a[href^=/detail]").attr("href");
                                String desc = mElement.
                                        select("div[class=desc]").html();
                                String projectAuthor;
                                try {projectAuthor = mElement.select("a[href*=user]").text();
                                } catch (Exception e) {Log.d("TAG", e.toString());
                                    projectAuthor = "";}
                                String newinfo;
                                try {newinfo = mElement.select("a[class*=badge new]").text();
                                } catch (Exception e) {Log.d("TAG", e.toString());
                                    newinfo = "";}
                                try {categoryname = mElement.select("a[class*=badge free]").text();
                                }catch (Exception e) {Log.d("TAG", e.toString());
                                    categoryname="";
                                }
                                if(categoryname.equals(""))
                                {try {
                                categoryname = mElement.select("a[class*=badge demo]").text();
                            }
                            catch (Exception ee){Log.d("TAG", ee.toString());
                                categoryname="";
                            }}
                                if(categoryname.equals(""))
                                {try {
                                    categoryname = mElement.select("a[class*=badge paid]").text();
                                }catch (Exception eee){Log.d("TAG", eee.toString());
                                    categoryname="";
                                }}
                                Project p = new Project(
                                        projectName, http,
                                        projectAuthor, projectTime,
                                        toHanHua(projectTag), desc ,toHanHua(newinfo),toHanHua(categoryname)
                                );
                                list_back.add(p);
                            }
                        } catch (IOException e) {
                           show(e.toString());
                        }
                        return list_back;
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        show(throwable.toString());
                    }
                })
                .subscribeOn(Schedulers.io())
                .onErrorReturn(new Func1<Throwable, List<Project>>() {
                    @Override
                    public List<Project> call(Throwable throwable) {
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Project>>() {
                    @Override
                    public void call(List<Project> list_back) {
                        try{
                        swipeRefreshLayout.setRefreshing(false);}
                        catch (Exception e){
                          show(e.toString());}
                        try {
                            if (list_back != null)
                                mRecyclerView.setAdapter(adapterItem = new AdapterItem(list_back));
                            adapterItem.notifyDataSetChanged();
                            adapterItem.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
                            //TODO @RecyclerView 点击监听事件
                            adapterItem.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    Intent intent = new Intent(view.getContext(), ActivityDetail.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("http", "https://android-arsenal.com" + ((Project) adapter.getData().get(position)).getHttp());
                                    bundle.putString("newinfo", ((Project) adapter.getData().get(position)).getNewinfo());
                                    bundle.putString("project", ((Project) adapter.getData().get(position)).getProjectName());
                                    bundle.putString("author", ((Project) adapter.getData().get(position)).getAuthor());
                                    bundle.putString("time", ((Project) adapter.getData().get(position)).getTime());
                                    bundle.putString("tag", ((Project) adapter.getData().get(position)).getTag());
                                    bundle.putString("desc", ((Project) adapter.getData().get(position)).getDesc());
                                    bundle.putString("category", ((Project) adapter.getData().get(position)).getCategory());
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            });
                            adapterItem.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                                @Override
                                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                    TextView v =(TextView) view.findViewById(R.id.tag);
                                    show(v.getText().toString());
                                }
                            });
                        } catch (Exception e) {
                            count();
                        }
                    }
                });
    }
    @Override
    public void onStop() {
        super.onStop();
        if(swipeRefreshLayout!=null)
        swipeRefreshLayout.setRefreshing(false);
    }
}
