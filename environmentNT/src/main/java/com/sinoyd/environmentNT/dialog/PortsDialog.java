package com.sinoyd.environmentNT.dialog;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.sinoyd.environmentNT.AppConfig;
import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.activity.PositionManagerActivity;
import com.sinoyd.environmentNT.adapter.PortExpandableListAdapter;
import com.sinoyd.environmentNT.model.PortInfo;
import com.sinoyd.environmentNT.util.BracaseUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 站点选择弹出框 Copyright (c) 2015 江苏远大信息股份有限公司
 *
 * @类型名称：PortsDialog
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class PortsDialog extends AlertDialog {
    private ExpandableListView valuelist = null;
    /**
     * 站点信息列表
     **/
    private List<PortInfo> portInfoList;
    /**
     * 站点信息选择监听事件
     **/
    private PortItemSelectListener portItemSelectListener;

    private PortTypeSelectListener portTypeSelectListener;
    private Context mContext;
    /**
     * 弹出适配器
     **/
    private PortExpandableListAdapter adapter = null;
    List<Map<String, Object>> groups = new ArrayList<Map<String, Object>>();
    List<String> diffAirPortType;
    List<String> diffWaterPortType;
    List<List<Map<String, Object>>> childs = new ArrayList<List<Map<String, Object>>>();
    Map<String, ObjectInfo> childsdelete = new HashMap<String, ObjectInfo>();

    public PortsDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);

    }

    public PortsDialog(Context context, int theme, List<PortInfo> portInfoList) {
//		super(context, R.style.Theme_Dialog_Transparent);
        super(context);
        this.mContext = context;
        this.portInfoList = portInfoList;

    }

    public PortsDialog(Context context) {
        super(context);

    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (AppConfig.isWatherSystem())
                NotifyUpdateWater(intent);
            else
                NotifyUpdate(intent);
        }
    };

    public class ObjectInfo extends Object {
        public String m_Name;
        public int m_Group;
        public int m_Child;

        public ObjectInfo(String Name, int Group, int Child) {
            m_Name = Name;
            m_Group = Group;
            m_Child = Child;
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
//        if (receiver != null)
//            try {
//
//                BracaseUtils.unregister(mContext, receiver);
//
//            } catch (IllegalArgumentException e) {
//
//            }

//        BracaseUtils.unregister(mContext, receiver);

    }

    /***
     * 监听事件的处理，修改站点的数量
     *
     * @param intent
     */
    public void NotifyUpdate(Intent intent) {
        if (BracaseUtils.ADD_ACTION.equalsIgnoreCase(intent.getAction())) {
//				PositionDBModel pd = mDB.findFirst(Selector.from(PositionDBModel.class).orderBy("id", true));
//				PortInfo i = new PortInfo();
//				i.PortId = pd.getPortId();
//				i.PortName = pd.getPortName();
//				listPort.add(i);
//				addRealTimeView(Integer.parseInt(i.PortId));
//				PortInfo portInfo = null;
//				try {
//					portInfo = MyApplication.mDB.findFirst(Selector.from(PortInfo.class).where("PortId", "=", Integer.parseInt(intent.getStringExtra("portId"))));
//				} catch (DbException e) {
//					e.printStackTrace();
//				}
//				  for(int i=0;i<diffAirPortType.size();i++)
//				  {
//					 List<PortInfo> portInfoAirOrders=null;
//					  try {
//						  portInfoAirOrders = MyApplication.mDB.findAll(Selector.from(PortInfo.class).where("PortType", "=", diffAirPortType.get(i)).and("isWaterPort", "=", "0").orderBy("orderNumber",true));
//					     for(PortInfo portInfo:portInfoAirOrders)
//						 {
//							 if(portInfo.getPortId().equals(intent.getStringExtra("portId")))
//							 {
//								 for(int j=0;j<childs.get(i).size();j++)
//									 childs.get(i).a
//								 }
//							 }
//						 }
//
//					  catch (DbException e) {
//						  // TODO Auto-generated catch block
//						  e.printStackTrace();
//					  }
//				  }
//            PortInfo portInfo = null;
//            try {
//                portInfo = MyApplication.mDB.findFirst(Selector.from(PortInfo.class).where("PortId", "=", Integer.parseInt(intent.getStringExtra("portId"))));
//            } catch (DbException e) {
//                e.printStackTrace();
//            }

            for (String key : childsdelete.keySet()) {
                if (childsdelete.get(key).m_Name.equals(intent.getStringExtra("portName"))) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("portName", intent.getStringExtra("portName"));
                    Log.d("childsdelete_addbefore", key);
                    Log.d("childsdelete_addgroup", String.valueOf(childsdelete.get(key).m_Group));
                    Log.d("childsdelete_addchild", String.valueOf(childsdelete.get(key).m_Child));
                    Log.d("childsdelete_size", String.valueOf(childs.size()));
                    if (childsdelete.get(key).m_Child + 1 > childs.size())
                        childs.get(childsdelete.get(key).m_Group).add(map);
                    else
                        childs.get(childsdelete.get(key).m_Group).add(childsdelete.get(key).m_Child, map);

                    Log.d("childsdelete_addafter", key);


                }
            }
            childsdelete.remove(intent.getStringExtra("portName"));
            adapter.notifyDataSetChanged();

        } else if (BracaseUtils.REMOVE_ACTION.equalsIgnoreCase(intent.getAction())) {
//			removeRealTimeView(Integer.parseInt(intent.getStringExtra("portId")));
//			List<Map<String, Object>> childWater = new ArrayList<Map<String, Object>>();
//            PortInfo portInfo = null;
//            try {
//                portInfo = MyApplication.mDB.findFirst(Selector.from(PortInfo.class).where("PortId", "=", Integer.parseInt(intent.getStringExtra("portId"))));
//            } catch (DbException e) {
//                e.printStackTrace();
//            }

//			for (List<Map<String, Object>> schild : childs) {
            for (int i = 0; i < childs.size(); i++) {
                {
//				        for(Map<String,Object> childmap:schild)
                    List<Map<String, Object>> schild = childs.get(i);

                    for (int j = 0; j < schild.size(); j++) {

                        if (schild.get(j).get("portName").toString().equals(intent.getStringExtra("portName"))) {
                            Log.d("childsdelete_delete", schild.get(j).get("portName").toString());
                            childsdelete.put(schild.get(j).get("portName").toString(), new ObjectInfo(schild.get(j).get("portName").toString(), i, j));
                            schild.remove(j);

                            break;
                        }
                    }
                }
            }

            adapter.notifyDataSetChanged();
        }

    }


    /***
     * 监听事件的处理，修改站点的数量
     *
     * @param intent
     */
    public void NotifyUpdateWater(Intent intent) {
        if (BracaseUtils.ADD_WATER_ACTION.equalsIgnoreCase(intent.getAction())) {


            for (String key : childsdelete.keySet()) {
                if (childsdelete.get(key).m_Name.equals(intent.getStringExtra("portName"))) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("portName", intent.getStringExtra("portName"));
                    Log.d("childsdelete_addbefore", key);
                    Log.d("childsdelete_addgroup", String.valueOf(childsdelete.get(key).m_Group));
                    Log.d("childsdelete_addchild", String.valueOf(childsdelete.get(key).m_Child));
                    Log.d("childsdelete_size", String.valueOf(childs.size()));
                    if (childsdelete.get(key).m_Child + 1 > childs.size())
                        childs.get(childsdelete.get(key).m_Group).add(map);
                    else
                        childs.get(childsdelete.get(key).m_Group).add(childsdelete.get(key).m_Child, map);

                    Log.d("childsdelete_addafter", key);


                }
            }
            childsdelete.remove(intent.getStringExtra("portName"));

            adapter.notifyDataSetChanged();

        } else if (BracaseUtils.REMOVE_WATER_ACTION.equalsIgnoreCase(intent.getAction())) {


//			for (List<Map<String, Object>> schild : childs) {
            for (int i = 0; i < childs.size(); i++) {
                {
//				        for(Map<String,Object> childmap:schild)
                    List<Map<String, Object>> schild = childs.get(i);

                    for (int j = 0; j < schild.size(); j++) {

                        if (schild.get(j).get("portName").toString().equals(intent.getStringExtra("portName"))) {

                            childsdelete.put(schild.get(j).get("portName").toString(), new ObjectInfo(schild.get(j).get("portName").toString(), i, j));
                            schild.remove(j);
                            break;
                        }
                    }
                }
            }

            adapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_portinfo);
        valuelist = (ExpandableListView) findViewById(R.id.site_list_list);
        findViewById(R.id.txt_station).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PositionManagerActivity.class);
                mContext.startActivity(intent);
            }
        });
        // 水、气站点PortType
        Map<String, Object> groupair = new HashMap<String, Object>();
//        if (AppConfig.isWatherSystem()) {
//            // 搜索出水的站点的portType
//            List<PortInfo> portInfoWater = new ArrayList<PortInfo>();
//            try {
//                portInfoWater = MyApplication.mDB.findAll(Selector.from(PortInfo.class).where("isWaterPort", "=", "1").orderBy("orderNumber", true));
//            } catch (DbException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            diffWaterPortType = getDiffPortTypeName(portInfoWater);
//            for (int i = 0; i < diffWaterPortType.size(); i++) {
//                groupair = new HashMap<String, Object>();
//                groupair.put("portType", diffWaterPortType.get(i));
//                groups.add(groupair);
//                // 搜索出水的站点的不同portType的数据并排序
//                List<Map<String, Object>> childWater = new ArrayList<Map<String, Object>>();
//                List<PortInfo> portInfoWaterOrders = new ArrayList<PortInfo>();
//                try {
//                    portInfoWaterOrders = MyApplication.mDB.findAll(Selector.from(PortInfo.class).where("PortType", "=", diffWaterPortType.get(i)).and("isWaterPort", "=", "1").orderBy("orderNumber", true));
//                } catch (DbException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                Map<String, Object> childWaterSecond = null;
//                for (int j = 0; j < portInfoWaterOrders.size(); j++) {
//                    childWaterSecond = new HashMap<String, Object>();
//                    childWaterSecond.put("portName", portInfoWaterOrders.get(j).PortName);
//                    childWater.add(childWaterSecond);
//                }
//                if (childWater.size() > 0) {
//                    childs.add(childWater);
//                }
//            }
//        } else {
        // 搜索出气的站点的portType
        List<PortInfo> portInfoAir = new ArrayList<PortInfo>();
        try {
            portInfoAir = MyApplication.mDB.findAll(Selector.from(PortInfo.class).where("isWaterPort", "=", "0"));
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        diffAirPortType = getDiffPortTypeName(portInfoAir);


        for (int i = 0; i < diffAirPortType.size(); i++) {
            groupair = new HashMap<String, Object>();
            groupair.put("RegionType", diffAirPortType.get(i));
            groups.add(groupair);
            // 搜索出气的站点的不同portType的数据并排序
            List<Map<String, Object>> childAir = new ArrayList<Map<String, Object>>();
            List<PortInfo> portInfoAirOrders = new ArrayList<PortInfo>();
            try {
                portInfoAirOrders = MyApplication.mDB.findAll(Selector.from(PortInfo.class).where("RegionType", "=", diffAirPortType.get(i)).and("isWaterPort", "=", "0").orderBy("orderNumber", true));
            } catch (DbException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Map<String, Object> childAirSecond = null;

            /**
             * 除去重复
             * scj 2017年8月18日增加  portInfoAirOrders
             * */

            for (int a = 0; a < portInfoAirOrders.size() - 1; a++) {
                for (int b=a+1; b < portInfoAirOrders.size(); b++) {


                    if (portInfoAirOrders.get(b).PortId.equals(portInfoAirOrders.get(a).PortId)) {

                        portInfoAirOrders.remove(b);

                    }


                }
            }

/**
 * * 除去重复*/
            for (int j = 0; j < portInfoAirOrders.size(); j++) {
                childAirSecond = new HashMap<String, Object>();
                childAirSecond.put("portName", portInfoAirOrders.get(j).PortName);
                childAir.add(childAirSecond);
            }
            if (childAir.size() > 0) {
                childs.add(childAir);
            }

        }
//        }

        adapter = new PortExpandableListAdapter(mContext, groups, childs);
        valuelist.setAdapter(adapter);
        valuelist.setGroupIndicator(null);
        valuelist.setGroupIndicator(mContext.getResources().getDrawable(R.drawable.group_icon_selector));
//		for (int i = 0; i < adapter.getGroupCount(); i++) {
//			valuelist.expandGroup(i);
//		}
        valuelist.expandGroup(0);
        // 设置监听器
        this.valuelist.setOnChildClickListener(new OnChildClickListener() {
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String selectPortName = adapter.getSelectItemPortName(groupPosition, childPosition);
                if (portItemSelectListener != null) {
                    for (int i = 0; i < portInfoList.size(); i++) {
                        if (portInfoList.get(i).PortName.equals(selectPortName)) {
                            portItemSelectListener.selectPortinfo(portInfoList.get(i));
                            dismiss();
                        }
                    }
                }
                return true;
            }
        });


        this.valuelist.setOnGroupExpandListener(new OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0; i < adapter.getGroupCount(); i++) {
                    // ensure only one expanded Group exists at every time
//					    if (groupPosition != i && isGroupExpanded(groupPosition)) {
//					    	 
//					       }
                    if (groupPosition != i) {
                        valuelist.collapseGroup(i);

                    }
                }
                String selectPortType = adapter.getSelectItemPortType(groupPosition);
                if (portTypeSelectListener != null) {
                    for (int i = 0; i < groups.size(); i++) {
                        if (((String) groups.get(i).get("RegionType")).equals(selectPortType)) {
                            portTypeSelectListener.selectPortTypeinfo((String) groups.get(i).get("RegionType"));
                            dismiss();
                        }
                    }
                }
            }
        });
        if (AppConfig.isWatherSystem())
            BracaseUtils.registerWater(mContext, receiver, 400);
        else
            BracaseUtils.register(mContext, receiver, 400);
//		this.valuelist.setOnGroupClickListener(new OnGroupClickListener() {
//			@Override
//			public boolean onGroupClick(ExpandableListView parent, View v,
//					int groupPosition, long id) {
//				
//				
//				return false;
//			}
//		});
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            if (AppConfig.isWatherSystem())
                BracaseUtils.registerWater(mContext, receiver, 400);
            else
                BracaseUtils.register(mContext, receiver, 400);

        } catch (IllegalArgumentException e) {

        }
    }

    /*
             * 搜索出不同站点类型
             */
    public static List<String> getDiffPortTypeName(List<PortInfo> mPortInfo) {
        List<String> mStrings = new ArrayList<String>();
        for (PortInfo string : mPortInfo) {
            if (!mStrings.contains(string.RegionType)) {
                mStrings.add(string.RegionType);
            }
        }
        return mStrings;
    }

    public void setPortItemSelectListener(PortItemSelectListener portItemSelectListener) {
        this.portItemSelectListener = portItemSelectListener;
    }

    public void setPortTypeSelectListener(PortTypeSelectListener portTypeSelectListener) {
        this.portTypeSelectListener = portTypeSelectListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dismiss();
        return super.onTouchEvent(event);
    }

    public static interface PortItemSelectListener {
        void selectPortinfo(PortInfo portInfo);
    }

    public interface PortTypeSelectListener {
        void selectPortTypeinfo(String PortType);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (receiver != null)
            try {

                BracaseUtils.unregister(mContext, receiver);

            } catch (IllegalArgumentException e) {

            }

//        BracaseUtils.unregister(mContext, receiver);
    }
}
