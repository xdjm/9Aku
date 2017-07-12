package com.xd.commander.aku;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xd.commander.aku.adapter.AdapterItem_library;
import com.xd.commander.aku.base.BaseActivity;
import com.xd.commander.aku.bean.Library;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class ActivityLibrary extends BaseActivity {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private  static    String[]  project =
            {
                    "CustomActivityOnCrash",
                    "CarouselView",
                    "MarqueeView",
                    "CircleImageView",
                    "overscroll-decor",
                    "WaveSideBar",
                    "Fragmentation",
                    "FabOptions"
                    ,"HtmlTextView"
                    ,"Retrofit"
                    ,"RxJava"
                    ,"RxDownloader"
                    ,"GSON"
                    ,"Glide"
                    ,"BRVAH"
                    ,"jsoup"
                    ,"LitePal"
                    ,"Butter Knife"
            };
    private  static String[]  author =
            {
                    "Ereza",
                    "sayyam",
                    "sfsheng0322",
                    "hdodenhof",
                    "EverythingMe",
                    "Solartisan",
                    "YoKeyword",
                    "JoaquimLey"
                    ,"SufficientlySecure"
                    ,"square"
                    ,"ReactiveX"
                    ,"esafirm"
                    ,"google"
                    ,"bumptech"
                    ,"CymChad"
                    ,"jhy"
                    ,"郭霖"
                    ,"JakeWharton"
            };
    private  static String[] http =
            {
                    "https://android-arsenal.com/details/1/2179",
                    "https://android-arsenal.com/details/1/3289",
                    "https://android-arsenal.com/details/1/3671",
                    "https://android-arsenal.com/details/1/1549",
                    "https://android-arsenal.com/details/1/2872",
                    "https://android-arsenal.com/details/1/4017",
                    "https://android-arsenal.com/details/1/5937",
                   "https://android-arsenal.com/details/1/4734"
                    ,"https://android-arsenal.com/details/1/476"
                    ,"https://android-arsenal.com/details/1/401"
                    ,"https://android-arsenal.com/details/1/168"
                    ,"https://android-arsenal.com/details/1/4601"
                    ,"https://android-arsenal.com/details/1/229"
                    ,"https://android-arsenal.com/details/1/806"
                    ,"https://android-arsenal.com/details/1/3644"
                    ,"https://android-arsenal.com/details/1/191"
                    ,"https://github.com/LitePalFramework/LitePal"
                    ,"https://android-arsenal.com/details/1/131"
            };
    private static String[] detail =
            {
                    "This library allows launching a custom activity when the app crashes, instead of showing the hated \"Unfortunately, X has stopped\" dialog.",
                    "A simple yet flexible library to add carousel view in your android application.",
                    "俗名：垂直跑马灯 \n" +
                            "学名：垂直翻页公告",
                    "A fast circular ImageView perfect for profile images. This is based on RoundedImageView from Vince Mi which itself is based on techniques recommended by Romain Guy.",
                    "The library provides an iOS-like over-scrolling effect applicable over almost all Android native scrollable views. It is also built to allow for very easy adaptation to support custom views.",
                    "一个快速跳跃分组的侧边栏控件，示例中配合RecyclerView实现。",
                    "Fragmentation is a powerful library managing Fragment for Android.\n" +
                            "\n" +
                            "It is designed for \"Single Activity + Multi-Fragments\" and \"Multi-FragmentActivities + Multi-Fragments\" architecture to simplify development process.",
                    "A multi-functional FAB component with customizable options."
                    ,"HtmlTextView is an extended TextView component for Android, which can load HTML and converts it into Spannable for displaying it. It is a replacement for usage of the WebView component, which behaves strange on some Android versions, flickers while loading, etc."
                    ,"Type-safe REST client for Android and Java by Square, Inc."
                    ,"RxJava is a Java VM implementation of Reactive Extensions: a library for composing asynchronous and event-based programs by using observable sequences.\n" +
                    "\n" +
                    "It extends the observer pattern to support sequences of data/events and adds operators that allow you to compose sequences together declaratively while abstracting away concerns vector_bmnavi_about things like low-level threading, synchronization, thread-safety and concurrent data structures."
                    ,"An Rx wrapper for Download Manager in Android"
                    ,"Gson is a Java library that can be used to convert Java Objects into their JSON representation. It can also be used to convert a JSON string to an equivalent Java object. Gson can work with arbitrary Java objects including pre-existing objects that you do not have source-code of."
                    ,"Glide is fast and efficient image loading library for Android that wraps image downloading, resizing, memory and disk caching, and bitmap recycling into one simple and easy to use interface. By default, Glide includes an implementation for fetching images over http based on Google's Volley project for fast, parallelized network operations on Android."
                    ,"Powerful and flexible RecyclerAdapter."
                    ,"Library for working with real-world HTML. It provides a very convenient API for extracting and manipulating data, using the best of DOM, CSS, and jquery-like methods."
                    ,"LitePal is an open source Android library that allows developers to use SQLite database extremely easy. You can finish most of the database operations without writing even a SQL statement, including create or upgrade tables, crud operations, aggregate functions, etc. The setup of LitePal is quite simple as well, you can integrate it into your project in less than 5 minutes."
                    ,"Field and method binding for Android views which uses annotation processing to generate boilerplate code for you."
            };
    @Override
    protected int getLayoutId() {
        return R.layout.item_recycelerview;
    }

    @Override
    protected Context getActivity() {
        return ActivityLibrary.this;
    }

    @Override
    protected void init(final Bundle savedInstanceState) {
        AdapterItem_library adapterItem_library = new AdapterItem_library(list());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapterItem_library);
        adapterItem_library.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(position==16){
//                String url = ((Library)adapter.getData().get(position)).getHttp();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(http[position]);
                intent.setData(uri);
                startActivity(intent);
                }
                else {
                    Intent intent = new Intent(view.getContext(), ActivityDetail.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("project",project[position]);
                    bundle.putString("http", http[position]);
                    bundle.putString("author", author[position]);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        View view_head = getLayoutInflater().inflate(R.layout.fragment_sort_head,(ViewGroup)mRecyclerView.getParent(),false);
        ImageView i =(ImageView)view_head.findViewById(R.id.img);
        Glide.with(getContext()).load("http://osbf25z0k.bkt.clouddn.com/opensourse.png").centerCrop().into(i);
        adapterItem_library.addHeaderView(view_head);
        OverScrollDecoratorHelper.setUpOverScroll(mRecyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
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
    private List<Library> list() {
        List<Library> list = new ArrayList<>();
        for (int i = 0; i <author.length; i++) {
            Library library = new Library(author[i],project[i],http[i],detail[i]);
            list.add(library);
        }
        return list;
    }

}
