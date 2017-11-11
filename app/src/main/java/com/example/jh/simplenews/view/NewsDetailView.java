package com.example.jh.simplenews.view;

/**
 * 作者：jinhui on 2017/2/21
 * 邮箱：1004260403@qq.com
 */

public interface NewsDetailView {
    // 显示新闻内容
    void showNewsDetialContent(String newsDetailContent);
    // 显示进度
    void showProgress();
    // 隐藏进度
    void hideProgress();
}
