//package com.sinoyd.environmentNT.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import com.sinoyd.environmentNT.AppConfig.PageBg;
//import com.sinoyd.environmentNT.R;
//
///***
// * 皮肤界面的适配器
// *
// * @author smz
// *
// */
//public class UpdateFaceAdapter extends BaseAdapter {
//	private LayoutInflater mLayoutInflater;
//	private int useIndex;
//	private boolean release;
//
//	public UpdateFaceAdapter(Context context) {
//		mLayoutInflater = LayoutInflater.from(context);
//		release = false;
//	}
//
//	public void setUseIndex(int useIndex) {
//		this.useIndex = useIndex;
//	}
//
//	public void setRelease(boolean release) {
//		this.release = release;
//		notifyDataSetChanged();
//	}
//
//	@Override
//	public int getCount() {
//		return PageBg.PAGE_BG_NAME.length;
//	}
//
//	@Override
//	public Object getItem(int position) {
//		return PageBg.PAGE_BG_NAME[position];
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return position;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		ViewHolder viewHolder;
//		if (convertView == null) {
//			convertView = mLayoutInflater.inflate(R.layout.item_update_face, null);
//			viewHolder = new ViewHolder();
//			viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView1);
//			convertView.setTag(viewHolder);
//		}
//		else {
//			viewHolder = (ViewHolder) convertView.getTag();
//		}
//		if (position == useIndex) {
//			viewHolder.imageView.setImageResource(PageBg.PAGE_BG_EXAMPLE_IMAGE_USE[position]);
//		}
//		else {
//			viewHolder.imageView.setImageResource(PageBg.PAGE_BG_EXAMPLE_IMAGE[position]);
//		}
//		if (release) {
//			viewHolder.imageView.setImageResource(0);
//		}
//		// if(position == useIndex){
//		// Bitmap bit =
//		// BitmapFactory.decodeResource(parent.getContext().getResources(),
//		// PageBg.PAGE_BG_EXAMPLE_IMAGE_USE[position]);
//		// viewHolder.imageView.setImageBitmap(bit);
//		// bit = null;
//		// }else{
//		// Bitmap bit =
//		// BitmapFactory.decodeResource(parent.getContext().getResources(),
//		// PageBg.PAGE_BG_EXAMPLE_IMAGE[position]);
//		// viewHolder.imageView.setImageBitmap(bit);
//		// bit = null;
//		// }
//		return convertView;
//	}
//
//	private static class ViewHolder {
//		public ImageView imageView;
//	}
//}
