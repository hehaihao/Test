package com.xm6leefun.wallet_module.home_wallet.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.refresh_view.itemdecora.SpacesItemDecoration;
import com.xm6leefun.common.refresh_view.layoutmanager.WrapContentLinearLayoutManager;
import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.wallet_manage.adapter.WalletListAdapter;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Description:切换钱包弹窗
 * @Author: hhh
 * @CreateDate: 2021/4/16 9:52
 */
public class SwitchWalletBottomSheetFragment extends BottomSheetDialogFragment implements BaseQuickAdapter.OnItemClickListener, View.OnClickListener {
    public static final String TAG = SwitchWalletBottomSheetFragment.class.getSimpleName();
    private static final String SELECT_ADDRESS = "select_address";
    private Context mContext;
    private View view;
    private List<Wallet_Main> idWalletMains,normalWalletMains;
    private String selectAddress;

    public static SwitchWalletBottomSheetFragment getInstance(String selectAddress, List<Wallet_Main> idWalletList, List<Wallet_Main> normalWalletList, ClickListener clickListener) {
        SwitchWalletBottomSheetFragment fragment = new SwitchWalletBottomSheetFragment();
        fragment.setClickListener(clickListener);
        fragment.setIdWalletList(idWalletList);
        fragment.setNormalWalletList(normalWalletList);
        Bundle args = new Bundle();
        args.putString(SELECT_ADDRESS,selectAddress);
        fragment.setArguments(args);
        return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //返回BottomSheetDialog的实例
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        //获取dialog对象
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        //把windowsd的默认背景颜色去掉，不然圆角显示不见
        dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //获取diglog的根部局
        FrameLayout bottomSheet = dialog.getDelegate().findViewById(R.id.design_bottom_sheet);
        if (bottomSheet != null) {
            //获取根部局的LayoutParams对象
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
            layoutParams.height = getPeekHeight();
            //修改弹窗的最大高度，不允许上滑（默认可以上滑）
            bottomSheet.setLayoutParams(layoutParams);

            final BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);
            //peekHeight即弹窗的最大高度
            behavior.setPeekHeight(getPeekHeight());
            // 初始为展开状态
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            ImageView close_btn = view.findViewById(R.id.close_btn);
            //设置监听
            close_btn.setOnClickListener(v -> {
                //关闭弹窗
                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            });
        }

    }

    /**
     * 弹窗高度，默认为屏幕高度的9/10
     * 子类可重写该方法返回peekHeight
     * @return height
     */
    protected int getPeekHeight() {
        int peekHeight = getResources().getDisplayMetrics().heightPixels;
        return peekHeight - peekHeight / 10;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        Bundle args = getArguments();
        if(args != null)
            selectAddress = args.getString(SELECT_ADDRESS,"");
        view = inflater.inflate(R.layout.switch_wallet_dialog_layout, container, false);
        TextView manager_tv = view.findViewById(R.id.manager_tv);
        manager_tv.setOnClickListener(this);
        RecyclerView id_wallet_rec = view.findViewById(R.id.id_wallet_rec);
        LinearLayout import_layout = view.findViewById(R.id.import_layout);
        RecyclerView wallet_rec = view.findViewById(R.id.wallet_rec);
        id_wallet_rec.setLayoutManager(new WrapContentLinearLayoutManager(mContext));
        id_wallet_rec.addItemDecoration(new SpacesItemDecoration(40));
        if(normalWalletMains.size() > 0){
            import_layout.setVisibility(View.VISIBLE);
            wallet_rec.setLayoutManager(new WrapContentLinearLayoutManager(mContext));
            wallet_rec.addItemDecoration(new SpacesItemDecoration(40));
            WalletListAdapter normalWalletAdapter = new WalletListAdapter(R.layout.item_list_wallet, normalWalletMains);
            normalWalletAdapter.setOnItemClickListener(this);
            wallet_rec.setAdapter(normalWalletAdapter);
        }else{
            import_layout.setVisibility(View.GONE);
        }
        WalletListAdapter idWalletAdapter = new WalletListAdapter(R.layout.item_list_wallet, idWalletMains);
        idWalletAdapter.setOnItemClickListener(this);
        id_wallet_rec.setAdapter(idWalletAdapter);
        return view;
    }
    private void setIdWalletList(List<Wallet_Main> idWalletMains){
        this.idWalletMains = idWalletMains;
    }
    private void setNormalWalletList(List<Wallet_Main> normalWalletMains){
        this.normalWalletMains = normalWalletMains;
    }
    private ClickListener clickListener;
    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<Wallet_Main> walletList = adapter.getData();
        Wallet_Main walletMain = walletList.get(position);
        if(clickListener != null){
            if(!walletMain.getAddress().equals(selectAddress)) {
                clickListener.switchWallet(walletMain);
            }
            dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.manager_tv){
            if(clickListener != null)clickListener.toManager();
            dismiss();
        }
    }

    public interface ClickListener{
        void switchWallet(Wallet_Main walletMain);
        void toManager();
    }

    public static class Builder{
        private String selectAddress;
        private List<Wallet_Main> idWalletList, normalWalletList;
        private ClickListener clickListener;

        public SwitchWalletBottomSheetFragment.Builder setSelectAddress(String selectAddress){
            this.selectAddress = selectAddress;
            return this;
        }

        public SwitchWalletBottomSheetFragment.Builder setIdWalletList(List<Wallet_Main> idWalletList) {
            this.idWalletList = idWalletList;
            return this;
        }

        public SwitchWalletBottomSheetFragment.Builder setNormalWalletList(List<Wallet_Main> normalWalletList) {
            this.normalWalletList = normalWalletList;
            return this;
        }

        public SwitchWalletBottomSheetFragment.Builder setClickListener(ClickListener clickListener){
            this.clickListener = clickListener;
            return this;
        }
        public SwitchWalletBottomSheetFragment build(){
            return SwitchWalletBottomSheetFragment.getInstance(selectAddress,idWalletList,normalWalletList,clickListener);
        }
    }
}
