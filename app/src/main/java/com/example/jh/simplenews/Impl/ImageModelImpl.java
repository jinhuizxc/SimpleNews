package com.example.jh.simplenews.Impl;

import com.example.jh.simplenews.beans.ImageBean;
import com.example.jh.simplenews.commons.Urls;
import com.example.jh.simplenews.interfaces.ImageModel;
import com.example.jh.simplenews.utils.ImageJsonUtils;
import com.example.jh.simplenews.utils.OkHttpUtils;

import java.util.List;

/**
 * 作者：jinhui on 2017/2/21
 * 邮箱：1004260403@qq.com
 */

public class ImageModelImpl implements ImageModel {
    /**
     * 获取图片列表
     * @param listener
     */
    @Override
    public void loadImageList(final OnLoadImageListListener listener) {
        String url = Urls.IMAGES_URL;
        OkHttpUtils.ResultCallback<String> loadNewsCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                List<ImageBean> iamgeBeanList = ImageJsonUtils.readJsonImageBeans(response);
                listener.onSuccess(iamgeBeanList);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("load image list failure.", e);
            }
        };
        OkHttpUtils.get(url, loadNewsCallback);
    }

    public interface OnLoadImageListListener {
        void onSuccess(List<ImageBean> list);
        void onFailure(String msg, Exception e);
    }
}
