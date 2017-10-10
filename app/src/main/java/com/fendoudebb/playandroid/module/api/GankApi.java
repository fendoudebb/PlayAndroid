package com.fendoudebb.playandroid.module.api;

import com.fendoudebb.playandroid.module.api.bean.gank.DailyData;
import com.fendoudebb.playandroid.module.api.bean.gank.DataHistory;
import com.fendoudebb.playandroid.module.api.bean.gank.GankData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * author : zbj on 2017/10/3 12:27.
 */

public interface GankApi {

    /**
     * 获取发过干货日期接口
     * http://gank.io/api/day/history 方式 GET
     *
     * @return {@link DataHistory}
     */
    @GET("day/history")
    Observable<DataHistory> getDataHistory();

    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     * 请求个数： 数字，大于0
     * 例：
     * http://gank.io/api/data/Android/10/1
     * http://gank.io/api/data/福利/10/1
     * http://gank.io/api/data/iOS/20/2
     * http://gank.io/api/data/all/20/2
     *
     * @param category 数据类型: 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * @param page     第几页：数字，大于0
     * @return {@link GankData}
     */
    @GET("data/{category}/10/{page}")
    Observable<GankData> getCategoryData(@Path("category") String category, @Path("page") int page);

    /**
     * 每日数据： http://gank.io/api/day/年/月/日
     * 例：http://gank.io/api/day/2015/08/06
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return {@link GankData}
     */
    @GET("day/{year}/{month}/{day}")
    Observable<DailyData> getDailyData(@Path("year") int year, @Path("month") int month
            , @Path("day") int day);

    /**
     * 随机数据：http://gank.io/api/random/data/分类/个数
     * 例：http://gank.io/api/random/data/Android/20
     *
     * @param category 数据类型：福利 | Android | iOS | 休息视频 | 拓展资源 | 前端
     * @param count 个数： 数字，大于0
     * @return {@link GankData}
     */
    @GET("random/data/{category}/{count}")
    Observable<GankData> getRandomCategoryData(@Path("category") String category
            , @Path("count") int count);

}
