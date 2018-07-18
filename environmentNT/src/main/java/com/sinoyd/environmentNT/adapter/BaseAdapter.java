package com.sinoyd.environmentNT.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

/**
 * 万能的Adapter，作为各具体使用的Adapter的父类
 *
 * @param <T> 需要处理的数据的类型
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
    /**
     * Context
     */
    private Context context;
    /**
     * 数据源
     */
    private List<T> data;
    /**
     * 将XML加载为View对象的工具
     */
    private LayoutInflater layoutInflater;

    public BaseAdapter(Context context, List<T> data) {
        super();
        setContext(context);
        setData(data);
        setLayoutInflater();
    }

    /**
     * 设置LayoutInflater属性的值
     */
    private void setLayoutInflater() {
        this.layoutInflater = LayoutInflater.from(context);
    }

    /**
     * 设置Context
     *
     * @param context
     */
    private void setContext(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("参数Context不允许为null！！！");
        }
        this.context = context;
    }

    /**
     * 设置数据源
     *
     * @param data 数据的List集合
     */
    private void setData(List<T> data) {
        if (data == null) {
            data = new ArrayList<T>();
        }
        this.data = data;
    }

    /**
     * 获取LayoutInflater对象
     *
     * @return LayoutInflater对象
     */
    protected final LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }

    /**
     * 获取Context对象
     *
     * @return Context对象
     */
    protected final Context getContext() {
        return context;
    }

    /**
     * 获取数据源
     *
     * @return 数据的List集合
     */
    protected final List<T> getData() {
        return data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}