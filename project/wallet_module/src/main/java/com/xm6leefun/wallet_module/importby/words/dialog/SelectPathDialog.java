package com.xm6leefun.wallet_module.importby.words.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xm6leefun.common.base.BaseDialogFragment;
import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.importby.words.adapter.SelectPathAdapter;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Description:选择路径
 * @Author: hhh
 * @CreateDate: 2021/3/27 12:38
 */
public class SelectPathDialog extends BaseDialogFragment implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener {
    public static final String TAG = SelectPathDialog.class.getSimpleName();
    private static final String SELECT_PATH = "select_path";
    private ClickListener clickListener;
    private Context mContext;
    private List<String> paths;

    public static SelectPathDialog getInstance(String selectPath, ClickListener clickListener){
        SelectPathDialog selectNetDialog = new SelectPathDialog();
        Bundle args = new Bundle();
        args.putString(SELECT_PATH,selectPath);
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.select_path_dialog_layout,null);
        RecyclerView recycler = view.findViewById(R.id.recycler);
        TextView cancel_tv = view.findViewById(R.id.cancel_tv);
        cancel_tv.setOnClickListener(this);
        if(args != null){
            String selectPath = args.getString(SELECT_PATH,"");
            paths = Arrays.asList(getResources().getStringArray(R.array.only_paths));
            SelectPathAdapter adapter = new SelectPathAdapter(R.layout.item_select_path,paths,selectPath,getActivity());
            recycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
            recycler.setAdapter(adapter);
            adapter.setOnItemClickListener(this);
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
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        String selectPath = paths.get(position);
        if(clickListener != null){
            SelectPathAdapter selectPathAdapter = (SelectPathAdapter)adapter;
            selectPathAdapter.setCurrSelectPath(selectPath);
            selectPathAdapter.notifyDataSetChanged();
            clickListener.select(selectPath, position == 0);
            dismiss();
        }
    }

    public static class Builder{
        private String selectPath;
        private ClickListener clickListener;

        public Builder setSelectPath(String selectPath){
            this.selectPath = selectPath;
            return this;
        }

        public Builder setClickListener(ClickListener clickListener){
            this.clickListener = clickListener;
            return this;
        }
        public SelectPathDialog build(){
            return SelectPathDialog.getInstance(selectPath,clickListener);
        }
    }

    /**
     * 暴露接口实现回调
     */
    public interface ClickListener{
        /**
         * 选中的路径
         * @param path
         */
        void select(String path, boolean isDefault);
    }
}
