package com.sinoyd.environmentNT.util;

import java.util.ArrayList;
import java.util.List;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.data.HealthTipDataInfo;

/***
 * 健康提醒工具类
 * 
 * @author smz
 * 
 */
public class HealTipUtil {
	private static String[] red_tips = { "建议佩戴口罩", "建议关闭窗户", "不宜户外运动", "需保护低抵抗力人群" };
	private static String[] green_tips = { "不必佩戴口罩", "适宜开窗通风", "适宜户外运动", "对低抵抗力人群无碍" };
	private static int[] red_bg = { R.drawable.daikouzhao, R.drawable.guanchuanghu, R.drawable.buyihuwaiyundong, R.drawable.baohurenquan };
	private static int[] green_bg = { R.drawable.bubidaikouzhao, R.drawable.kaichuanghu, R.drawable.huwaiyundong, R.drawable.lvserenqun };

	public static List<HealthTipDataInfo> getTipData(int pullectValue) {
		List<HealthTipDataInfo> list = new ArrayList<HealthTipDataInfo>();
		switch (PullectUtils.ValueByLevel(pullectValue)) {
		case 0:
		case 1:
			for (int i = 0; i < red_tips.length; i++) {
				list.add(new HealthTipDataInfo(green_bg[i], green_tips[i]));
			}
			break;
		case 2:
			for (int i = 0; i < red_tips.length; i++) {
				if (i == 0 || i == 1)
					list.add(new HealthTipDataInfo(green_bg[i], green_tips[i]));
				else
					list.add(new HealthTipDataInfo(red_bg[i], red_tips[i]));
			}
			break;
		case 3:
			for (int i = 0; i < red_tips.length; i++) {
				if (i == 0)
					list.add(new HealthTipDataInfo(green_bg[i], green_tips[i]));
				else
					list.add(new HealthTipDataInfo(red_bg[i], red_tips[i]));
			}
			break;
		case 4:
		case 5:
			for (int i = 0; i < red_tips.length; i++) {
				list.add(new HealthTipDataInfo(red_bg[i], red_tips[i]));
			}
			break;
		default:
			break;
		}
		return list;
	}
}
