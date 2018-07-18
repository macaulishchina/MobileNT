package com.sinoyd.environmentNT.common;

import com.sinoyd.environmentNT.data.FactorProperty;

import java.util.List;

public class BizCommon {
	
	/**
	 * @功能描述：获取包含因子个数最大的索引
	 * @param mItemList
	 * @return
	 * @创建者： 
	 * @创建日期：2015年12月14日
	 * @维护人员：
	 * @维护日期：
	 */
	public static int GetMaxFactor(List<FactorProperty> mItemList)
	{
		int Max=0;
		int index=0;
		for(int i=0;i<mItemList.size();i++)
		{
			if(mItemList.get(i).factorNames.size()>Max)
			{
				   index=i;
				   Max=mItemList.get(i).factorNames.size();
		    }
		}
		return index;
		
		
		
	}
}
