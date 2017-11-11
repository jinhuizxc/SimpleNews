package com.example.jh.simplenews.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.jh.simplenews.Presenter.mainPresenter.MainPresenterImpl;
import com.example.jh.simplenews.R;
import com.example.jh.simplenews.fragment.AboutFragment;
import com.example.jh.simplenews.fragment.ImageFragment;
import com.example.jh.simplenews.fragment.NewsFragment;
import com.example.jh.simplenews.fragment.WeatherFragment;
import com.example.jh.simplenews.Presenter.mainPresenter.MainPresenter;
import com.example.jh.simplenews.view.MainView;


public class MainActivity extends AppCompatActivity implements MainView {

    private static final String TAG = "MainActivity";
    //进行编辑啦、
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;

    private MainPresenter mMainPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,
                R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        setupDrawerContent(mNavigationView);

        mMainPresenter = new MainPresenterImpl(this);
        //显示新闻界面
        switchNews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.about) {
            Toast.makeText(MainActivity.this, "关于",Toast.LENGTH_SHORT).show();
        }else if (id == R.id.settings) {
            Toast.makeText(MainActivity.this, "设置",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // 对侧滑项的id进行一个识别
                        mMainPresenter.switchNavigation(menuItem.getItemId());
                        Log.e(TAG, "id = " + menuItem.getItemId());
                        /**
                         * 侧滑id这样就得到我们结果了！
                         *  id = 2131558567
                         *  id = 2131558568
                         *  id = 2131558569
                         *  id = 2131558566
                         */
                        menuItem.setChecked(true);// 这一句只是说明item项被选中的状态为true，在这里不写也无所谓。
                        // 加上这句可以上侧滑栏关闭
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public void switchNews() {
        // 开始我们的正文吧，这个地方通通都是打开了fragment,按照以前的思维这里会启动一个activity
        // 但是你会发现现在android的xml布局里面都是目前最新的设置，加上fragment比activity要轻巧，
        // 而activity的话需要重新打开一个界面造成布局上的冗杂！这是其一，
        // 其二是不管是打开新闻、图片等都需要采用一个布局而且统一
        // 将fragment添加到了frameLayout里面，toolbar的改变就会变得很容易用activity每次都得重新配置头文件，
        // 降低了编程的效率
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl, new NewsFragment())
                .commit();
        mToolbar.setTitle(R.string.navigation_news);
    }

    @Override
    public void switchImages() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fl, new ImageFragment()).commit();
        mToolbar.setTitle(R.string.navigation_images);
    }

    @Override
    public void switchWeather() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fl, new WeatherFragment()).commit();
        mToolbar.setTitle(R.string.navigation_weather);
    }

    @Override
    public void switchAbout() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fl, new AboutFragment()).commit();
        mToolbar.setTitle(R.string.navigation_about);
    }
}
