package com.xm6leefun.common.widget.toast;

import com.taopao.rxtoast.RxToast;


/**
 * Created by hehaihao on 2019/5/8.
 */

public class ToastUtil {
	/**
	 * 设置自定义的Toast布局
	 */
	public static void showCenterToast(String msg) {
		RxToast.show(msg);
	}
}
