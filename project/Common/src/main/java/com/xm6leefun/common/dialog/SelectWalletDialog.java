package com.xm6leefun.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.util.SmartUtil;
import com.xm6leefun.common.R;
import com.xm6leefun.common.base.BaseDialogFragment;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.dialog.adapter.SelectWalletAdapter;
import com.xm6leefun.common.refresh_view.itemdecora.SpacesItemDecoration;
import com.xm6leefun.common.widget.toast.ToastUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Description:选择钱包
 * @Author: hhh
 * @CreateDate: 2020/9/21 16:40
 */
public class SelectWalletDialog extends BaseDialogFragment implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener, SelectWalletAdapter.CopyListener {
    public static final String TAG = SelectWalletDialog.class.getSimpleName();
    private static final String SELECT_ADDRESS = "select_address";
    private ClickListener clickListener;
    private Context mContext;
    private List<Wallet_Main> walletList;
    private String selectAddress;

    public static SelectWalletDialog getInstance(String selectAddress, List<Wallet_Main> walletMains, ClickListener clickListener){
        SelectWalletDialog selectNetDialog = new SelectWalletDialog();
        Bundle args = new Bundle();
        args.putString(SELECT_ADDRESS,selectAddress);
        selectNetDialog.setArguments(args);
        selectNetDialog.setClickListener(clickListener);
        selectNetDialog.setWalletList(walletMains);
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.select_wallet_dialog_layout,null);
        RecyclerView recycler = view.findViewById(R.id.recycler);
        ImageView close_btn = view.findViewById(R.id.close_btn);
        close_btn.setOnClickListener(this);

        if(args != null){
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) recycler.getLayoutParams();
            if(walletList.size() > 4){
                lp.height = SmartUtil.dp2px(50 * 4);
            } else {
                lp.height = SmartUtil.dp2px(50 * walletList.size());
            }
            recycler.setLayoutParams(lp);

            selectAddress = args.getString(SELECT_ADDRESS,"");
            SelectWalletAdapter adapter = new SelectWalletAdapter(R.layout.item_list_select_wallet,walletList,selectAddress,getActivity(),this);
            recycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
            recycler.setAdapter(adapter);
            adapter.setOnItemClickListener(this);
        }
        Dialog dialog = new Dialog(mContext,R.style.bottom_nopadding_dialog_style);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
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
    private void setWalletList(List<Wallet_Main> walletMains){
        this.walletList = walletMains;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.close_btn) {
            dismiss();
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Wallet_Main walletMain = walletList.get(position);
        if(clickListener != null){
            if(!walletMain.getAddress().equals(selectAddress)) {
                SelectWalletAdapter selectWalletAdapter = (SelectWalletAdapter)adapter;
                selectWalletAdapter.setCurrAddress(walletMain.getAddress());
                selectWalletAdapter.notifyDataSetChanged();
                clickListener.select(walletMain);
            }
            dismiss();
        }
    }

    @Override
    public void copy(String address) {
        copyStrings(address);
        ToastUtil.showCenterToast(getContext().getResources().getString(R.string.copy_success));
    }

    public static class Builder{
        private String selectAddress;
        private List<Wallet_Main> walletList;
        private ClickListener clickListener;

        public Builder setSelectAddress(String selectAddress){
            this.selectAddress = selectAddress;
            return this;
        }
        public Builder setWalletList(List<Wallet_Main> walletList){
            this.walletList = walletList;
            return this;
        }

        public Builder setClickListener(ClickListener clickListener){
            this.clickListener = clickListener;
            return this;
        }
        public SelectWalletDialog build(){
            return SelectWalletDialog.getInstance(selectAddress,walletList,clickListener);
        }
    }

    /**
     * 暴露接口实现回调
     */
    public interface ClickListener{
        /**
         * 选中的钱包
         * @param walletMain
         */
        void select(Wallet_Main walletMain);
    }
}
