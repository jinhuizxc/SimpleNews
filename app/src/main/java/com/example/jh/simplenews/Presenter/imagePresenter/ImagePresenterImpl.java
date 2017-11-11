package com.example.jh.simplenews.Presenter.imagePresenter;

import com.example.jh.simplenews.listener.OnLoadImageListListener;
import com.example.jh.simplenews.model.imageModel.ImageModelImpl;
import com.example.jh.simplenews.beans.ImageBean;
import com.example.jh.simplenews.model.imageModel.ImageModel;
import com.example.jh.simplenews.view.ImageView;

import java.util.List;

/**
 * 作者：jinhui on 2017/2/21
 * 邮箱：1004260403@qq.com
 */

public class ImagePresenterImpl implements ImagePresenter, OnLoadImageListListener {

    private ImageModel mImageModel;
    private ImageView mImageView;

    public ImagePresenterImpl(ImageView imageView) {
        this.mImageModel = new ImageModelImpl();
        this.mImageView = imageView;
    }

    @Override
    public void loadImageList() {
        mImageView.showProgress();
        mImageModel.loadImageList(this);
    }

    @Override
    public void onSuccess(List<ImageBean> list) {
        mImageView.addImages(list);
        mImageView.hideProgress();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mImageView.hideProgress();
        mImageView.showLoadFailMsg();
    }
}