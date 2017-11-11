package com.example.jh.simplenews.listener;

import com.example.jh.simplenews.beans.ImageBean;

import java.util.List;

/**
 * 作者：jinhui on 2017/3/10
 * 邮箱：1004260403@qq.com
 */

public interface OnLoadImageListListener {
    void onSuccess(List<ImageBean> list);
    void onFailure(String msg, Exception e);
}
