package com.example.huadong.bean;

/**
 * Created by 陈振聪 on 2017/3/30.
 */
public class Category {
    private int cid ;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    private String name ;
    private String title ;

    public Category(int cid, String name, String title) {
        this.cid = cid;
        this.name = name;
        this.title = title;
    }

    @Override
    public String toString() {
        return title ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
