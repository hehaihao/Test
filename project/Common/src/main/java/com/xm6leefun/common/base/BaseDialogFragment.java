package com.xm6leefun.common.base;


import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;

import com.xm6leefun.common.R;
import com.xm6leefun.common.widget.toast.ToastUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2020/9/23 9:23
 */
public class BaseDialogFragment extends DialogFragment {

    @Override
    public void show(FragmentManager manager, String tag) {
//        super.show(manager, tag);
        if (this.isAdded()) return;
        try{
            Class c = Class.forName("androidx.fragment.app.DialogFragment");
            Constructor con = c.getConstructor();
            Object obj = con.newInstance();
            Field dismissed = c.getDeclaredField("mDismissed");
            dismissed.setAccessible(true);
            dismissed.set(obj, false);
            Field shownByMe = c.getDeclaredField("mShownByMe");
            shownByMe.setAccessible(true);
            shownByMe.set(obj,true);
        }catch (Exception e){
            e.printStackTrace();
        }
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this,tag);
        ft.commitAllowingStateLoss();
    }



    @Override
    public void dismiss() {
//        super.dismiss();
        dismissAllowingStateLoss();
    }

    /**
     * 复制字符串
     */
    protected void copyStrings(String string) {
        ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager != null) {
            clipboardManager.setText(string);
        }
    }
}
