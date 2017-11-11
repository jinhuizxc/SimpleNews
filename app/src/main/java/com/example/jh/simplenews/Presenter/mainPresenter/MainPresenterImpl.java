package com.example.jh.simplenews.Presenter.mainPresenter;

import com.example.jh.simplenews.R;
import com.example.jh.simplenews.view.MainView;

/**
 * 作者：jinhui on 2017/2/21
 * 邮箱：1004260403@qq.com
 *
 * 子类实现接口，接口对象的引用可以new子类的构造。
 * 即便有PresenterImpl 、presenter 但是我们得知道我们的主要逻辑还是得写在presenter里面
 * 只是presenter可能有需要实现其他的功能需求所以才抽离了出来而已。
 * 其实这每一个方法的编写都是基于一个想法，一个方法，一旦一个方法有了，就可以去编写这个方法，具体是定义接口还是类根据实际需要去实现！
 * 这里的MainPresenterImpl在activity的oncreate里面创建，为什么会让MainPresenterImpl implements MainPresenter有这个必要么？
 * 答案是肯定的，由那一个方法使之衍生呢？下面这个方法就是我们的需求。
 *  mMainPresenter.switchNavigation(menuItem.getItemId());
 *  于是我们根据我的需求在MainPresenterImpl创建我们的mainView这与mvp的架构设计并没有发生冲突！
 *
 *   如何更加深入理解Mvp?
 *   从下面我们知道在presenter里面传入MainView,
 *   有了它的引用自然就可以调用其方法，这样的使用方式不就是方法的调用么？这就是面向对象的思想
 */

public class MainPresenterImpl implements MainPresenter {

    private MainView mMainView;

    public MainPresenterImpl(MainView mainView) {
        this.mMainView = mainView;
    }

    @Override
    public void switchNavigation(int id) {
        switch (id) {
            case R.id.navigation_item_news:
                mMainView.switchNews();
                break;
            case R.id.navigation_item_images:
                mMainView.switchImages();
                break;
            case R.id.navigation_item_weather:
                mMainView.switchWeather();
                break;
            case R.id.navigation_item_about:
                mMainView.switchAbout();
                break;
            default:
                mMainView.switchNews();
                break;
        }
    }
}