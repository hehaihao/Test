package com.xm6leefun.common.utils;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * 项目：ocToken
 * 文件描述：输入框输入监听工具类
 * 作者：ljj
 * 创建时间：2020/4/30
 */
public class TextWatcherUtils {

    /**
     * 不输入中文
     */
    private static EditText mEd;
    public static void setNoCharactersTextWatcher(EditText editText) {
        mEd = editText;
        editText.addTextChangedListener(setNoCharactersTextWatcher);
    }

    private static TextWatcher setNoCharactersTextWatcher = new TextWatcher() {  // 不输入中文
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int i, int i1, int i2) {
//            TextUtil.textWatchDecimalLimit(mEd, s, 8);
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0) {
                for (int i = 0; i < s.length(); i++) {
                    char c = s.charAt(i);
                    if (c >= 0x4e00 && c <= 0X9fa5) { // 根据字节码判断
                        // 如果是中文，则清除输入的字符，否则保留
                        s.delete(i,i+1);
                    }
                }
            }

        }
    };

    /**
     * 不输入空格
     */
    public static void setNoSpaceTextWatcher(EditText editText) {
        mEd = editText;
        editText.addTextChangedListener(setNoSpaceTextWatcher);
    }
    private static TextWatcher setNoSpaceTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() > 0) {
                boolean b = editable.toString().endsWith(" ");
                // 如果是空格，则清除，否则保留
                if (b){
//                    ToastUtil.show("最后是空格！！！");
                    editable.delete(editable.length() - 1,editable.length());
                }
            }
        }
    };

    /**
     * 限制EditText只能输入空格和字母
     * @param editText
     */
    public static void setSpaceAndCharTextWatcher(EditText editText){
        editText.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if(cs.equals(" ")){ // for backspace
                            return cs;
                        }
                        if(cs.toString().matches("[a-zA-Z ]+")){
                            return cs;
                        }
                        return "";
                    }
                }
        });
    }


}
