package com.sinoyd.environmentNT.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.RadioGroup;
import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.adapter.FragmentTabAdapter;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.view.MenuButton;
import com.sinoyd.environmentNT.view.MenuGroup;

/**
 * 更多 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：MoreMenuActivity

 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class MoreMenuActivity extends FragmentActivity {
	/** 底部菜单 **/
	private MenuGroup menuGroup;
	public List<BaseFragment> fragments = new ArrayList<BaseFragment>();
	/** 底部菜单适配器 **/
	private FragmentTabAdapter tabAdapter;
	public static int selelctIndex;
	private static final String[] TAB_AIR_TITLES = { "更多" };
	private static final int[] TAB_AIR_ICONS = { R.drawable.tab_gengduo };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(MyApplication.currentThemeId);
		setContentView(R.layout.activity_moremenu);
		fragments.add(new MoreActivity());
		tabAdapter = new FragmentTabAdapter(this, fragments, R.id.tab_content);
		menuGroup = ((MenuGroup) findViewById(R.id.more_menuGroup));
		findViewById(R.id.more_menuGroup).setVisibility(View.VISIBLE);
		for (int i = 0; i < menuGroup.getChildCount(); i++) {
			((MenuButton) menuGroup.getChildAt(i)).setText(TAB_AIR_TITLES[i]);
			((MenuButton) menuGroup.getChildAt(i)).setICON(TAB_AIR_ICONS[i]);
		}
		menuGroup.setOnMenuItemClickListener(tabAdapter);
		tabAdapter.setOnRgsExtraCheckedChangedListener(new FragmentTabAdapter.OnRgsExtraCheckedChangedListener() {
			@Override
			public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
				selelctIndex = index;
				System.out.println("Extra---- " + index + " checked!!! ");
			}
		});
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	//	startActivity(new Intent(MoreMenuActivity.this, MainActivity.class));
		MoreMenuActivity.this.finish();
	}
}