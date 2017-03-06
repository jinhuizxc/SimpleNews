package com.example.jh.simplenews.Impl;

import com.example.jh.simplenews.beans.NewsBean;
import com.example.jh.simplenews.commons.Urls;
import com.example.jh.simplenews.fragment.NewsFragment;
import com.example.jh.simplenews.interfaces.NewsModel;
import com.example.jh.simplenews.interfaces.NewsPresenter;
import com.example.jh.simplenews.interfaces.OnLoadNewsListListener;
import com.example.jh.simplenews.utils.LogUtils;
import com.example.jh.simplenews.view.NewsView;

import java.util.List;

/**
 * 作者：jinhui on 2017/2/21
 * 邮箱：1004260403@qq.com
 */

public class NewsPresenterImpl implements NewsPresenter, OnLoadNewsListListener {
    private static final String TAG = "NewsPresenterImpl";

    private NewsView mNewsView;
    private NewsModel mNewsModel;

    public NewsPresenterImpl(NewsView newsView) {
        this.mNewsView = newsView;
        this.mNewsModel = new NewsModelImpl();
    }

    @Override
    public void loadNews(final int type, final int pageIndex) {
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

