package com.xd.commander.aku.api;
import com.xd.commander.aku.bean.GitHubUserInfo;
import com.xd.commander.aku.constants.Constants;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Administrator on 2017/4/9.
 * 网络请求接口
 */

public interface RetrofitApi {
//    https://android-arsenal.com/?page=1&sort=updated&category=1
    /**
     * @param page     页码
     * @param sort     种类
     * @param category 分类
     * @return 开源库列表
     */
    @GET("/")
    Observable<ResponseBody> getHtml(
            @Query(Constants.Page) int page
            , @Query(Constants.Sort) String sort
            , @Query(Constants.Category) int category
    );
//    https://android-arsenal.com/details/1/5565
    /**
     * @param http 开源库的链接
     * @return 开源库详情
     */
    @GET
    Observable<ResponseBody> getProjectDetail(
            @Url String http
    );

    /**
     * @param owner 作者名字
     * @return  作者详情
     */
    //https://api.github.com/users/yjm
    @GET("users/{user}")
    Observable<GitHubUserInfo> getUserInfo(@Path("user") String owner);

    /**
     * @param http 网址
     * @return 搜索结果or分类
     */
    //https://android-arsenal.com/tag/240?category=1
    //
    @GET
    Observable<ResponseBody> getSortProject(
            @Url String http
    );
    //https://raw.githubusercontent.com/safetysystemtechnology/android-shake-detector/master/README.md
    //有的项目名称中带有空格，导致跳转markedown失败，解决方案为在把项目名称中带有空格的换成-
    @GET("{user}/{project}/master/README.md")
    Observable<ResponseBody> getMarkdown(@Path("user")String user,@Path("project")String project);
}
