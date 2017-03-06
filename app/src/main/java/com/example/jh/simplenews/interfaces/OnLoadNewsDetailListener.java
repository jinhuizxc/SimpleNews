package com.example.jh.simplenews.interfaces;

import com.example.jh.simplenews.beans.NewsDetailBean;

/**
 * 作者：jinhui on 2017/2/21
 * 邮箱：1004260403@qq.com
 *  新闻详情加载回调
 */

public interface OnLoadNewsDetailListener {

    void onSuccess(NewsDetailBean newsDetailBean);
    void onFailure(String msg, Exception e);
}
