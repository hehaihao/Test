package com.xm6leefun.points_module.transaction_detail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.points_module.R;
import com.xm6leefun.points_module.R2;
import com.xm6leefun.points_module.transaction.mvp.TransactionListBean;
import com.xm6leefun.points_module.transaction_detail.mvp.TransactionDetailPresenter;
import com.xm6leefun.points_module.transaction_detail.mvp.TransitionDetailContract;

import butterknife.BindView;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/4/6 16:58
 */
@Route(path = ModuleRouterTable.TRANSACTION_DETAIL_PAGE)
public class TransactionDetailActivity extends BaseToolbarPresenterActivity<TransactionDetailPresenter> implements TransitionDetailContract.IView {
    @BindView(R2.id.icon)
    ImageView icon;
    @BindView(R2.id.status_tv)
    TextView status_tv;
    @BindView(R2.id.time_tv)
    TextView time_tv;
    @BindView(R2.id.amount_tv)
    TextView amount_tv;
    @BindView(R2.id.receive_address_tv)
    TextView receive_address_tv;
    @BindView(R2.id.pay_address_tv)
    TextView pay_address_tv;
    @BindView(R2.id.trans_code_tv)
    TextView trans_code_tv;
    @BindView(R2.id.recei_address_layout)
    RelativeLayout recei_address_layout;
    @BindView(R2.id.pay_address_layout)
    RelativeLayout pay_address_layout;
    @BindView(R2.id.trans_code_layout)
    RelativeLayout trans_code_layout;



    @Override
    protected TransactionDetailPresenter createPresenter() {
        return new TransactionDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transaction_detail_layout;
    }

    @Override
    protected void initView() {
        addOnClickListeners(
                R.id.query_detail_layout,
                R.id.recei_address_layout,
                R.id.pay_address_layout,
                R.id.trans_code_layout);
        topBarTvTitle.setText(R.string.transaction_detail_title);
    }

    private TransactionListBean mTransactionListBean;
    @Override
    protected void initData() {
        Bundle args = getIntent().getExtras();
        if(args != null){
            mTransactionListBean = args.getParcelable(ModuleServiceUtils.DATA);
            getDetailSuccess(mTransactionListBean);
        }
    }

    @Override
    public void getDetailSuccess(TransactionListBean transactionListBean) {
        if(transactionListBean != null){
            status_tv.setText(transactionListBean.getTypeName());
            time_tv.setText(transactionListBean.getCreate_time());
            amount_tv.setText(transactionListBean.getTransfer_num()+"");
            receive_address_tv.setText(transactionListBean.getRe_address());
            pay_address_tv.setText(transactionListBean.getOriginator_address());
            trans_code_tv.setText(transactionListBean.getHash());
            switch (transactionListBean.getType()){//1普通转出 2普通转入5NFT转出 6NFT转入 7nft创建(2和6是加，其它是减)
                case 7://创建
                    icon.setImageResource(R.mipmap.transfer_wait_icon);
                    break;
                case 1://普通转出
                case 5://nft转出
                    icon.setImageResource(R.mipmap.transfer_send_success_icon);
                    break;
                case 2://普通转入
                case 6://nft转入
                    icon.setImageResource(R.mipmap.transfer_receive_success_icon);
                    break;
                case 3://失败
                    icon.setImageResource(R.mipmap.transfer_ing_icon);
                    break;
            }
        }
    }

    @Override
    public void onLoadFail(String msg) {
        ToastUtil.showCenterToast(msg);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.query_detail_layout) {
            ModuleServiceUtils.navigateTradeDetailPage(trans_code_tv.getText().toString());
        }else if(id == R.id.recei_address_layout){
            copyStrings(receive_address_tv.getText().toString());
        }else if(id == R.id.pay_address_layout){
            copyStrings(pay_address_tv.getText().toString());
        }else if(id ==R.id.trans_code_layout){
            copyStrings(trans_code_tv.getText().toString());
        }
    }
}
