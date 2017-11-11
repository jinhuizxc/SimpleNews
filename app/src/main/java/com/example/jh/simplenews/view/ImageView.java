package com.example.jh.simplenews.view;

import com.example.jh.simplenews.beans.ImageBean;

import java.util.List;

/**
 * 作者：jinhui on 2017/2/21
 * 邮箱：1004260403@qq.com
 */

public interface ImageView {
    // 添加图片
    void addImages(List<ImageBean> list);
    // 显示进度
    void showProgress();
    // 隐藏进度
    void hideProgress();
    // 显示加载失败信息
    void showLoadFailMsg();
}
