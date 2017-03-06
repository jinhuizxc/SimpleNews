package com.example.jh.simplenews.interfaces;

/**
 * 作者：jinhui on 2017/2/21
 * 邮箱：1004260403@qq.com
 */

public interface NewsModel {

    void loadNews(String url, int type, OnLoadNewsListListener listener);
    void loadNewsDetail(String docid, OnLoadNewsDetailListener listener);
}
