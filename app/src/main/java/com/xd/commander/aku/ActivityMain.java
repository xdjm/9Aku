package com.xd.commander.aku;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.feedback.PgyFeedback;
import com.pgyersdk.views.PgyerDialog;
import com.xd.commander.aku.adapter.AdapterViewPaper;
import com.xd.commander.aku.base.BaseActivity;
import com.xd.commander.aku.bean.Project;
import com.xd.commander.aku.fragment.FragmentAll;
import com.xd.commander.aku.fragment.FragmentCollect;
import com.xd.commander.aku.fragment.FragmentOther;
import com.xd.commander.aku.fragment.FragmentSort;
import com.xd.commander.aku.interf.SnackerBarShow;
import com.xd.commander.aku.util.ThemeUtil;


import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import me.wangyuwei.banner.BannerEntity;
import me.wangyuwei.banner.BannerView;
import me.wangyuwei.banner.OnBannerClickListener;

import static com.xd.commander.aku.util.IsInternet.isInternetCanDo;

/***************************************************************
 * //         ,--^----------,--------,-----,-------^--,
 * //        | |||||||||   `--------'     |          O
 * //        `+---------------------------^----------|
 * //        `\_,-------, _________________________|
 * //        / XXXXXX /`|     /
 * //        / XXXXXX /  `\   /
 * //        / XXXXXX /\______(
 * //        / XXXXXX /
 * //        / XXXXXX /
 * //        (________(
 * //        `------'
 * //
 * //        ╔♂╗┏┯┓┏┯┓┏┯┓┏┯┓╔♀╗
 * //        ┃★┃┠忠┨┠诚┨┠于┨┠党┨┃☆┃
 * //        ┃☆┃┗┷┛┗┷┛┗┷┛┗┷┛┃★┃
 * //        ╚♂╝↘*≡热≡爱≡人≡民≡*↙╚♀╝
 ***************************************************************/

public class ActivityMain extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener
        , AppBarLayout.OnOffsetChangedListener
        , SnackerBarShow {
    @BindView(R.id.appbarlayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.floating_search_view)
    FloatingSearchView floatingSearchView;
    @BindView(R.id.banner_view)
    BannerView bannerView;
    @BindView(R.id.img)
    View v;
    private String mLastQuery = "";
    private AppBarLayout.LayoutParams layoutParams;
    private AHNotification notification;
    private List<Project> project;
    private SharedPreferences.Editor sharedPreferences;
    private SharedPreferences prf;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected Context getActivity() {
        return ActivityMain.this;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        init();
    }

    private void init() {
        PgyCrashManager.register(this);
        sharedPreferences = getSharedPreferences("ahbottom",MODE_PRIVATE).edit();
        prf = getSharedPreferences("ahbottom",MODE_PRIVATE);
        layoutParams = (AppBarLayout.LayoutParams) v.getLayoutParams();
        appBarLayout.addOnOffsetChangedListener(this);
        navView.setNavigationItemSelectedListener(this);
        if (viewpager != null) {
            setupViewPager(viewpager, 0);
        }
        //添加图标
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(null, R.drawable.vector_bmnavi_all);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(null, R.drawable.vector_bmnavi_sort);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(null, R.drawable.vector_bmnavi_collect);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(null, R.drawable.vector_bmnavi_about);
        // Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);
        if(prf.getInt("ah",0)==0)
        bottomNavigation.setDefaultBackgroundColor(ContextCompat.getColor(this,R.color.colorAccent));
        else bottomNavigation.setDefaultBackgroundColor(ContextCompat.getColor(this,R.color.color_Shenhei));
        bottomNavigation.setAccentColor(Color.parseColor("#1e89e7"));
        bottomNavigation.setInactiveColor(Color.parseColor("#979797"));
        project = DataSupport.findAll(Project.class);
        notification = new AHNotification.Builder()
                .setText(project.size()+"")
                .setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_Danhong))
                .setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                .build();
        bottomNavigation.setNotification(notification, 2);

        // Add TabItems
        if(prf.getInt("ah",0)==0) {
            tablayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), android.R.color.white));
            tablayout.setTabTextColors(ContextCompat.getColor(getContext(), R.color.search_view_night),ContextCompat.getColor(getContext(), R.color.colorPrimary));}
        else {tablayout.setTabTextColors(ContextCompat.getColor(getContext(), android.R.color.white),ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            tablayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));}
        tablayout.setupWithViewPager(viewpager);
        /*
         * 排序
         */
        if (!isInternetCanDo(getContext()))
            Snackbar.make(coordinator, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
        if(prf.getInt("ah",0)==0)
        floatingSearchView.setBackgroundColor(ContextCompat.getColor(this,R.color.search_view_day));
        else floatingSearchView.setBackgroundColor(ContextCompat.getColor(this,R.color.search_view_night));
        floatingSearchView.attachNavigationDrawerToMenuButton(drawer);
        /*
         *监听点击搜索栏的动作，可提供给用户搜索前的参考items
         */
        floatingSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
            }

            @Override
            public void onFocusCleared() {
                floatingSearchView.setSearchBarTitle(mLastQuery);
            }
        });
        floatingSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {
                mLastQuery = searchSuggestion.getBody();
                if (!mLastQuery.equals(""))
                    goToSearchActivity(mLastQuery);
            }

            @Override
            public void onSearchAction(String query) {
                mLastQuery = query;
                if (!mLastQuery.equals(""))
                    goToSearchActivity(mLastQuery);
            }
        });
        floatingSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.action_change_colors) {
                    ThemeUtil.switchAppTheme(ActivityMain.this);
                    int i =prf.getInt("ah",0)==0?1:0;
                    sharedPreferences.putInt("ah",i).apply();
                    reload();
            }
        }});
        final List<BannerEntity> entities = new ArrayList<>();
//        Banner[] banners=new Gson().fromJson(getBanner(),Banner[].class);
        for(int i=0;i<=2;i++){
            BannerEntity entity = new BannerEntity();
            entity.imageUrl = "http://upload-images.jianshu.io/upload_images/3764374-ed2fe1a918d38bdf.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/375/h/300";
            entity.title = "记一次旅行:越南芽庄";
            entities.add(entity);
        }
        bannerView.setEntities(entities);
        bannerView.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void onClick(int position) {
                Snackbar.make(coordinator, position + "=> " + entities.get(position).title, Snackbar.LENGTH_SHORT).show();
            }
        });
        navView.setItemIconTintList(null);
    }

    private void setupViewPager(ViewPager viewPager, int i) {
        ArrayList<Fragment> list0 = new ArrayList<>();
        ArrayList<Fragment> list1 = new ArrayList<>();
        ArrayList<Fragment> list2 = new ArrayList<>();
        ArrayList<Fragment> list3 = new ArrayList<>();
        list0.add(new FragmentAll().newInstance(1));
        list0.add(new FragmentAll().newInstance(2));
        list0.add(new FragmentAll().newInstance(3));
        list1.add(new FragmentSort().newInstance(1));
        list1.add(new FragmentSort().newInstance(2));
        list1.add(new FragmentSort().newInstance(3));
        list2.add(new FragmentCollect());
        list3.add(new FragmentOther());
        ArrayList<String> listtab0 = new ArrayList<>();
        listtab0.add("免费");
        listtab0.add("付费");
        listtab0.add("演示");
        ArrayList<String> listtab1 = new ArrayList<>();
        listtab1.add("免费");
        listtab1.add("付费");
        listtab1.add("演示");
        ArrayList<String> listtab2 = new ArrayList<>();
        listtab2.add("未分类");
        ArrayList<String> listtab3 = new ArrayList<>();
        listtab3.add("设置");
        AdapterViewPaper fragmentDreamAdapter0 = new AdapterViewPaper(getSupportFragmentManager(), list0, listtab0);
        AdapterViewPaper fragmentDreamAdapter1 = new AdapterViewPaper(getSupportFragmentManager(), list1, listtab1);
        AdapterViewPaper fragmentDreamAdapter2 = new AdapterViewPaper(getSupportFragmentManager(), list2, listtab2);
        AdapterViewPaper fragmentDreamAdapter3 = new AdapterViewPaper(getSupportFragmentManager(), list3, listtab3);
        viewPager.removeAllViews();
        viewPager.removeAllViewsInLayout();
        if (i == 0) {
            viewPager.setAdapter(fragmentDreamAdapter0);
        } else if (i == 1) {
            viewPager.setAdapter(fragmentDreamAdapter1);
        } else if (i == 2) {
            viewPager.setAdapter(fragmentDreamAdapter2);
        } else viewPager.setAdapter(fragmentDreamAdapter3);
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                setupViewPager(viewpager, position);

                switch (position) {
                    case 0:
                        bannerView.setVisibility(View.GONE);
                        layoutParams.height = 168;
                        layoutParams.width = AppBarLayout.LayoutParams.MATCH_PARENT;
                        v.setLayoutParams(layoutParams);
                        tablayout.setVisibility(View.VISIBLE);
                        floatingSearchView.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        bannerView.setVisibility(View.GONE);
                        layoutParams.height = 168;
                        layoutParams.width = AppBarLayout.LayoutParams.MATCH_PARENT;
                        v.setLayoutParams(layoutParams);
                        tablayout.setVisibility(View.VISIBLE);
                        floatingSearchView.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        bannerView.setVisibility(View.VISIBLE);
                        layoutParams.height = 0;
                        layoutParams.width = AppBarLayout.LayoutParams.MATCH_PARENT;
                        v.setLayoutParams(layoutParams);
                        tablayout.setVisibility(View.VISIBLE);
                        floatingSearchView.setVisibility(View.GONE);
                        break;
                    case 3:
                        bannerView.setVisibility(View.GONE);
                        layoutParams.height = 0;
                        layoutParams.width = AppBarLayout.LayoutParams.MATCH_PARENT;
                        v.setLayoutParams(layoutParams);
                        floatingSearchView.setVisibility(View.GONE);
                        tablayout.setVisibility(View.GONE);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START) || floatingSearchView.isSearchBarFocused()) {
            drawer.closeDrawer(GravityCompat.START);
            floatingSearchView.setSearchFocused(false);
        } else {
            startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME));
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        switch(item.getItemId())
        {
            case R.id.nav_share:
                startActivity(new Intent(this,ActivityLibrary.class));
                break;
            case R.id.nav_crash:
                PgyerDialog.setDialogTitleBackgroundColor("#1e89e7");
                PgyerDialog.setDialogTitleTextColor("#ffffff");
                PgyFeedback.getInstance().showDialog(ActivityMain.this);
                // 设置顶部导航栏和底部bar的颜色
                break;
            case R.id.author_item:
                Toast.makeText(getContext(),"一条临近毕业的大四狗",Toast.LENGTH_SHORT).show();
                break;
            case R.id.translater:
                Toast.makeText(getContext(),"一名尚未解放的高三党",Toast.LENGTH_SHORT).show();
                break;
            case R.id.viper:
                Toast.makeText(getContext(),"一名非常v5的老司机",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        floatingSearchView.setTranslationY(verticalOffset);
    }

    private void goToSearchActivity(String searchinfo) {
        Intent intent = new Intent(getContext(), ActivtyCategory.class);
        intent.putExtra("url", "search?q=" + searchinfo);
        intent.putExtra("category", searchinfo);
        intent.putExtra("what", "搜索结果");
        startActivity(intent);
    }

    /**
     * @param message 显示tag信息
     */
    @Override
    public void show(String message) {
        if(isInteger(message)) {
            notification = new AHNotification.Builder()
                    .setText(message)
                    .setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_Danhong))
                    .setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                    .build();
        bottomNavigation.setNotification(notification, 2);
        }
        else Snackbar.make(coordinator, message, Snackbar.LENGTH_LONG).show();
    }

    /**
     * @param isDataAvailable 显示网络情况
     */
    @Override
    public void onSync(boolean isDataAvailable) {
        count();
        if (isDataAvailable)
            Snackbar.make(coordinator, R.string.netisok, Snackbar.LENGTH_SHORT).show();
        else
            Snackbar.make(coordinator, R.string.error_internet, Snackbar.LENGTH_SHORT).show();
    }
    @Override
    public void onStart() {
        super.onStart();
        project = DataSupport.findAll(Project.class);
        notification = new AHNotification.Builder()
                .setText(project.size()+"")
                .setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_Danhong))
                .setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                .build();
        bottomNavigation.setNotification(notification, 2);
        bannerView.startAutoScroll();
    }

    @Override
    protected void onResume() {
        super.onResume();
        project = DataSupport.findAll(Project.class);
        notification = new AHNotification.Builder()
                .setText(project.size()+"")
                .setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_Danhong))
                .setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                .build();
            bottomNavigation.setNotification(notification, 2);
    }

    @Override
    public void onStop() {
        bannerView.stopAutoScroll();
        super.onStop();
    }
    private  boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
