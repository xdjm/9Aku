package com.xd.commander.aku;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xd.commander.aku.adapter.AdapterItem;
import com.xd.commander.aku.base.BaseActivity;
import com.xd.commander.aku.bean.Project;
import com.xd.commander.aku.constants.Constants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.xd.commander.aku.api.RetrofitHttp.createService;
import static com.xd.commander.aku.util.HanHua.toHanHua;

public class ActivityCategory extends BaseActivity {
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.v)
    View v;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.sort)
    TextView sort;
    @BindView(R.id.finish)
    ImageView finish;
    private AdapterItem adapterItem;
    private Bundle bundle;
    private List<Project> list;
    private String[] fenlei = {"注册时间", "最近更新", "评价", "名称"};
    private String[] paixu = {"?sort=created", "?sort=updated", "?sort=rating", "?sort=name"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_category;
    }

    @Override
    protected Context getActivity() {
        return ActivityCategory.this;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
                getWindow()
                        .getDecorView()
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                getWindow()
                        .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
        dealStatusBar();
        bundle = getIntent().getExtras();
        if (Objects.equals(bundle.getString("what"), "搜索"))
            sort.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(true);
        getCategoryData(bundle.getString("url"));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list = null;
                getCategoryData(bundle.getString("url"));
            }
        });
        content.setText(bundle.getString("what") + ":" + bundle.getString("category"));
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder a = new AlertDialog.Builder(getContext());
                a.setTitle("排序");
                a.setSingleChoiceItems(fenlei, 4, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        swipeRefreshLayout.setRefreshing(true);
                        getCategoryData(bundle.getString("url") + paixu[which]);
                        dialog.dismiss();
                    }
                });
                a.show();
            }
        });
    }

    public void getCategoryData(final String url) {
        createService(getContext(), Constants.URL).getSortProject(Constants.URL + url)
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
                                try {
                                    projectAuthor = mElement.select("a[href*=user]").text();
                                } catch (Exception e) {
                                    Log.d("TAG", e.toString());
                                    projectAuthor = "";
                                }
                                String newInfo;
                                String category;
                                try {
                                    newInfo = mElement.select("a[class*=badge new]").text();
                                } catch (Exception e) {
                                    Log.d("TAG", e.toString());
                                    newInfo = "";
                                }
                                try {
                                    category = mElement.select("a[class*=badge free]").text();
                                } catch (Exception e) {
                                    Log.d("TAG", e.toString());
                                    category = "";
                                }
                                if (category.equals("")) {
                                    try {
                                        category = mElement.select("a[class*=badge demo]").text();
                                    } catch (Exception e) {
                                        Log.d("TAG", e.toString());
                                        category = "";
                                    }
                                }
                                if (category.equals("")) {
                                    try {
                                        category = mElement.select("a[class*=badge paid]").text();
                                    } catch (Exception eee) {
                                        Log.d("TAG", eee.toString());
                                        category = "";
                                    }
                                }
                                Project p = new Project(
                                        projectName, detail,
                                        projectAuthor, projectTime,
                                        toHanHua(projectTag), desc, toHanHua(newInfo), toHanHua(category)
                                );
                                list.add(p);
                            }
                        } catch (Exception e) {
                            Log.e("tag", e.toString());
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

    private void dealStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = getStatusBarHeight();
            ViewGroup.LayoutParams lp = v.getLayoutParams();
            lp.height = statusBarHeight;
            v.setLayoutParams(lp);
        }
    }

    private int getStatusBarHeight() {
        Class<?> c;
        Object obj;
        Field field;
        int x, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }
}
