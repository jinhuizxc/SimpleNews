package com.example.jh.simplenews.model.imageModel;

import android.util.Log;

import com.example.jh.simplenews.beans.ImageBean;
import com.example.jh.simplenews.Api.Urls;
import com.example.jh.simplenews.listener.OnLoadImageListListener;
import com.example.jh.simplenews.utils.ImageJsonUtils;
import com.example.jh.simplenews.utils.OkHttpUtils;

import java.util.List;

/**
 * 作者：jinhui on 2017/2/21
 * 邮箱：1004260403@qq.com
 */

public class ImageModelImpl implements ImageModel{

    public static final String TAG = "ImageModelImpl";
    /**
     * 获取图片列表
     * @param listener
     */
    @Override
    public void loadImageList(final OnLoadImageListListener listener) {
        String url = Urls.IMAGES_URL;
        OkHttpUtils.ResultCallBack<String> loadNewsCallback = new OkHttpUtils.ResultCallBack<String>() {

            @Override
            public void OnSuccess(String response) {
                Log.e(TAG, "response" + response);
                List<ImageBean> iamgeBeanList = ImageJsonUtils.readJsonImageBeans(response);
                listener.onSuccess(iamgeBeanList);
            }

            @Override
            public void OnFailure(Exception e) {
                listener.onFailure("load image list failure.", e);
            }
        };
        OkHttpUtils.get(url, loadNewsCallback);
    }


}
