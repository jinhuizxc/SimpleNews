package com.example.jh.simplenews.listener;

import com.example.jh.simplenews.beans.NewsBean;

import java.util.List;

/**
 * 作者：jinhui on 2017/2/21
 * 邮箱：1004260403@qq.com
 *
 * 新闻列表加载回调
 */

public interface OnLoadNewsListListener {

    void onSuccess(List<NewsBean> list);
    void onFailure(String msg, Exception e);
}
