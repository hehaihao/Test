package com.xm6leefun.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Field;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2020/10/16 9:36
 */
@SuppressLint("NewApi")
public class NoMenuEditText extends AppCompatEditText {
    private final Context context;

    /**
     * 该方法为隐藏方法
     */
    boolean canPaste() {
        return false;
    }

    @Override
    public boolean isSuggestionsEnabled() {
        return false;
    }

    public NoMenuEditText(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public NoMenuEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public NoMenuEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        this.setLongClickable(false);
        //this.setTextIsSelectable(false);
        this.setCustomSelectionActionModeCallback(new ActionModeCallbackInterceptor());
        //使用该方式基本可以实现禁用粘贴复制功能,6.0以上可用
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            this.setCustomInsertionActionModeCallback(new ActionModeCallbackInterceptor());
//        }
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                NoMenuEditText.this.clearFocus();
                return false;
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // setInsertionDisabled when user touches the view
            this.setInsertionDisabled();
        }
        return super.onTouchEvent(event);
    }

    private void setInsertionDisabled() {
        try {
            Field editorField = TextView.class.getDeclaredField("mEditor");
            editorField.setAccessible(true);
            Object editorObject = editorField.get(this);

            @SuppressLint("PrivateApi") Class editorClass = Class.forName("android.widget.Editor");
            Field mInsertionControllerEnabledField = editorClass.getDeclaredField("mInsertionControllerEnabled");
            mInsertionControllerEnabledField.setAccessible(true);
            mInsertionControllerEnabledField.set(editorObject, false);
        } catch (Exception ignored) {
        }
    }

    /**
     * Prevents the action bar (top horizontal bar with cut,copy,paste,etc.)
     * from appearing by intercepting the callback that would cause it to be
     * created,and returning false.
     */
    private class ActionModeCallbackInterceptor implements ActionMode.Callback {

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            //删除复制选项
            MenuItem itemCopy = menu.findItem(android.R.id.copy);
            if (itemCopy != null) {
                menu.removeItem(android.R.id.copy);
            }
            //删除剪切选项
            MenuItem itemCut = menu.findItem(android.R.id.cut);
            if (itemCut != null) {
                menu.removeItem(android.R.id.cut);
            }
            return true;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        public void onDestroyActionMode(ActionMode mode) {
        }
    }
}
