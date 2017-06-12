package com.xd.commander.aku;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xd.commander.aku.adapter.AdapterItem;
import com.xd.commander.aku.base.BaseActivity;
import com.xd.commander.aku.bean.Project;
import com.xd.commander.aku.constants.Constants;
import com.xd.commander.aku.util.ThemeUtil;

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

public class ActivtyCategory extends BaseActivity {
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    private AdapterItem adapterItem;
    private Bundle bundle;
private List<Project> list;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_category;
    }

    @Override
    protected Context getActivity() {
        return ActivtyCategory.this;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        bundle = getIntent().getExtras();
        toolbar.setTitle(bundle.getString("what"));
        toolbar.setSubtitle(bundle.getString("category"));
        toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);
        swipeRefreshLayout.setProgressViewEndTarget(true,400);
        swipeRefreshLayout.setRefreshing(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mRecyclerView.setNestedScrollingEnabled(false);
        getCategoryData(bundle.getString("url"));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list = null;
                getCategoryData(bundle.getString("url"));
            }
        });
    }
    public void getCategoryData(final String url) {
        createService(getContext(), Constants.URL).getSortProject(Constants.URL+url)
                .map(new Func1<ResponseBody, List<Project>>() {
                    @Override
                    public List<Project> call(ResponseBody mResponseBody) {
                        list = new ArrayList<>();
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
                                String detail = mElement.
                                        select("a[href^=/detail]").attr("href");
                                String desc = mElement.
                                        select("div[class=desc]").html();
                                String projectAuthor;
                                try {projectAuthor = mElement.select("a[href*=user]").text();
                                } catch (Exception e) {
                                    Log.d("TAG", e.toString());
                                    projectAuthor = "";}
                                String newinfo;
                                try {newinfo = mElement.select("a[class*=badge new]").text();
                                } catch (Exception e) {Log.d("TAG", e.toString());
                                    newinfo = "";}
                                String category;
                                try {category = mElement.select("a[class*=badge free]").text();
                                }catch (Exception e) {Log.d("TAG", e.toString());
                                    category="";
                                }
                                if(category.equals(""))
                                {try {
                                    category = mElement.select("a[class*=badge demo]").text();
                                }
                                catch (Exception ee){Log.d("TAG", ee.toString());
                                    category="";
                                }}
                                if(category.equals(""))
                                {try {
                                    category = mElement.select("a[class*=badge paid]").text();
                                }catch (Exception eee){Log.d("TAG", eee.toString());
                                    category="";
                                }}
                                Project p = new Project(
                                        projectName, detail,
                                        projectAuthor, projectTime,
                                        toHanHua(projectTag), desc ,toHanHua(newinfo),toHanHua(category)
                                );
                                list.add(p);
                            }
                        } catch (IOException e) {
                            Log.d("TAG", e.toString());
                        }
                        return list;
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
                        try {
                            if (!(list_back == null))
                                mRecyclerView.setAdapter(adapterItem = new AdapterItem(list_back));
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            adapterItem.notifyDataSetChanged();
                            adapterItem.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
                            swipeRefreshLayout.setRefreshing(false);
                            //TODO @RecyclerView 点击监听事件
                            adapterItem.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    Intent intent = new Intent(view.getContext(), ActivityDetail.class);
                                    bundle.putString("http", "https://android-arsenal.com" + ((Project) adapter.getData().get(position)).getHttp());
                                    bundle.putString("project", ((Project) adapter.getData().get(position)).getProjectName());
                                    bundle.putString("author", ((Project) adapter.getData().get(position)).getAuthor());
                                    bundle.putString("time", ((Project) adapter.getData().get(position)).getTime());
                                    bundle.putString("tag", ((Project) adapter.getData().get(position)).getTag());
                                    bundle.putString("desc", ((Project) adapter.getData().get(position)).getDesc());
                                    bundle.putString("category", ((Project) adapter.getData().get(position)).getCategory());
                                    bundle.putString("newinfo", ((Project) adapter.getData().get(position)).getNewinfo());
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            });
                        } catch (Exception e) {
                            Log.d("fragment_all", e.toString());
                        }
                    }

                });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int theme;
        if (savedInstanceState == null) {
            theme = ThemeUtil.getAppTheme(ActivtyCategory.this);
        } else {
            theme = savedInstanceState.getInt("theme");
        }
        setTheme(theme);
        super.onCreate(savedInstanceState);
    }
}