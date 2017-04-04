package com.example.huadong;

/**
 * Created by 陈振聪 on 2017/4/2.
 */
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class ListViewLoad extends ListView implements OnScrollListener {
    View footerView;
    // 总数量
    int totalItemCount;
    // 最后一个可见的Item
    int lastvisibleitem;
    // 自定义加载的接口
    private IListViewLoad ilistViewLoad;
    // 是否在加载的标志位
    boolean isLoading = false;
    public ListViewLoad(Context context) {
        super(context);
        // 在构造方法中加载视图
        initView(context);
    }
    public ListViewLoad(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 在构造方法中加载视图
        initView(context);
    }

    public ListViewLoad(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 在构造方法中加载视图
        initView(context);
    }
    //添加底部listview布局到视图中
    private void initView(Context context) {
        footerView = LayoutInflater.from(context).inflate(R.layout.foot_view, null);

        // 初始化视图的时候将布局移除掉，防止一开始出现在视图中
        footerView.findViewById(R.id.footer_lay).setVisibility(View.GONE);
        // 向ListView中添加上拉加载布局
        this.addFooterView(footerView , 1 , false);

        // ListView设置滑动监听
        this.setOnScrollListener(this);

    }
    // 视图滚动时进行回调
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if (!isLoading) {
            this.lastvisibleitem = firstVisibleItem + visibleItemCount -1  ;
            this.totalItemCount = totalItemCount;
        }
    }

    // 当滚动改变时进行回调
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // 如果总数量等于最后一个item且手在向下滚动时，显示加载布局，且加载更多内容
        if (lastvisibleitem == view.getCount() - 1
                && scrollState == SCROLL_STATE_IDLE) {
            // 显示
            footerView.findViewById(R.id.footer_lay).setVisibility(View.VISIBLE);
 /*           footerView = LayoutInflater.from(getContext()).inflate(R.layout.foot_view, null);

            // 初始化视图的时候将布局移除掉，防止一开始出现在视图中
//                footerView.findViewById(R.id.footer_lay).setVisibility(View.GONE);
            // 向ListView中添加上拉加载布局
            this.addFooterView(footerView);*/
            if (!isLoading) {
                isLoading = true;
                // 加载更多
                ilistViewLoad.onLoad();
            }
        }
    }
    //加载完毕
    public void loadComplete(){
        footerView.findViewById(R.id.footer_lay).setVisibility(View.GONE);
//        this.removeFooterView(footerView) ;
        isLoading = false;

    }
    //将接口设置进去
    public void setInterface(IListViewLoad ilistViewLoad){
        this.ilistViewLoad = ilistViewLoad;
    }
    public interface IListViewLoad{
        public void onLoad();
    }

}