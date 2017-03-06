package com.example.jh.simplenews.view;

import com.example.jh.simplenews.beans.NewsBean;

import java.util.List;

/**
 * 作者：jinhui on 2017/2/21
 * 邮箱：1004260403@qq.com
 */

public interface NewsView {

    void showProgress();
    void addNews(List<NewsBean> newsList);
    void hideProgress();
    void showLoadFailMsg();
}
