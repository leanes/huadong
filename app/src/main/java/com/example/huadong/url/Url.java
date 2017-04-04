package com.example.huadong.url;

/**
 * Created by 陈振聪 on 2017/3/30.
 */

//http://wangyi.butterfly.mopaasapp.com/news/api?type=war&page=1&limit=10
    /*
    <item>war|军事</item>
        <item>sport|体育</item>
        <item>tech|科技</item>
        <item>edu|教育</item>
        <item>ent|娱乐</item>
        <item>money|财经</item>
        <item>gupiao|股票</item>
        <item>travel|旅游</item>
        <item>lady|女人</item>
     */
public class Url {
    public static final String hosturl = "http://wangyi.butterfly.mopaasapp.com/news/api?type=" ;
    public static final String pageurl = "&page=" ;
    public static final String limiturl = "&limit=" ;
    public static final String warurl = "war" ;
    public static final String sporturl = "sport" ;
    public static final String techurl = "tech" ;
    public static final String eduurl = "edu" ;
    public static final String enturl = "ent" ;
    public static final String moneyurl = "money" ;
    public static final String gupiaourl = "gupiao" ;
    public static final String travelurl = "travel" ;
    public static final String ladyurl = "lady" ;

    private int page ;
    private int limit ;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
