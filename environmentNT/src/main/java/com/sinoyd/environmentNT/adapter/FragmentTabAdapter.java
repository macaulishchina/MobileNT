package com.sinoyd.environmentNT.adapter;

import java.util.List;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioGroup;
import com.sinoyd.environmentNT.activity.BaseFragment;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.view.MenuGroup.OnMenuItemClickListener;

/**
 * 底部菜单适配器 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：FragmentTabAdapter

 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class FragmentTabAdapter implements OnMenuItemClickListener {
	/** 一个tab页面对应一个Fragment **/
	private List<BaseFragment> fragments;
	/** Fragment所属的Activity **/
	private FragmentActivity fragmentActivity;
	/** Activity中所要被替换的区域的id **/
	private int fragmentContentId;
	/** 当前Tab页面索引 **/
	private int currentTab;
	/** 用于让调用者在切换tab时候增加新的功能 **/
	private OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener;

	public FragmentTabAdapter(FragmentActivity fragmentActivity, List<BaseFragment> fragments, int fragmentContentId) {
		this.fragments = fragments;
		this.fragmentActivity = fragmentActivity;
		this.fragmentContentId = fragmentContentId;
		// 默认显示第一页
		FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
		ft.add(fragmentContentId, fragments.get(0));
		ft.commit();
	}

	/**
	 * 切换tab
	 * 
	 * @param idx
	 */
	public void showTab(int idx) {
		for (int i = 0; i < fragments.size(); i++) {
			Fragment fragment = fragments.get(i);
			FragmentTransaction ft = obtainFragmentTransaction(idx);
			if (idx == i) {
				ft.show(fragment);
			}
			else {
				ft.hide(fragment);
			}
			ft.commit();
		}
		currentTab = idx; // 更新目标tab为当前tab
	}

	/**
	 * 获取一个带动画的FragmentTransaction
	 * 
	 * @param index
	 * @return
	 */
	private FragmentTransaction obtainFragmentTransaction(int index) {
		FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
		// 设置切换动画
		if (index > currentTab) {
			ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
		}
		else {
			ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out);
		}
		return ft;
	}

	public int getCurrentTab() {
		return currentTab;
	}

	public Fragment getCurrentFragment() {
		return fragments.get(currentTab);
	}

	public OnRgsExtraCheckedChangedListener getOnRgsExtraCheckedChangedListener() {
		return onRgsExtraCheckedChangedListener;
	}

	public void setOnRgsExtraCheckedChangedListener(OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener) {
		this.onRgsExtraCheckedChangedListener = onRgsExtraCheckedChangedListener;
	}

	/**
	 * 切换tab额外功能功能接口
	 */
	public static class OnRgsExtraCheckedChangedListener {
		public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
		}
	}

	@Override
	public void onItemClick(int position, View v) {
		BaseFragment fragment = fragments.get(position);
		FragmentTransaction ft = obtainFragmentTransaction(position);
		getCurrentFragment().onPause(); // 暂停当前tab
		// getCurrentFragment().onStop(); // 暂停当前tab
		if (fragment.isAdded()) {
			// fragment.onStart(); // 启动目标tab的onStart()
			fragment.onResume(); // 启动目标tab的onResume()
		}
		else {
			ft.add(fragmentContentId, fragment);
		}
		showTab(position); // 显示目标tab
		ft.commit();
	}
}
