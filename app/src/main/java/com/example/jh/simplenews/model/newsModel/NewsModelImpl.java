package com.example.jh.simplenews.model.newsModel;

import android.util.Log;

import com.example.jh.simplenews.beans.NewsBean;
import com.example.jh.simplenews.beans.NewsDetailBean;
import com.example.jh.simplenews.Api.Urls;
import com.example.jh.simplenews.fragment.NewsFragment;
import com.example.jh.simplenews.listener.OnLoadNewsDetailListener;
import com.example.jh.simplenews.listener.OnLoadNewsListListener;
import com.example.jh.simplenews.utils.NewsJsonUtils;
import com.example.jh.simplenews.utils.OkHttpUtils;

import java.util.List;

/**
 * 作者：jinhui on 2017/2/21
 * 邮箱：1004260403@qq.com
 *
 * 新闻业务处理类
 */

public class NewsModelImpl implements NewsModel {

    private static final String TAG = "NewsModelImpl";

    /**
     * 加载新闻列表
     * @param url
     * @param listener
     */
    @Override
    public void loadNews(String url, final int type, final OnLoadNewsListListener listener) {
        OkHttpUtils.ResultCallBack<String> loadNewsCallback = new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void OnSuccess(String response) {
                http://c.m.163.com/nc/article/list/T1348654060988/0-20.html
                Log.e(TAG, "response@@@@@@@@@@@@@@@ = " + response + "ENDEND");
                List<NewsBean> newsBeanList = NewsJsonUtils.readJsonNewsBeans(response, getID(type));
                Log.e(TAG, "newsBeanList" + newsBeanList);
                listener.onSuccess(newsBeanList);
            }

            @Override
            public void OnFailure(Exception e) {
                listener.onFailure("load news list failure.", e);
            }
        };
        OkHttpUtils.get(url, loadNewsCallback);
        Log.e(TAG, "URL--------------" + url);
    }

    /**
     * 加载新闻详情
     * @param docid
     * @param listener
     */
    @Override
    public void loadNewsDetail(final String docid, final OnLoadNewsDetailListener listener) {
        String url = getDetailUrl(docid);
        OkHttpUtils.ResultCallBack<String> loadNewsCallback = new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void OnSuccess(String response) {
                NewsDetailBean newsDetailBean = NewsJsonUtils.readJsonNewsDetailBeans(response, docid);
                listener.onSuccess(newsDetailBean);
            }

            @Override
            public void OnFailure(Exception e) {
                listener.onFailure("load news detail info failure.", e);
            }
        };
        OkHttpUtils.get(url, loadNewsCallback);
    }

    /**
     * 获取ID
     * @param type
     * @return
     */
    private String getID(int type) {
        String id;
        switch (type) {
            case NewsFragment.NEWS_TYPE_TOP:
                id = Urls.TOP_ID;
                break;
            case NewsFragment.NEWS_TYPE_NBA:
                id = Urls.NBA_ID;
                break;
            case NewsFragment.NEWS_TYPE_CARS:
                id = Urls.CAR_ID;
                break;
            case NewsFragment.NEWS_TYPE_JOKES:
                id = Urls.JOKE_ID;
                break;
            default:
                id = Urls.TOP_ID;
                break;
        }
        return id;
    }

    private String getDetailUrl(String docId) {
        StringBuffer sb = new StringBuffer(Urls.NEW_DETAIL);
        sb.append(docId).append(Urls.END_DETAIL_URL);
        return sb.toString();
    }

}