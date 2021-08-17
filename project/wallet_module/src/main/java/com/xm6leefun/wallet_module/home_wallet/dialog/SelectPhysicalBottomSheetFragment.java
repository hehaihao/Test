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
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.home_wallet.adapter.SelectPhysicalListAdapter;
import com.xm6leefun.wallet_module.home_wallet.mvp.PhysicalListBean;
import com.xm6leefun.wallet_module.wallet_manage.adapter.WalletListAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Description:选择实物资产
 * @Author: hhh
 * @CreateDate: 2021/4/16 9:52
 */
public class SelectPhysicalBottomSheetFragment extends BottomSheetDialogFragment implements BaseQuickAdapter.OnItemClickListener, View.OnClickListener {
    public static final String TAG = SelectPhysicalBottomSheetFragment.class.getSimpleName();
    private Context mContext;
    private View view;
    private List<PhysicalListBean> nftBeans;

    public static SelectPhysicalBottomSheetFragment getInstance(List<PhysicalListBean> nftBeans, ClickListener clickListener) {
        SelectPhysicalBottomSheetFragment fragment = new SelectPhysicalBottomSheetFragment();
        fragment.setClickListener(clickListener);
        fragment.setNftBeans(nftBeans);
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
        return peekHeight - peekHeight / 3;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        view = inflater.inflate(R.layout.select_physical_dialog_layout, container, false);
        RecyclerView physical_rec = view.findViewById(R.id.physical_rec);
        physical_rec.setLayoutManager(new WrapContentLinearLayoutManager(mContext));
        SelectPhysicalListAdapter selectPhysicalListAdapter = new SelectPhysicalListAdapter(getContext(),R.layout.item_home_select_list_physical, nftBeans);
        selectPhysicalListAdapter.setOnItemClickListener(this);
        physical_rec.setAdapter(selectPhysicalListAdapter);
        return view;
    }

    private ClickListener clickListener;
    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    public void setNftBeans(List<PhysicalListBean> nftBeans) {
        this.nftBeans = nftBeans;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        PhysicalListBean nftBean = (PhysicalListBean) adapter.getData().get(position);
        if(clickListener != null){
            clickListener.select(nftBean);
            dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
    }

    public interface ClickListener{
        void select(PhysicalListBean nftBean);
    }

    public static class Builder{
        private List<PhysicalListBean> nftBeans;
        private ClickListener clickListener;

        public SelectPhysicalBottomSheetFragment.Builder setNftBeans(List<PhysicalListBean> nftBeans) {
            this.nftBeans = nftBeans;
            return this;
        }

        public SelectPhysicalBottomSheetFragment.Builder setClickListener(ClickListener clickListener){
            this.clickListener = clickListener;
            return this;
        }
        public SelectPhysicalBottomSheetFragment build(){
            return SelectPhysicalBottomSheetFragment.getInstance(nftBeans,clickListener);
        }
    }
}
