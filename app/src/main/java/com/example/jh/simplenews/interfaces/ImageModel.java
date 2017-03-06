package com.example.jh.simplenews.interfaces;

import com.example.jh.simplenews.Impl.ImageModelImpl;

/**
 * 作者：jinhui on 2017/2/21
 * 邮箱：1004260403@qq.com
 */

public interface ImageModel {
    void loadImageList(ImageModelImpl.OnLoadImageListListener listener);
}
