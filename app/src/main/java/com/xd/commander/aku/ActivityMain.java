package com.xd.commander.aku;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.regex.Pattern;
import butterknife.BindView;

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
    @BindView(R.id.img)
    View img;
    private String mLastQuery = "";
    private SharedPreferences.Editor sharedPreferences;
    private SharedPreferences prf;
    private View positionView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected Context getActivity() {
        return this;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        init();
    }

    private void init() {
        positionView= findViewById(R.id.v);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                positionView.setBackgroundColor(Color.TRANSPARENT);
                getWindow()
                        .getDecorView()
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                getWindow()
                        .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
        dealStatusBar();
        PgyCrashManager.register(this);
        sharedPreferences = getSharedPreferences("ahbottom", MODE_PRIVATE).edit();
        prf = getSharedPreferences("ahbottom", MODE_PRIVATE);
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
        if (prf.getInt("ah", 0) == 0)
            bottomNavigation.setDefaultBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        else
            bottomNavigation.setDefaultBackgroundColor(ContextCompat.getColor(this, R.color.color_Shenhei));
        bottomNavigation.setAccentColor(Color.parseColor("#1e89e7"));
        bottomNavigation.setInactiveColor(Color.parseColor("#979797"));

         //判断夜间模式换主题
         //根据模式切换searchView和tabLayout的主题
         //白昼、夜间

        if (prf.getInt("ah", 0) == 0) {
            floatingSearchView.setBackgroundColor(ContextCompat.getColor(this, R.color.search_view_day));
            tablayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), android.R.color.white));
            tablayout.setTabTextColors(ContextCompat.getColor(getContext(), R.color.color_Shenhei), ContextCompat.getColor(getContext(), R.color.colorPrimary));
        } else {
            floatingSearchView.setBackgroundColor(ContextCompat.getColor(this, R.color.search_view_night));
            tablayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            tablayout.setTabTextColors(ContextCompat.getColor(getContext(), android.R.color.white), ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        }
        tablayout.setupWithViewPager(viewpager);
        floatingSearchView.attachNavigationDrawerToMenuButton(drawer);

       //判断网络

        if (!isInternetCanDo(getContext()))
            Snackbar.make(coordinator, R.string.error_internet, Snackbar.LENGTH_SHORT).show();

        //监听点击搜索栏的动作，可提供给用户搜索前的参考items

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
                    int i = prf.getInt("ah", 0) == 0 ? 1 : 0;
                    sharedPreferences.putInt("ah", i).apply();
                    reload();
                }
            }
        });
        navView.setItemIconTintList(null);
    }

    /**
     * 设置翻页
     * @param viewPager 翻页器
     * @param i 页面指针
     */
    private void setupViewPager(ViewPager viewPager, int i) {
        ArrayList<Fragment> list0 = new ArrayList<>();
        ArrayList<Fragment> list1 = new ArrayList<>();
        ArrayList<Fragment> list2 = new ArrayList<>();
        ArrayList<Fragment> list3 = new ArrayList<>();
        list0.add(new FragmentAll().newInstance(1));
        list0.add(new FragmentAll().newInstance(2));
        list0.add(new FragmentAll().newInstance(3));
        list1.add(new FragmentSort().newInstance());
        list2.add(new FragmentCollect());
        list3.add(new FragmentOther());
        ArrayList<String> listtab0 = new ArrayList<>();
        listtab0.add("免费");
        listtab0.add("付费");
        listtab0.add("演示");
        ArrayList<String> listtab1 = new ArrayList<>();
        listtab1.add("分类");
        ArrayList<String> listtab2 = new ArrayList<>();
        listtab2.add("收藏");
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

        //viewpager切换fragment时，控制头部控件

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                setupViewPager(viewpager, position);
                if(position==0||position==1){
                        tablayout.setVisibility(View.VISIBLE);
                        floatingSearchView.setVisibility(View.VISIBLE);
                        img.setVisibility(View.VISIBLE);}
                else {
                    tablayout.setVisibility(View.GONE);
                    floatingSearchView.setVisibility(View.GONE);
                    img.setVisibility(View.GONE);
                }
                return true;
            }
        });
    }

    /**
     * 重写返回键
     */
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
        switch (item.getItemId()) {
            case R.id.nav_share:
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeCustomAnimation(this,
                        R.anim.activity_in, R.anim.activity_out);
                ActivityCompat.startActivity(this,
                        new Intent(this, ActivityLibrary.class), compat.toBundle());
                break;
            case R.id.nav_crash:
                PgyerDialog.setDialogTitleBackgroundColor("#1e89e7");
                PgyerDialog.setDialogTitleTextColor("#ffffff");
                PgyFeedback.getInstance().showDialog(ActivityMain.this);
                break;
            case R.id.author_item:
                show("一名即将分流的大四狗");
                break;
            case R.id.translater:
                show("一名刚刚解放的高三党");
                break;
            case R.id.viper:
                show("一名废寝忘食的老司机");
                break;
        }
        return true;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        floatingSearchView.setTranslationY(verticalOffset);
    }

    private void goToSearchActivity(String searchInfo) {
        Intent intent = new Intent(getContext(), ActivtyCategory.class);
        intent.putExtra("url", "search?q=" + searchInfo);
        intent.putExtra("category", searchInfo);
        intent.putExtra("what", "搜索结果");
        startActivity(intent);
    }

    /**
     * @param message
     * 处理来自Fragment中的SnackBar
     * 通过判断 message 的数据类型 分为两种模式
     * 一是，来自普通的通知消息，正常显示SnackerBar
     * 二是，来自收藏删除后的回调，更新显示还剩下的收藏个数
     */

    @Override
    public void show(String message) {
        if (isInteger(message)) {
            setCollectedCount(Integer.valueOf(message),false);
        } else Snackbar.make(coordinator, message, Snackbar.LENGTH_LONG).show();
    }

    /**
     * @param isDataAvailable
     * 网络变化监听
     */
    @Override
    public void onSync(boolean isDataAvailable) {
        count();
        if (isDataAvailable)
            show(getString(R.string.netisok));
        else
            show(getString(R.string.error_internet));
    }

    @Override
    public void onStart() {
        super.onStart();
        setCollectedCount(0,true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCollectedCount(0,true);
    }

    private boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 设置已收藏数
     * @param count 个数
     * @param ifCount 是否从数据库中计算
     */

    private void setCollectedCount(int count,Boolean ifCount){
        if(ifCount)
            count = DataSupport.findAll(Project.class).size();
        AHNotification notification = new AHNotification.Builder()
                .setText(count + "")
                .setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_Danhong))
                .setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                .build();
        bottomNavigation.setNotification(notification, 2);
    }
    private void dealStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = getStatusBarHeight();
            ViewGroup.LayoutParams lp = positionView.getLayoutParams();
            lp.height = statusBarHeight;
            positionView.setLayoutParams(lp);
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
