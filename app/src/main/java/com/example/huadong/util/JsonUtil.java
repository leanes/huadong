package com.example.huadong.util;

import com.example.huadong.bean.NewsMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by 陈振聪 on 2017/3/30.
 */
public class JsonUtil {
    public static void dataParse(String data , List<NewsMode> list){
        try {
            list.clear();
            JSONObject jsonObject = new JSONObject(data) ;
            JSONArray jsonArray = jsonObject.getJSONArray("list") ;
            for (int i = 0 ; i < jsonArray.length() ; i++){
                JSONObject object = jsonArray.getJSONObject(i) ;
                NewsMode newsMode = new NewsMode() ;
                newsMode.setImgurl(object.getString("imgurl"));
                newsMode.setDocurl(object.getString("docurl"));
                newsMode.setTime(object.getString("time"));
                newsMode.setTitle(object.getString("title"));
                newsMode.setChannelname(object.getString("channelname"));
                list.add(newsMode) ;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
