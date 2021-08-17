package com.xm6leefun.common.dialog;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import com.xm6leefun.common.R;
import com.xm6leefun.common.base.BaseDialogFragment;
import com.xm6leefun.common.utils.SavePhoto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * @Description:收款码提示窗口
 * @Author: hhh
 * @CreateDate: 2020/9/21 16:40
 */
public class CollectionCodeDialog extends BaseDialogFragment implements View.OnClickListener {
    public static final String TAG = CollectionCodeDialog.class.getSimpleName();
    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static final String CODE = "code";
    private static final String ADDRESS = "address";
    private static final String NAME = "name";
    private ClickListener clickListener;
    private Context mContext;

    public static CollectionCodeDialog getInstance(
            String title,
            String content,
            String code,
            String address,
            String name,
            ClickListener clickListener){
        CollectionCodeDialog selectNetDialog = new CollectionCodeDialog();
        Bundle args = new Bundle();
        args.putString(TITLE,title);
        args.putString(CONTENT,content);
        args.putString(CODE,code);
        args.putString(ADDRESS,address);
        args.putString(NAME,name);
        selectNetDialog.setArguments(args);
        selectNetDialog.setClickListener(clickListener);
        return selectNetDialog;
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.collection_code_dialog_layout,null);


        TextView title_tv = view.findViewById(R.id.title_tv);
        ConstraintLayout root_view = view.findViewById(R.id.root_view);
        TextView content_tv = view.findViewById(R.id.content_tv);
        ImageView code_iv = view.findViewById(R.id.code_iv);
        TextView address_tv = view.findViewById(R.id.address_tv);
        TextView name_tv = view.findViewById(R.id.name_tv);
        TextView cancel_tv = view.findViewById(R.id.cancel_tv);

        ImageView close_iv = view.findViewById(R.id.close_iv);

        cancel_tv.setOnClickListener(this);
        close_iv.setOnClickListener(this);

        if(args != null){
            String title = args.getString(TITLE,"");
            String content = args.getString(CONTENT,"");
            String code = args.getString(CODE,"");
            String address = args.getString(ADDRESS,"");
            String name = args.getString(NAME,"");
            title_tv.setText(title);
            content_tv.setText(content);
            address_tv.setText(address);
            name_tv.setText(name);
            Bitmap bitmap = SavePhoto.createQrCodeImg(code, 220);
            if(bitmap!=null)code_iv.setImageBitmap(bitmap);
        }
        Dialog dialog = new Dialog(mContext,R.style.bottom_nopadding_dialog_style);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnShowListener(dialog1 -> savePhoto(root_view));
        return dialog;
    }

    private void savePhoto(ViewGroup root_view) {
        String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
//检测是否有写的权限
        int permission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // 没有写的权限，去申请写的权限，会弹出对话框
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, 1);
        } else {
            saveQrCode(mContext,root_view);
        }
    }

    /**
     * 保存图片
     * @param mContext
     * @param view
     * @return
     */
    public void saveQrCode(Context mContext, View view) {
        SavePhoto savePhoto = new SavePhoto(mContext);
        savePhoto.SaveBitmapFromView(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if(dialog != null) {
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.gravity = Gravity.BOTTOM; //将对话框放到布局下面，也就是屏幕下方
            dialog.getWindow().setAttributes((WindowManager.LayoutParams) params);
        }
    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancel_tv) {
            dismiss();
        }else if (v.getId() == R.id.close_iv){
            dismiss();
        }
    }

    public static class Builder{
        private String title;
        private String content;
        private String code;
        private String address;
        private String name;
        private ClickListener clickListener;

        public Builder setTitle(String title){
            this.title = title;
            return this;
        }
        public Builder setContent(String content){
            this.content = content;
            return this;
        }

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setClickListener(ClickListener clickListener){
            this.clickListener = clickListener;
            return this;
        }
        public CollectionCodeDialog build(){
            return CollectionCodeDialog.getInstance(title,content,code,address,name,clickListener);
        }
    }

    public interface ClickListener{
        void more(String imagePath);
    }
}
