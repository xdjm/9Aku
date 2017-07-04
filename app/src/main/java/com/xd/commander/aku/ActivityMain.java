package com.xd.commander.aku;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import com.xd.commander.aku.base.BaseActivity;
import com.xd.commander.aku.base.BaseFragment;
import com.xd.commander.aku.bean.Project;
import com.xd.commander.aku.fragment.first_sort.FragmentFirstAll;
import com.xd.commander.aku.fragment.fourth_other.FragmentFourthAll;
import com.xd.commander.aku.fragment.second_all.FragmentSecondAll;
import com.xd.commander.aku.fragment.third_collect.FragmentThirdAll;
import com.xd.commander.aku.interf.OnNetChangeListener;
import com.xd.commander.aku.interf.SnackBarShow;
import com.xd.commander.aku.util.ThemeUtil;
import com.xd.commander.aku.view.BottomBar;
import com.xd.commander.aku.view.BottomBarTab;
import org.litepal.crud.DataSupport;
import java.util.regex.Pattern;
import me.yokeyword.fragmentation.SupportFragment;
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
public class ActivityMain extends BaseActivity implements BaseFragment.OnBackToFirstListener,OnNetChangeListener,SnackBarShow {
    private BottomBar mBottomBar;
    private final SupportFragment[] mFragments = new SupportFragment[4];

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
        SharedPreferences sharedPreferences = getSharedPreferences("theme", Context.MODE_ENABLE_WRITE_AHEAD_LOGGING);
        SupportFragment firstFragment = findFragment(FragmentFirstAll.class);
        if (firstFragment == null) {
            mFragments[0] = FragmentFirstAll.newInstance();
            mFragments[1] = FragmentSecondAll.newInstance();
            mFragments[2] = FragmentThirdAll.newInstance();
            mFragments[3] = FragmentFourthAll.newInstance();

            loadMultipleRootFragment(R.id.fl_container, 0,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2],
                    mFragments[3]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.findFragmentByTag()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[0] = firstFragment;
            mFragments[1] = findFragment(FragmentSecondAll.class);
            mFragments[2] = findFragment(FragmentThirdAll.class);
            mFragments[3] = findFragment(FragmentFourthAll.class);
        }
        //添加图标
        mBottomBar = (BottomBar) findViewById(R.id.bottomBar);
        mBottomBar.mTabLayout.setBackgroundColor(sharedPreferences.getInt("theme",1)==1?Color.WHITE:Color.GRAY);
        mBottomBar.addItem(new BottomBarTab(this, R.drawable.vector_bmnavi_sort))
                .addItem(new BottomBarTab(this, R.drawable.vector_bmnavi_all))
                .addItem(new BottomBarTab(this, R.drawable.vector_bmnavi_collect))
                .addItem(new BottomBarTab(this, R.drawable.vector_bmnavi_about));
        mBottomBar.getItem(2).setUnreadCount(getCollectCount());
        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
                                                @Override
                                                public void onTabSelected(int position, int prePosition) {
                                                    showHideFragment(mFragments[position], mFragments[prePosition]);
                                                }

                                                @Override
                                                public void onTabUnselected(int position) {

                                                }

                                                @Override
                                                public void onTabReselected(int position) {
                                                }
                                            });
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
    }

    private void goToSearchActivity(String searchInfo) {
        Intent intent = new Intent(getContext(), ActivtyCategory.class);
        intent.putExtra("url", "search?q=" + searchInfo);
        intent.putExtra("category", searchInfo);
        intent.putExtra("what", "搜索");
        startActivity(intent);
    }
    private boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    @Override
    public void onBackToFirstFragment() {
        mBottomBar.setCurrentItem(0);
    }

    @Override
    public void onSync(boolean isDataAvailable) {
        ThemeUtil.switchAppTheme(this);
        reload();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBottomBar.getItem(2).setUnreadCount(getCollectCount());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBottomBar.getItem(2).setUnreadCount(getCollectCount());
    }
    private int getCollectCount(){
        return DataSupport.findAll(Project.class).size();
    }

    @Override
    public void show(String message) {
        if(isInteger(message))
            mBottomBar.getItem(2).setUnreadCount(getCollectCount());
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(Intent.ACTION_MAIN)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addCategory(Intent.CATEGORY_HOME));
    }
}
