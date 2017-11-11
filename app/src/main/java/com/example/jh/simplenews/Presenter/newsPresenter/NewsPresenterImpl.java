package com.example.jh.simplenews.Presenter.newsPresenter;

import android.util.Log;

import com.example.jh.simplenews.model.newsModel.NewsModelImpl;
import com.example.jh.simplenews.beans.NewsBean;
import com.example.jh.simplenews.Api.Urls;
import com.example.jh.simplenews.fragment.NewsFragment;
import com.example.jh.simplenews.model.newsModel.NewsModel;
import com.example.jh.simplenews.listener.OnLoadNewsListListener;
import com.example.jh.simplenews.utils.LogUtils;
import com.example.jh.simplenews.view.NewsView;

import java.util.List;

/**
 * 作者：jinhui on 2017/2/21
 * 邮箱：1004260403@qq.com
 *
 * 这里NewsPresenterImpl是NewsPresenter的子类，在这个类里面传入NewsView就可以了，
 * 但是这里又多了一个NewsModel，这里就是和之前写的登录的一个demo类似
 * 对于此项目可以知道需求已经变得不一样了！，所以会多出来NewsModel一个新闻处理类，
 * 其实不管怎么样就是这些方法在来回调用，重要在于他们是如何实现的，自己一定要清楚!
 * 你会发现他们总是在构造方法里面一层一层的嵌套。。。。
 */

public class NewsPresenterImpl implements NewsPresenter, OnLoadNewsListListener {

    private static final String TAG = "NewsPresenterImpl";

    private NewsView mNewsView;
    private NewsModel mNewsModel;

    // 构造方法
    public NewsPresenterImpl(NewsView newsView) {
        this.mNewsView = newsView;
        this.mNewsModel = new NewsModelImpl();   // 指向空的构造，只要是类就有其构造，无参以及有参的
    }

    @Override
    public void loadItem(final int type, final int pageIndex) {
        String url = getUrl(type, pageIndex);
        LogUtils.d(TAG, url);
        //只有第一页的或者刷新的时候才显示刷新进度条
        if(pageIndex == 0) {
            mNewsView.showProgress();
        }
        mNewsModel.loadNews(url, type, this);
    }

    /**
     * 根据类别和页面索引创建url
     * @param type
     * @param pageIndex
     * @return
     */
    private String getUrl(int type, int pageIndex) {
        StringBuffer sb = new StringBuffer();
        switch (type) {
            case NewsFragment.NEWS_TYPE_TOP:
                sb.append(Urls.TOP_URL).append(Urls.TOP_ID);
                break;
            case NewsFragment.NEWS_TYPE_NBA:
                sb.append(Urls.COMMON_URL).append(Urls.NBA_ID);
                break;
            case NewsFragment.NEWS_TYPE_CARS:
                sb.append(Urls.COMMON_URL).append(Urls.CAR_ID);
                break;
            case NewsFragment.NEWS_TYPE_JOKES:
                sb.append(Urls.COMMON_URL).append(Urls.JOKE_ID);
                break;
            default:
                sb.append(Urls.TOP_URL).append(Urls.TOP_ID);
                break;
        }
        sb.append("/").append(pageIndex).append(Urls.END_URL);
        return sb.toString();
    }



    @Override
    public void onSuccess(List<NewsBean> list) {
        mNewsView.hideProgress();
        mNewsView.addNews(list);
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mNewsView.hideProgress();
        mNewsView.showLoadFailMsg();
    }
}

