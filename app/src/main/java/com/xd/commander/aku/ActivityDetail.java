package com.xd.commander.aku;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.esafirm.rxdownloader.RxDownloader;
import com.joaquimley.faboptions.FabOptions;
import com.xd.commander.aku.base.BaseActivity;
import com.xd.commander.aku.bean.Detail;
import com.xd.commander.aku.bean.GitHubUserInfo;
import com.xd.commander.aku.bean.Project;
import com.xd.commander.aku.constants.Constants;
import com.xd.commander.aku.util.BlurTransformation;
import com.xd.commander.aku.util.GlideCircleTransform;
import com.xd.commander.aku.util.MarkdownStyle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.tiagohm.markdownview.MarkdownView;
import butterknife.BindView;
import okhttp3.ResponseBody;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.support.design.widget.BottomSheetBehavior.STATE_EXPANDED;
import static com.xd.commander.aku.api.RetrofitHttp.createService;
import static com.xd.commander.aku.util.HanHua.toHanHua;
import static com.xd.commander.aku.util.TrueProjectName.trueProject;
/**
 * Created by Administrator on 2017/3/25.
 */
public class ActivityDetail extends BaseActivity {
    @BindView(R.id.tv_dt_1)
    TextView tvDt1;
    @BindView(R.id.tv_dd_1)
    TextView tvDd1;
    @BindView(R.id.tv_dt_2)
    TextView tvDt2;
    @BindView(R.id.tv_dd_2)
    TextView tvDd2;
    @BindView(R.id.markdown)
    MarkdownView markdown;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.backdrop)
    ImageView backdrop;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.publicrepos)
    TextView publicRepo;
    @BindView(R.id.publicgists)
    TextView publicGist;
    @BindView(R.id.action_button)
    FabOptions fabOptions;
    @BindView(R.id.watchbtn)
    RadioButton watchBtn;
    @BindView(R.id.starbtn)
    RadioButton starBtn;
    @BindView(R.id.forkbtn)
    RadioButton forkBtn;
    @BindView(R.id.issuebtn)
    RadioButton issueBtn;
    @BindView(R.id.vs_progressbar)
    ViewStub viewStub;
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;
    private Subscription subscription;
    private Subscription subscription1;
    private Subscription subscription2;
    private Document doc;
    private BottomSheetBehavior behavior;
    private String star;
    final private List<Integer> list = new ArrayList<>();
    private Bundle bundle;
    private Project project;
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
        //设置透明状态栏
        if(Build.VERSION.SDK_INT>=19)
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bundle = getIntent().getExtras();
        project = new Project(bundle.getString("project"), bundle.getString("http"),
                bundle.getString("author"), bundle.getString("time"), bundle.getString("tag"),
                bundle.getString("desc"), bundle.getString("newinfo"), bundle.getString("category"));
        behavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        collapsingToolbarLayout.setTitle(bundle.getString("author"));
        //设置 fabOptions
        fabOptions.setFabColor(android.R.color.holo_red_dark);
        fabOptions.setBackgroundColor(android.R.color.holo_red_dark);
        fabOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    //收藏功能
                    case R.id.collect:
                        CollectProject();
                        break;
                    //详细信息
                    case R.id.details:
                        if (behavior.getState() == STATE_EXPANDED)
                            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        else behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;
                    //下载功能
                    case R.id.download:
                        CheckPermission();
                        break;
                    //分享功能
                    case R.id.share:
                        share(getString(R.string.share_info),bundle.getString("project")+bundle.getString("http"));
                        break;
                }
            }
        });
        //请求作者信息
        getAuthor(bundle.getString("author"));
        //请求Markdown文档信息
        getMarkdown(bundle.getString("author"),trueProject(bundle.getString("project")));
        //请求详细内容
        getData(bundle.getString("http"));
    }

    private void getData(String http) {
        subscription=createService(getContext(), Constants.URL).getProjectDetail(http)
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<ResponseBody, Detail>() {
                    @Override
                    public Detail call(ResponseBody responseBody) {
                        StringBuilder dt_1 = new StringBuilder();
                        StringBuilder dd_1 = new StringBuilder();
                        StringBuilder dt_2 = new StringBuilder();
                        StringBuilder dd_2 = new StringBuilder();
                        try {
                            doc = Jsoup.parse(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Elements dtNodes_0 = doc.select("div[class=col-md-4]")
                                .get(0)
                                .select("dl[class=dl-horizontal]").select("dt");
                        Elements ddNodes_0 = doc.select("div[class=col-md-4]")
                                .get(0)
                                .select("dl[class=dl-horizontal]").select("dd");
                        Elements dtNodes_1 = doc.select("div[class=col-md-4]")
                                .get(1)
                                .select("dl[class=dl-horizontal]").select("dt");
                        Elements ddNodes_1 = doc.select("div[class=col-md-4]")
                                .get(1)
                                .select("dl[class=dl-horizontal]").select("dd");
                        //Elements markdownNodes = doc.select("div#projectDesc");
                        try {
                            Pattern pattern1 = Pattern.compile("(stargazers:)\\d+(,watchers:)\\d+(,forks:)\\d+(,issues:)\\d+");
                            Pattern pattern2 = Pattern.compile("\\d+");
                            Matcher matcher1 = pattern1.matcher(doc.html());
                            while (matcher1.find())
                                star = matcher1.group();
                            Matcher matcher2 = pattern2.matcher(star);
                            while (matcher2.find())
                                list.add(Integer.valueOf(matcher2.group()));
                        } catch (Exception e) {
                            Snackbar.make(coordinator, "无用户", Snackbar.LENGTH_SHORT).show();
                            list.add(0);
                            list.add(0);
                            list.add(0);
                            list.add(0);
                        }
                        for (Element ddNode : ddNodes_0) {
                            dd_1.append(ddNode.text()).append("\n");
                        }
                        for (Element dtNode : dtNodes_0) {
                            dt_1.append(toHanHua(dtNode.text())).append("\n");
                        }
                        for (Element ddNode : ddNodes_1) {
                            dd_2.append(ddNode.text()).append("\n");
                        }
                        for (Element dtNode : dtNodes_1) {
                            if (!dtNode.text().equals(""))
                                dt_2.append(toHanHua(dtNode.text())).append("\n");
                        }
                        return new Detail(
                                dt_1.toString()
                                , dd_1.toString()
                                , dt_2.toString()
                                , dd_2.toString()
                                , null, bundle.getString("http"), list.get(1), list.get(0), list.get(2), list.get(3));
                    }
                })
                .onErrorReturn(new Func1<Throwable, Detail>() {
                    @Override
                    public Detail call(Throwable throwable) {
                        return new Detail("无", "无", "无", "无", "无", "无", 0, 0, 0, 0);

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Snackbar.make(coordinator, throwable.toString(), Snackbar.LENGTH_SHORT).show();
                    }
                })
                .subscribe(new Action1<Detail>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void call(Detail detail) {
                        tvDt1.setText(detail.getDt_0());
                        tvDd1.setText(detail.getDd_0());
                        tvDt2.setText(detail.getDt_1());
                        tvDd2.setText(detail.getDd_1());
                        watchBtn.setText("" + detail.getWatch());
                        starBtn.setText("" + detail.getStar());
                        forkBtn.setText("" + detail.getFork());
                        issueBtn.setText("" + detail.getIssue());
                    }
                });
    }

    private void getMarkdown(String author,String repos){
        viewStub.inflate();
        subscription1=createService(getContext(),Constants.GitHubMarkdown)
                .getMarkdown(author,repos)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.progressbar);
                        linearLayout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        markdown.addStyleSheet(new MarkdownStyle());
                        try {
                            markdown.loadMarkdown(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    private void getAuthor(String name) {
        subscription2=createService(getContext(), Constants.GitHub)
                .getUserInfo(name)
                .onErrorReturn(new Func1<Throwable, GitHubUserInfo>() {
                    @Override
                    public GitHubUserInfo call(Throwable throwable) {
                        return new GitHubUserInfo();
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GitHubUserInfo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(GitHubUserInfo gitHubUserInfo) {
                        location.setText("来自 " + gitHubUserInfo.getLocation() + " / 公司 " + gitHubUserInfo.getCompany());
                        publicRepo.setText("公开仓库 " + gitHubUserInfo.getPublic_repos() + " / 公开项目 " + gitHubUserInfo.getPublic_gists() + " ");
                        publicGist.setText("追随 " + gitHubUserInfo.getFollowers() + " / 跟随 " + gitHubUserInfo.getFollowing());
                        if(Build.VERSION.SDK_INT ==19)
                        {
                            Glide.with(ActivityDetail.this)
                                    .load(gitHubUserInfo.getAvatar_url())
                                    .override(100,100)
                                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                                    .priority(Priority.HIGH)
                                    .error(R.drawable.ic_wronginfo)
                                    .transform(new GlideCircleTransform(getContext()))
                                    .into(fab);
                        }
                        else {
                        Glide.with(ActivityDetail.this)
                                .load(gitHubUserInfo.getAvatar_url())
                                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                                .priority(Priority.HIGH)
                                .error(R.drawable.ic_wronginfo)
                                .transform(new GlideCircleTransform(getContext()))
                                .into(fab);}

                        Glide.with(ActivityDetail.this)
                                .load(gitHubUserInfo.getAvatar_url())
                                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                                .priority(Priority.LOW)
                                .error(R.drawable.ic_wronginfo)
                                .transform(new BlurTransformation(getContext(), 30))
                                .into(backdrop);
                    }
                });
    }

    private void CollectProject() {
        if (project != null){
            project.saveOrUpdate("http=?", project.getHttp());
            Snackbar.make(coordinator, "已收藏", Snackbar.LENGTH_LONG).show();}
        else Snackbar.make(coordinator, "收藏失败", Snackbar.LENGTH_LONG).show();
    }

    private void DownloadSource() {
        /*
         * url 网址
         * 下载后的命名
         * 类型
         * https://codeload.github.com/esafirm/RxDownloader/zip/master
         */
        RxDownloader.getInstance(this)
                .download("https://codeload.github.com/" + bundle.getString("author") + "/" + bundle.getString("project") + "/" + "zip/master", bundle.getString("project") + "-master.zip", "application/x-gzip")
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        Snackbar.make(coordinator, "已经开始", Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCompleted() {
                        /*
                         * TODO 通知下载完成
                         */
                    }

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(coordinator, "下载失败", Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(final String s) {
                        /*
                         * TODO 系统通知栏下载位置
                         */
                        Snackbar.make(coordinator, "已下载至" + s, Snackbar.LENGTH_LONG)
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

    private void CheckPermission() {
        final String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int permissionCheck = ContextCompat.checkSelfPermission(this, permission);

        if (permissionCheck != PermissionChecker.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, 0);
            return;
        }
        DownloadSource();
    }

    @Override
    public void onBackPressedSupport() {
        if (!fabOptions.isOpen())
            super.onBackPressedSupport();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(subscription!=null&&!subscription.isUnsubscribed())
            subscription.unsubscribe();
        if(subscription1!=null&&!subscription1.isUnsubscribed())
            subscription1.unsubscribe();
        if(subscription2!=null&&!subscription2.isUnsubscribed())
            subscription2.unsubscribe();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != 0) return;
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            DownloadSource();
        }
    }
    private Intent getImageFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/x-gzip");
        return intent;
    }
    private void share(String title,String text){
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT,text);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, getTitle()));
    }
}
