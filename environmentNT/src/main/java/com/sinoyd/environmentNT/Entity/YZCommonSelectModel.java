package com.sinoyd.environmentNT.Entity;

/**
 * Created by shenchuanjiang on 2017/9/7.
 */

public class YZCommonSelectModel {

    public YZCommonSelectModel(String key, String vlaue) {
        this.key = key;
        this.vlaue = vlaue;
    }

    private String key;
    private String vlaue;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVlaue() {
        return vlaue;
    }

    public void setVlaue(String vlaue) {
        this.vlaue = vlaue;
    }
}
