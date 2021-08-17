package com.xm6leefun.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xm6leefun.common.R;
import com.xm6leefun.common.base.BaseDialogFragment;
import com.xm6leefun.common.utils.LogUtil;
import com.xm6leefun.common.utils.StrUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * @Description:弹框
 * @Author: ljj
 * @CreateDate: 2020/9/24 17:50
 */
public class HintDialog extends BaseDialogFragment implements View.OnClickListener {
    public static final String TAG = HintDialog.class.getSimpleName();
    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static final String SURE_TEXT = "sureText";
    private static final String IS_CENTER_GRAVITY = "is_center_gravity";
    private ClickListener clickListener;
    private Context mContext;
    private TextView title_tv;
    private TextView content_tv;

    public static HintDialog getInstance(String title, String content, String sureText, boolean isCenterGravity, ClickListener clickRightListener){
        HintDialog hintDialog = new HintDialog();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(CONTENT, content);
        args.putString(SURE_TEXT, sureText);
        args.putBoolean(IS_CENTER_GRAVITY, isCenterGravity);
        hintDialog.setArguments(args);
        hintDialog.setClickListener(clickRightListener);
        return hintDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if(args == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.hint_dialog_layout,null);
        title_tv = view.findViewById(R.id.title_tv);
        content_tv = view.findViewById(R.id.content_tv);
        TextView cancel_tv = view.findViewById(R.id.cancel_tv);
        TextView sure_tv = view.findViewById(R.id.sure_tv);
        cancel_tv.setOnClickListener(this);
        sure_tv.setOnClickListener(this);

        if(args != null){
            String title = args.getString(TITLE);
            String content = args.getString(CONTENT);
            String sureText = args.getString(SURE_TEXT);
            boolean isCenterGravity = args.getBoolean(IS_CENTER_GRAVITY,true);
            LogUtil.i("content==" + content);
            if (!StrUtils.isEmpty(content)){
                content_tv.setText(content + "");
            }
            if(isCenterGravity){
                content_tv.setGravity(Gravity.CENTER);
            }else{
                content_tv.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            }
            if (!StrUtils.isEmpty(sureText)){
                sure_tv.setText(sureText + "");
            }

            if (StrUtils.isEmpty(title)){
                title_tv.setVisibility(View.GONE);
            } else {
                title_tv.setVisibility(View.VISIBLE);
                title_tv.setText(title + "");
            }
        }
        Dialog dialog = new Dialog(mContext,R.style.bottom_dialog_style);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if(dialog != null) {
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.gravity = Gravity.CENTER; //将对话框放到布局中间，也就是屏幕中间
            dialog.getWindow().setAttributes((WindowManager.LayoutParams) params);
        }
    }

    public void setClickListener(ClickListener clickRightListener){
        this.clickListener = clickRightListener;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.cancel_tv) {
            dismiss();
        } else if (id == R.id.sure_tv) {
            if (clickListener != null) {
                clickListener.onRightClick();
            }
            dismiss();
        }
    }

    public static class Builder{
        private String title;
        private String content;
        private String sureText;
        private boolean isCenterGravity = true;
        private ClickListener clickRightListener;

        public Builder setTitle(String title){
            this.title = title;
            return this;
        }
        public Builder setContent(String content){
            this.content = content;
            return this;
        }
        public Builder setSureText(String sureText){
            this.sureText = sureText;
            return this;
        }
        public Builder setIsCenterGravity(boolean isCenterGravity){
            this.isCenterGravity = isCenterGravity;
            return this;
        }
        public Builder setClickListener(ClickListener clickRightListener){
            this.clickRightListener = clickRightListener;
            return this;
        }
        public HintDialog build(){
            return HintDialog.getInstance(title, content,sureText,isCenterGravity,clickRightListener);
        }
    }

    public interface ClickListener{
        void onRightClick();
    }

}
