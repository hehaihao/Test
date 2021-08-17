package com.xm6leefun.common.utils;

import android.widget.EditText;

/**
 * 项目：ChatfengIM
 * 文件描述：文字工具类
 * 作者：ljj
 * 创建时间：2019/9/18
 */
public class TextUtil {

    /**
     * 限制小数位数以及小数形式
     *
     * @param editText
     * @param s
     * @param limitCounts 小数后位数
     */
    public static void textWatchDecimalLimit(EditText editText, CharSequence s, int limitCounts) {
        //删除.后面超过limitCounts位的数字
        if (s.toString().contains(".")) {
            if (s.length() - 1 - s.toString().indexOf(".") > limitCounts) {
                s = s.toString().subSequence(0,
                        s.toString().indexOf(".") + limitCounts + 1);
                editText.setText(s);
                editText.setSelection(s.length());
            }
        }

        //如果.在起始位置,则起始位置自动补0
        if (s.toString().trim().substring(0).equals(".")) {
            s = "0" + s;
            editText.setText(s);
            editText.setSelection(2);
        }

        //如果起始位置为0并且第二位跟的不是".",则无法后续输入
        if (s.toString().startsWith("0")
                && s.toString().trim().length() > 1) {
            if (!s.toString().substring(1, 2).equals(".")) {
                editText.setText(s.subSequence(0, 1));
                editText.setSelection(1);
                return;
            }
        }
    }


}
