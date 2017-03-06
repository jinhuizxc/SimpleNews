package com.example.jh.simplenews.view;

import com.example.jh.simplenews.beans.ImageBean;

import java.util.List;

/**
 * 作者：jinhui on 2017/2/21
 * 邮箱：1004260403@qq.com
 */

public interface ImageView {
    void addImages(List<ImageBean> list);
    void showProgress();
    void hideProgress();
    void showLoadFailMsg();
}
