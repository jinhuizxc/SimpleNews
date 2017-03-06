package com.example.jh.simplenews.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.jh.simplenews.Impl.NewsDetailPresenterImpl;
import com.example.jh.simplenews.R;
import com.example.jh.simplenews.beans.NewsBean;
import com.example.jh.simplenews.interfaces.NewsDetailPresenter;
import com.example.jh.simplenews.utils.ImageLoaderUtils;
import com.example.jh.simplenews.utils.ToolsUtil;
import com.example.jh.simplenews.view.NewsDetailView;
import com.example.jh.swipeback.SwipeBackLayout;
import com.example.jh.swipeback.app.SwipeBackActivity;

import org.sufficientlysecure.htmltextview.HtmlTextView;

/**
 * 作者：jinhui on 2017/2/21
 * 邮箱：1004260403@qq.com
 *  新闻详情界面
 */

public class NewsDetailActivity extends SwipeBackActivity implements NewsDetailView {

    private NewsBean mNews;
    private HtmlTextView mTVNewsContent;
    private NewsDetailPresenter mNewsDetailPresenter;
    private ProgressBar mProgressBar;
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mTVNewsContent = (HtmlTextView) findViewById(R.id.htNewsContent);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeSize(ToolsUtil.getWidthInPx(this));
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

        mNews = (NewsBean) getIntent().getSerializableExtra("news");

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(mNews.getTitle());

        ImageLoaderUtils.display(getApplicationContext(), (ImageView) findViewById(R.id.ivImage), mNews.getImgsrc());

        mNewsDetailPresenter = new NewsDetailPresenterImpl(getApplication(), this);
        mNewsDetailPresenter.loadNewsDetail(mNews.getDocid());
    }

    @Override
    public void showNewsDetialContent(String newsDetailContent) {
        mTVNewsContent.setHtmlFromString(newsDetailContent, new HtmlTextView.LocalImageGetter());
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }
}
