package com.xd.commander.aku.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.xd.commander.aku.util.IsInternet.isInternetCanDo;

/**
 * Created by Administrator on 2017/4/12.
 */

public class RetrofitHttp {

    private final static String TAG = "factory";

    public static RetrofitApi createService(final Context context, String http) {

        //日志拦截器
        /*
         * 获取缓存
         */
        final SharedPreferences pref = context.getSharedPreferences("cache",MODE_PRIVATE);
        Interceptor baseInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!isInternetCanDo(context)) {
                    /*
                     * 离线缓存控制  总的缓存时间=在线缓存时间+设置离线缓存时间
                     */
                    int maxStale = pref.getInt("maxStale",60 * 60 * 24); // 离线时缓存保存1天,单位:秒
                    CacheControl tempCacheControl = new CacheControl.Builder()
                            .onlyIfCached()
                            .maxStale(maxStale, TimeUnit.SECONDS)
                            .build();
                    request = request.newBuilder()
                            .cacheControl(tempCacheControl)
                            .build();
                    Log.i(TAG, "intercept:no network ");
                    Toast.makeText(context,"网路出BUG了",Toast.LENGTH_SHORT).show();
                }
                return chain.proceed(request);
            }
        };
        //只有 网络拦截器环节 才会写入缓存写入缓存,在有网络的时候 设置缓存时间
        Interceptor rewriteCacheControlInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response originalResponse = chain.proceed(request);
                int maxAge = pref.getInt("maxAge",60*10); // 在线缓存在600秒内可读取 单位:秒
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            }
        };
        //设置缓存路径 内置存储
        //File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        //外部存储
        File httpCacheDirectory = new File(context.getExternalCacheDir(), "akucache");
        //设置缓存 5M
        int cacheSize = pref.getInt("cacheSize",10 * 1024 * 1024);
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(baseInterceptor)
                .addNetworkInterceptor(rewriteCacheControlInterceptor)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
        /*
         * 创建客户端
         */
        return new Retrofit
                .Builder()
                .baseUrl(http)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build().create(RetrofitApi.class);
    }
}
