package com.xd.commander.aku;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.esafirm.rxdownloader.RxDownloader;
import com.joaquimley.faboptions.FabOptions;
import com.sunfusheng.marqueeview.MarqueeView;
import com.xd.commander.aku.base.BaseActivity;
import com.xd.commander.aku.bean.Detail;
import com.xd.commander.aku.bean.GitHubUserInfo;
import com.xd.commander.aku.bean.Project;
import com.xd.commander.aku.bean.Repos;
import com.xd.commander.aku.constants.Constants;
import com.xd.commander.aku.util.HanHua;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.ResponseBody;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.xd.commander.aku.api.RetrofitHttp.createService;

/**
 * Created by Administrator on 2017/3/25.
 */
public class ActivityDetail extends BaseActivity {

    @BindView(R.id.marqueeView)
    MarqueeView marqueeView;
    @BindView(R.id.repoName)
    TextView repoName;
    @BindView(R.id.author)
    ImageView authorPic;
    @BindView(R.id.watchbtn)
    RadioButton watchbtn;
    @BindView(R.id.starbtn)
    RadioButton starbtn;
    @BindView(R.id.forkbtn)
    RadioButton forkbtn;
    @BindView(R.id.issuebtn)
    RadioButton issuebtn;
    @BindView(R.id.detail)
    RecyclerView detail_rv;
    @BindView(R.id.webview)
    WebView mdView;
    @BindView(R.id.fab_options)
    FabOptions fabOptions;
    @BindView(R.id.cool)
    CoordinatorLayout cool;
    private Document doc;
    private Bundle bundle;
    private Project project;
    private String setMDText;

    private String[] title = {"类别", "标签", "执照", "最小SDK", "注册时间", "喜好", "链接"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected Context getActivity() {
        return ActivityDetail.this;
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
        fabOptions.setButtonsMenu(R.menu.menu_detail_faboption);
        bundle = getIntent().getExtras();
        project = new Project(bundle.getString("project"), bundle.getString("http"),
                bundle.getString("author"), bundle.getString("time"), bundle.getString("tag"),
                bundle.getString("desc"), bundle.getString("newinfo"), bundle.getString("category"));
        mdView.setBackgroundColor(0);
        mdView.getSettings().setDefaultTextEncodingName("UTF -8");
        getAuthor();
        getData(bundle.getString("http"));
        repoName.setText(project.getProjectName());
        fabOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.collect:
                        CollectProject();
                        break;

                    case R.id.detail:
                        break;


                    case R.id.download:
                        CheckPermission();
                        break;

                    case R.id.share:
                        share(getString(R.string.share_info), bundle.getString("project") + bundle.getString("http"));
                        break;

                    default:
                        // no-op
                }
            }
        });
    }

    private void getAuthor() {
        createService(getContext(), Constants.GitHub).getUserInfo(bundle.getString("author"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GitHubUserInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Glide.with(getContext()).load(R.drawable.error_author).into(authorPic);
                    }

                    @Override
                    public void onNext(GitHubUserInfo gitHubUserInfo) {
                        Glide.with(getContext()).load(gitHubUserInfo.getAvatar_url()).error(R.drawable.error_author).crossFade().into(authorPic);
                    }
                });
    }

    private void getData(String http) {
        createService(getContext(), Constants.URL).getProjectDetail(http)
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, Detail>() {
                    @Override
                    public Detail call(ResponseBody responseBody) {
                        try {
                            doc = Jsoup.parse(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Elements elements = doc.select("div#projectDesc");
                        Log.i("tag", elements.html());
                        setMDText = elements.html();
                        Elements elements_dd = doc.select("div div div div dl dd");
                        List<String> s = new ArrayList<>();
                        List<String> ss = new ArrayList<>();
                        for (int i = 0; i < 12; i++) {
                            if (i == 1)
                                s.add(HanHua.toHanHua(elements_dd.get(i).text()));
                            if (i < 7 && i != 1)
                                s.add(elements_dd.get(i).text());
                            else ss.add(elements_dd.get(i).text());
                        }
                        return new Detail(s, ss);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Detail>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Detail detail) {
                        BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.activity_detail_projectinfo, detail.getList1()) {

                            @Override
                            protected void convert(BaseViewHolder helper, String item) {
                                helper.setText(R.id.detailcontent, item);
                                helper.setText(R.id.detailtitle, title[helper.getPosition()]);
                            }
                        };
                        detail_rv.setLayoutManager(new LinearLayoutManager(getContext()));
                        detail_rv.setAdapter(baseQuickAdapter);
                        marqueeView.startWithList(detail.getList2());
                        mdView.loadData(setMDText, "text/html", "UTF-8");
                        getRepos("https://api.github.com/repos" + detail.getList1().get(6).substring(18));
                    }

                    private void getRepos(String net) {
                        createService(getContext(), Constants.GitHub)
                                .getRepos(net)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<Repos>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(Repos repos) {
                                        watchbtn.setText(repos.getWatchers() + "");
                                        starbtn.setText(repos.getStargazers_count() + "");
                                        forkbtn.setText(repos.getForks_count() + "");
                                        issuebtn.setText(repos.getOpen_issues_count() + "");
                                    }
                                });
                    }
                });
    }

    private void CollectProject() {
        if (project != null) {
            project.saveOrUpdate("http=?", project.getHttp());
            Snackbar.make(cool, "已收藏", Snackbar.LENGTH_LONG).show();
        } else Snackbar.make(cool, "收藏失败", Snackbar.LENGTH_LONG).show();
    }


    private void DownloadSource() {
        /*
         * url 网址
         * 下载后的命名
         * 类型
         * https://codeload.github.com/esafirm/RxDownloader/zip/master
         */
        RxDownloader.getInstance(this)
                .download(Constants.GitHubSource + bundle.getString("author") + "/" + bundle.getString("project") + "/" + "zip/master", bundle.getString("project") + "-master.zip", "application/x-gzip")
                .subscribe(new Subscriber<String>() {
                               @Override
                               public void onStart() {
                                   super.onStart();
                                   Snackbar.make(cool, "已经开始", Snackbar.LENGTH_SHORT).show();
                               }

                               @Override
                               public void onCompleted() {
                        /*
                         * TODO 通知下载完成
                         */
                               }

                               @Override
                               public void onError(Throwable e) {
                                   Snackbar.make(cool, "下载失败", Snackbar.LENGTH_LONG).show();
                               }

                               @Override
                               public void onNext(final String s) {
                        /*
                         * TODO 系统通知栏下载位置
                         */
                                   Snackbar.make(cool, "已下载至" + s, Snackbar.LENGTH_LONG)
                                           .setAction("确定", new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   Intent it = getImageFileIntent(s);
                                                   startActivity(it);
                                               }
                                           })
                                           .show();
                               }
                           }
                );
    }

    private void share(String title, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, getTitle()));
    }

    private Intent getImageFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/x-gzip");
        return intent;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != 0) return;
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            DownloadSource();
        }
    }

    private void CheckPermission() {
        final String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int permissionCheck = ContextCompat.checkSelfPermission(this, permission);

        if (permissionCheck != PermissionChecker.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, 0);
            return;
        }
        DownloadSource();
    }
}


//

//

//
//    @Override
//    public void onBackPressedSupport() {
//        if (!fabOptions.isOpen())
//            super.onBackPressedSupport();
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (subscription != null && !subscription.isUnsubscribed())
//            subscription.unsubscribe();
//        if (subscription1 != null && !subscription1.isUnsubscribed())
//            subscription1.unsubscribe();
//        if (subscription2 != null && !subscription2.isUnsubscribed())
//            subscription2.unsubscribe();
//    }
//

//

//
//
