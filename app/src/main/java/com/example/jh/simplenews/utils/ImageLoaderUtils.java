package com.example.jh.simplenews.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jh.simplenews.R;

/**
 * 作者：jinhui on 2017/2/21
 * 邮箱：1004260403@qq.com
 * 图片加载工具类
 */

public class ImageLoaderUtils {

    public static void display(Context context, ImageView imageView, String url) {
        if(imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url).placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_image_loadfail).crossFade().into(imageView);
    }


}
