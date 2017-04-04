package com.example.huadong;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.huadong.adapter.CategoryListAdapter;
import com.example.huadong.bean.NewsMode;
import com.example.huadong.url.Url;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.huadong.util.JsonUtil.dataParse;

/**
 * Created by 陈振聪 on 2017/4/1.
 */
public class Categoryaivity extends Activity implements ListViewLoad.IListViewLoad {

     private ListViewLoad  newsList ;

    private SwipeRefreshLayout swipeRefreshLayout ;
    private String newsType ;

    private int count = 20 ;

    private List<NewsMode> modeList ;
    private CategoryListAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cayegory_list);
        final Intent intent = getIntent() ;
        Bundle bundle = intent.getExtras() ;
        newsType = bundle.getString("newsType") ;
        modeList = new ArrayList<>() ;
        showListView(modeList);
        getNewsCategoryWithOkHttp(newsType) ;           //连接服务器
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshCategory() ;
            }

        });

        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent inten = new Intent(Categoryaivity.this , NewsContentActivity.class) ;
                    String url = modeList.get(position).getDocurl() ;
                    inten.putExtra("url" , url) ;
                    startActivity(inten);

            }
        });

    }
    private void showListView(List<NewsMode> newsMode) {
        if (adapter == null) {
             newsList = (ListViewLoad) findViewById(R.id.news_list);
            // 设置回调接口
            newsList.setInterface(this);
            adapter = new CategoryListAdapter(this, newsMode);
            newsList.setAdapter(adapter);
        } else {
            adapter.setDateChange(newsMode);
        }
    }


    private void refreshCategory() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getNewsCategoryWithOkHttp(newsType);

            }
        }).start();
    }

    private void getNewsCategoryWithOkHttp(final String newsType) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient() ;
                    Request request = new Request.Builder()
                            .url(Url.hosturl + newsType + Url.pageurl + 1 + Url.limiturl + count)
                            // .url("http://wangyi.butterfly.mopaasapp.com/news/api?type=ent&page=1&limit=20")
                            .build() ;
                    Response response = client.newCall(request).execute() ;
                    String responseData = response.body().string() ;

                    dataParse(responseData , modeList) ;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            newsList.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    public void onLoad() {
        count = count + 10 ;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    OkHttpClient client = new OkHttpClient() ;
                    Request request = new Request.Builder()
                            .url(Url.hosturl + newsType + Url.pageurl + 1 + Url.limiturl + count)
                            // .url("http://wangyi.butterfly.mopaasapp.com/news/api?type=ent&page=1&limit=20")
                            .build() ;
                    Response response = client.newCall(request).execute() ;
                    String responseData = response.body().string() ;
                    List<NewsMode> otherList = new ArrayList<NewsMode>() ;
                    dataParse(responseData , otherList) ;
                    modeList = otherList ;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 显示
                            showListView(modeList);
                            // 通知listview加载完毕
                            newsList.loadComplete();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
