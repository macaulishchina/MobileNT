package com.sinoyd.environmentNT.util;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.sinoyd.environmentNT.Entity.YZCommonSelectModel;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.adapter.CommonSelectorAdapter;

import java.util.List;


/**
 * Created by wangyifan on 2017/8/24.
 * 选择器工具
 */

public class CommonSelector {
    public PopupWindow popupWindow;
    public View popupWindowView;
    private Activity activity;
    private View anchor;
    public OnSelectClickListener onSelect;

    ListView selectLv;
    List<YZCommonSelectModel> commonList;


    public CommonSelector(Activity activity, View v, List<YZCommonSelectModel> list, OnSelectClickListener callback) {
        this.activity = activity;
        this.anchor = v;
        this.commonList = list;
        this.onSelect = callback;
        initPop();
    }

    public void initPop() {
        popupWindowView = activity.getLayoutInflater().inflate(R.layout.yz_common_selector, null);
        popupWindow = new PopupWindow(popupWindowView, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //菜单背景色
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setOnDismissListener(new PopupDismissListener());
        initEvent();
    }

    private void initEvent() {
        selectLv = (ListView) popupWindowView.findViewById(R.id.select_lv);
        CommonSelectorAdapter commonAdapter = new CommonSelectorAdapter(activity, commonList);
        selectLv.setAdapter(commonAdapter);
        selectLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                onSelect.onCommonItemSelect(position);
            }
        });

    }

    public void showPop() {
        if (popupWindow.isShowing()) {
            return;
        }
        if (commonList.size() > 7) {
            popupWindow.setHeight(800);
        }
        //显示位置
        popupWindow.showAsDropDown(anchor);
        //backgroundAlpha(0.5f);
    }

    public void dismissPop() {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，将背景透明度复原
     */
    class PopupDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
            //选择回调
            // selectCallBack.onColorChange(fontColor,fontSize);
        }

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 公共选择器接口
     */
    public interface OnSelectClickListener {
        void onCommonItemSelect(int position);
    }
}
