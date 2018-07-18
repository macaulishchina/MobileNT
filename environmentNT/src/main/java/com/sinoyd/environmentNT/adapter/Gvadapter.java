package com.sinoyd.environmentNT.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sinoyd.environmentNT.Entity.GetImageUrl2;
import com.sinoyd.environmentNT.R;

import java.util.List;

/**
 * Created by shenchuanjiang on 2017/7/31.
 */

public class Gvadapter extends BaseAdapter<GetImageUrl2.CityImageBean> {
    private List<GetImageUrl2.CityImageBean> data;
    private Context context;
    private ImageLoader imageLoader;

    public Gvadapter(Context context, List<GetImageUrl2.CityImageBean> data) {
        super(context, data);
        this.data = data;
        this.context = context;
//        imageloader = new ImageLoader(Volley.newRequestQueue(context), new lruCacheiml());
        imageLoader = ImageLoader.getInstance();
    }


    //
//
//    //加载图片
//    class lruCacheiml implements ImageLoader.ImageCache {
//        private LruCache<String, Bitmap> mCache;
//
//        public lruCacheiml() {
//            // TODO Auto-generated constructor stub
//            mCache = new LruCache<String, Bitmap>(100 * 1024 * 1024) {
//                @Override
//                protected int sizeOf(String key, Bitmap value) {
//                    // TODO Auto-generated method stub
//                    return value.getRowBytes() * value.getHeight();
//                }
//
//            };
//
//        }
//
//        @Override
//        public Bitmap getBitmap(String url) {
//            // TODO Auto-generated method stub
//            return mCache.get(url);
//        }
//
//        @Override
//        public void putBitmap(String url, Bitmap bitmap) {
//            // TODO Auto-generated method stub
//            mCache.put(url, bitmap);
//        }
//    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.gv_tree_layout, null);
            holder = new ViewHolder();
            holder.item_v_tree_iv = (ImageView) convertView.findViewById(R.id.item_v_tree_iv);
            convertView.setTag(holder);

        }
        holder = (ViewHolder) convertView.getTag();
        GetImageUrl2.CityImageBean td = data.get(position);


        //加载网络图片
//
//        ImageLoader.ImageListener l = imageloader.getImageListener(holder.item_v_tree_iv, R.drawable.android, R.drawable.android);
//        imageloader.get(td.getImageUrl(), l);
        imageLoader.displayImage(td.getImageUrl(), holder.item_v_tree_iv);

        return convertView;
    }


    class ViewHolder {
        ImageView item_v_tree_iv;
    }


}
