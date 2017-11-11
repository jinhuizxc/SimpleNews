package com.example.jh.simplenews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jh.simplenews.R;
import com.example.jh.simplenews.beans.NewsBean;
import com.example.jh.simplenews.utils.ImageLoaderUtils;

import java.util.List;


/**
 *
 * 作者：jinhui on 2017/2/21
 * 邮箱：1004260403@qq.com
 * 新闻 adapter
 *   继承RecyclerView.Adapter<RecyclerView.ViewHolder>重写3个方法
 *   对于适配器都会有Context对象，与链表数据
 *   至于header与footer的话，在加上，分别是独立的分支。
 *
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "NewsAdapter";
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private boolean mShowFooter = true;

    private List<NewsBean> mData;
    private Context mContext;



    // 之前写的话，构造方法里面有context、有链表。其实是可以分开的，便于每一个部分的管理与编写！
    public NewsAdapter(Context context) {
        this.mContext = context;
    }

    // 设置数据，这个方法在NewsListFragment中addNews方法中调用。
    public void setmData(List<NewsBean> data) {
        this.mData = data;
        this.notifyDataSetChanged();
    }

    // RecyclerView中的方法
    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if(!mShowFooter) {
            return TYPE_ITEM;
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_news, parent, false);
            ItemViewHolder viewHolder = new ItemViewHolder(view);
            return viewHolder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.footer, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder) {
            NewsBean news = mData.get(position);
            if(news == null) {
                return;
            }
            ((ItemViewHolder) holder).mTitle.setText(news.getTitle());
            ((ItemViewHolder) holder).mDesc.setText(news.getDigest());
//            Uri uri = Uri.parse(news.getImgsrc());
//            ((ItemViewHolder) holder).mNewsImg.setImageURI(uri);

            //加载图片
            ImageLoaderUtils.display(mContext, ((ItemViewHolder) holder).mNewsImg, news.getImgsrc());
        }
    }

    @Override
    public int getItemCount() {
        int begin = mShowFooter ? 1 : 0;
        if(mData == null) {
            return begin;
        }
        Log.e(TAG, "begin =" + begin);
        return mData.size() + begin;
    }

    public NewsBean getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    // mShowFooter的getter/setter方法
    public void isShowFooter(boolean showFooter) {
        this.mShowFooter = showFooter;
    }
    public boolean isShowFooter() {
        return this.mShowFooter;
    }



    // 设置item项的监听接口，回调方法
    private OnItemClickListener mOnItemClickListener;
    // 传参数为view对象与position
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    // footer    ViewHolder
    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTitle;
        public TextView mDesc;
        public ImageView mNewsImg;
        // 之前在写item项的监听时，设置点击事件直接listview进行点击，
        // 对其中某个控件，比如按钮设置点击就是这里进行view项项的点击
        public ItemViewHolder(View view) {
            super(view);
            mTitle = (TextView) view.findViewById(R.id.tvTitle);
            mDesc = (TextView) view.findViewById(R.id.tvDesc);
            mNewsImg = (ImageView) view.findViewById(R.id.ivNews);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // 既然是监听就有借口这里的这个接口自己定义的
            if(mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, this.getPosition());
            }
        }
    }

}
