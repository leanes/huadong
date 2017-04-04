package com.example.huadong.bean;

/**
 * Created by 陈振聪 on 2017/3/30.
 */
public class NewsMode {
    private int size ;              //新闻显示几条
    private String imgurl ;         //图片地址
    private String docurl ;         //内容地址
    private String time ;           //时间

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String title ;          //标题
    private String channelname ;     //新闻分类

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getDocurl() {
        return docurl;
    }

    public void setDocurl(String docurl) {
        this.docurl = docurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannelname() {
        return channelname;
    }

    public void setChannelname(String channelname) {
        this.channelname = channelname;
    }
}
