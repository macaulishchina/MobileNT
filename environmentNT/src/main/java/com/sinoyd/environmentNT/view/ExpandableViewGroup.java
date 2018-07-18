package com.sinoyd.environmentNT.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sinoyd.environmentNT.adapter.WaterPmAdapter;
import com.sinoyd.environmentNT.adapter.WaterPmAdapter.WaterPmCallback;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.model.WaterPortInfo;

@SuppressLint("NewApi")
public class ExpandableViewGroup extends LinearLayout {
	private List<View> iconList;
	class ViewHolder {
		ImageView gIcon;
		TextView tvTitle;
		GridView gvPm;
	}

	public interface CallBack {
		void onBack();
	}

	private CallBack callBack;

	public void setCallBack(CallBack back) {
		this.callBack = back;
	}

	public void setMapData(Map<String, List<WaterPortInfo>> mapData) {
		removeAllViews();
		int i = 0;
		iconList.clear();
		views.clear();
		for (String title : mapData.keySet()) {
			View convertView = View.inflate(getContext(), R.layout.pm_item_title, null);
			ViewHolder holder = new ViewHolder();
			holder.gIcon = (ImageView) convertView.findViewById(R.id.gIcon);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
			holder.tvTitle.setText(title);
			holder.gvPm = (GridView) convertView.findViewById(R.id.gvPm);
			convertView.setTag(holder);
			iconList.add(holder.gIcon);
			views.add(holder.gvPm);
			final int index = i++;
			final List<WaterPortInfo> listData = mapData.get(title);
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (expendMap.containsKey(index) && expendMap.get(index)) {
						views.get(index).setVisibility(View.GONE);
						expendMap.put(index, false);
						((ImageView) iconList.get(index)).setImageResource(R.drawable.expend_default);
					}
					else {
						views.get(index).setVisibility(View.VISIBLE);
						WaterPmAdapter adapter = new WaterPmAdapter(listData);
						adapter.setPmCallback(new WaterPmCallback() {
							@Override
							public void onClick(WaterPortInfo info) {
								if (callBack != null)
									callBack.onBack();
							}
						});
						views.get(index).setAdapter(adapter);
						final int count = listData.size() / 4 + (listData.size() % 4 > 1 ? 1 : 0);
						if (count > 1)
							mHandler.postDelayed(new Runnable() {
								@Override
								public void run() {
									int height = ((LinearLayout.LayoutParams) views.get(index).getLayoutParams()).topMargin * 2 + count * (views.get(index).getChildAt(0).getHeight() + 30);
									LinearLayout.LayoutParams params = (LayoutParams) views.get(index).getLayoutParams();
									params.height = height;
									views.get(index).setLayoutParams(params);
								}
							}, 20);
						expendMap.put(index, true);
						((ImageView) iconList.get(index)).setImageResource(R.drawable.expended);
					}
				}
			});
			addView(convertView, 0);
		}
	}

	private Handler mHandler = new Handler();

	public ExpandableViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(VERTICAL);
		iconList = new ArrayList<View>();
	}

	public ExpandableViewGroup(Context context) {
		this(context, null);
	}

	@SuppressLint("UseSparseArrays")
	private Map<Integer, Boolean> expendMap = new HashMap<Integer, Boolean>();
	private List<GridView> views = new ArrayList<GridView>();
}
