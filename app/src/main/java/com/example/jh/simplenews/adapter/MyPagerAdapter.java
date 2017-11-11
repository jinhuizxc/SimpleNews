package com.example.jh.simplenews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：jinhui on 2017/3/10
 * 邮箱：1004260403@qq.com
 * 继承了FragmentPagerAdapter的类会默认添加它的两个方法与构造函数，
 * 这里我们主要是设置我们fragment列表，并没有添加数据。
 * 其中这两项是必备：
 *  private final List<Fragment> mFragments = new ArrayList<>();
 *  private final List<String> mFragmentTitles = new ArrayList<>();
 *
 *  你要知道这是因为一个fragment对应一个title,如果没有title的话，滑动可以进行，但是title为空
 *
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    // 添加fragment
    public void addFragment(Fragment fragment, String title) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }
}