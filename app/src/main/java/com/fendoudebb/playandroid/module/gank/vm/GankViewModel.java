package com.fendoudebb.playandroid.module.gank.vm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListProvider;
import android.arch.paging.PagedList;
import android.arch.paging.TiledDataSource;
import android.support.annotation.NonNull;

import com.fendoudebb.playandroid.module.api.ApiFactory;
import com.fendoudebb.playandroid.module.api.bean.gank.Gank;
import com.fendoudebb.playandroid.module.api.bean.gank.GankData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * zbj on 2017-11-15 16:36.
 */

public class GankViewModel extends AndroidViewModel {

    private static final String TAG = "GankViewModel";

    /**
     * 每次需要10个数据.
     */
    private static final int NEED_NUMBER = 10;

    /**
     * 福利第一页.
     */
    private static final int PAGE_FIRST = 1;

    /**
     * 分页.
     */
    private int mPage = PAGE_FIRST;

    public LiveData<PagedList<Gank>> mGankLiveData;

    public GankViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<PagedList<Gank>> getGankLiveData(String title) {
        initGankPageList(title);
        return mGankLiveData;
    }

    private void initGankPageList(final String title) {
        final TiledDataSource<Gank> gankTiledDataSource = new TiledDataSource<Gank>() {

            /**
             * 需要的总个数,如果数量不定,就传COUNT_UNDEFINED.
             */
            @Override
            public int countItems() {
                return DataSource.COUNT_UNDEFINED;
            }

            /**
             * 返回需要加载的数据.
             * 这里是在线程异步中执行的,所以可以同步请求数据并且返回
             * @param startPosition 现在第几个数据
             * @param count 加载的数据数量
             */
            @Override
            public List<Gank> loadRange(int startPosition, int count) {
                List<Gank> ganks = new ArrayList<>();

                try {
                    Response<GankData> execute = ApiFactory.getGankApi().getCategoryDataSync
                            (title, mPage).execute();
                    List<Gank> results = execute.body().results;
                    ganks.addAll(results);

                    if (!ganks.isEmpty()) {
                        mPage++;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return ganks;
            }
        };

        mGankLiveData = new LivePagedListProvider<Integer, Gank>() {
            @Override
            protected DataSource<Integer, Gank> createDataSource() {
                return gankTiledDataSource;
            }
        }.create(0, new PagedList.Config.Builder()
                .setPageSize(NEED_NUMBER)
                .setPrefetchDistance(2)
                .setEnablePlaceholders(false)
                .build());
    }

}
