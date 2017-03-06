package com.example.jh.simplenews.Impl;

import com.example.jh.simplenews.R;
import com.example.jh.simplenews.interfaces.MainPresenter;
import com.example.jh.simplenews.view.MainView;

/**
 * 作者：jinhui on 2017/2/21
 * 邮箱：1004260403@qq.com
 */
//子类实现接口，接口对象的引用可以new子类的构造。
public class MainPresenterImpl implements MainPresenter {

    private MainView mMainView;

    public MainPresenterImpl(MainView mainView) {
        this.mMainView = mainView;
    }

    @Override
    public void switchNavigation(int id) {
        switch (id) {
            case R.id.navigation_item_news:
                mMainView.switch2News();
                break;
            case R.id.navigation_item_images:
                mMainView.switch2Images();
                break;
            case R.id.navigation_item_weather:
                mMainView.switch2Weather();
                break;
            case R.id.navigation_item_about:
                mMainView.switch2About();
                break;
            default:
                mMainView.switch2News();
                break;
        }
    }
}