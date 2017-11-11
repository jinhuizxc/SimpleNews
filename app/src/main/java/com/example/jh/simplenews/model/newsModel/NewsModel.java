package com.example.jh.simplenews.model.newsModel;

import com.example.jh.simplenews.listener.OnLoadNewsDetailListener;
import com.example.jh.simplenews.listener.OnLoadNewsListListener;

/**
 * 作者：jinhui on 2017/2/21
 * 邮箱：1004260403@qq.com
 *
 * 这里它有两个方法：
 *  void loadNews(String url, int type, OnLoadNewsListListener listener);
 *  void loadNewsDetail(String docid, OnLoadNewsDetailListener listener);
 */

public interface NewsModel {

    void loadNews(String url, int type, OnLoadNewsListListener listener);
    void loadNewsDetail(String docid, OnLoadNewsDetailListener listener);
}
