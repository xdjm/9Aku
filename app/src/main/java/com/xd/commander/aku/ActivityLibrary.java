package com.xd.commander.aku;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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

    private String[] project =
            {
                    "FabOptions"
                    ,"MarkdownView"
                    ,"HtmlTextView"
                    ,"AHBottomNavigation"
                    ,"Retrofit"
                    ,"RxJava"
                    ,"RxDownloader"
                    ,"GSON"
                    ,"Glide"
                    ,"BaseRecyclerViewAdapterHelper"
                    ,"jsoup"
                    ,"LitePal"
                    ,"Butter Knife"
                    ,"QingtingBannerView"
            };
    private String[] author =
            {
                   "JoaquimLey"
                    ,"tiagohm"
                    ,"SufficientlySecure"
                    ,"aurelhubert"
                    ,"square"
                    ,"ReactiveX"
                    ,"esafirm"
                    ,"google"
                    ,"bumptech"
                    ,"CymChad"
                    ,"jhy"
                    ,"郭霖"
                    ,"JakeWharton"
                    ,"jcmore2"


            };
    private String[] http =
            {
                   "https://android-arsenal.com/details/1/4734"
                    ,"https://android-arsenal.com/details/1/5386"
                    ,"https://android-arsenal.com/details/1/476"
                    ,"https://android-arsenal.com/details/1/3295"
                    ,"https://android-arsenal.com/details/1/401"
                    ,"https://android-arsenal.com/details/1/168"
                    ,"https://android-arsenal.com/details/1/4601"
                    ,"https://android-arsenal.com/details/1/229"
                    ,"https://android-arsenal.com/details/1/806"
                    ,"https://android-arsenal.com/details/1/3644"
                    ,"https://android-arsenal.com/details/1/191"
                    ,"https://github.com/LitePalFramework/LitePal"
                    ,"https://android-arsenal.com/details/1/131"
                    ,"https://android-arsenal.com/details/1/3712"
            };
    private String[] detail =
            {
                    "A multi-functional FAB component with customizable options."
                    ,"Android library to display markdown text."
                    ,"HtmlTextView is an extended TextView component for Android, which can load HTML and converts it into Spannable for displaying it. It is a replacement for usage of the WebView component, which behaves strange on some Android versions, flickers while loading, etc."
                    ,"Library to implement the Bottom Navigation component from Material Design guidelines."
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
                    ,"Carousel banner view."

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
    protected void init(Bundle savedInstanceState) {
        AdapterItem_library adapterItem_library = new AdapterItem_library(list());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapterItem_library);
        adapterItem_library.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String url = ((Library)adapter.getData().get(position)).getHttp();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(url);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        OverScrollDecoratorHelper.setUpOverScroll(mRecyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
    }
    private List<Library> list() {
        List<Library> list = new ArrayList<>();
        for (int i = 0; i <14; i++) {
            Library library = new Library(author[i],project[i],http[i],detail[i]);
            list.add(library);
        }
        return list;
    }

}
