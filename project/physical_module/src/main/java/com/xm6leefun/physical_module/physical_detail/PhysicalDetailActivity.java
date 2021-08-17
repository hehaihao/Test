package com.xm6leefun.physical_module.physical_detail;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.refresh_view.anim.ScaleInRightAnimator;
import com.xm6leefun.common.refresh_view.itemdecora.SpacesItemDecoration;
import com.xm6leefun.common.refresh_view.layoutmanager.WrapContentLinearLayoutManager;

import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.common.utils.status_bar.StatusBarUtil;
import com.xm6leefun.common.wallet.KeysInfo;
import com.xm6leefun.common.widget.banner.NetworkImageHolderView;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.export_module.bean.SelectAddressBean;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.physical_module.R;
import com.xm6leefun.physical_module.R2;
import com.xm6leefun.physical_module.certificate.CertificateActivity;
import com.xm6leefun.physical_module.photo_viewer.PhotosActivity;
import com.xm6leefun.physical_module.physical_detail.adapter.PhysicalDescAdapter;
import com.xm6leefun.physical_module.physical_detail.adapter.PhysicalTranAdapter;
import com.xm6leefun.physical_module.physical_detail.bean.PhysicalDetailBean;
import com.xm6leefun.physical_module.physical_detail.dialog.PhysicalTranDialog;
import com.xm6leefun.physical_module.physical_detail.mvp.PhysicalDetailContract;
import com.xm6leefun.physical_module.physical_detail.mvp.PhysicalDetailPresenter;
import com.xm6leefun.physical_module.physical_detail.mvp.PhysicalTranListBean;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/4/7 11:38
 */
@Route(path = ModuleRouterTable.PHYSICAL_DETAIL_PAGE)
public class PhysicalDetailActivity extends BaseToolbarPresenterActivity<PhysicalDetailPresenter> implements PhysicalDetailContract.IView, OnRefreshListener {
    @BindView(R2.id.emptyView)
    LinearLayout emptyView;
    @BindView(R2.id.ultra_pull)
    SmartRefreshLayout ultra_pull;
    @BindView(R2.id.img_banner)
    ConvenientBanner<String> img_banner;
    @BindView(R2.id.ownner_address_tv)
    TextView ownnerAddressTv;
    @BindView(R2.id.contract_address_tv)
    TextView contractAddressTv;
    @BindView(R2.id.token_id_tv)
    TextView tokenIdTv;
    @BindView(R2.id.physical_desc_layout)
    RelativeLayout physical_desc_layout;
    @BindView(R2.id.physical_desc_recycler)
    RecyclerView physical_desc_recycler;
    @BindView(R2.id.physical_intro_tv)
    TextView physical_intro_tv;
    @BindView(R2.id.trans_layout)
    LinearLayout trans_layout;
    @BindView(R2.id.trans_recycler)
    RecyclerView transRecycler;

    private PhysicalTranAdapter adapter;
    private PhysicalDescAdapter physicalDescAdapter;

    private long mGodId;

    private boolean mIsHide;

    @Override
    protected PhysicalDetailPresenter createPresenter() {
        return new PhysicalDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_physical_detail_layout;
    }

    @Override
    protected void initView() {

        ultra_pull.setRefreshHeader(new ClassicsHeader(this));
        ultra_pull.setOnRefreshListener(this);
        physical_desc_recycler.setLayoutManager(new WrapContentLinearLayoutManager(this));
        transRecycler.setLayoutManager(new WrapContentLinearLayoutManager(this));
        transRecycler.addItemDecoration(new SpacesItemDecoration(8));
    }

    @Override
    protected void initData() {
        Bundle args = getIntent().getExtras();
        if(args != null){
            mGodId = args.getLong(ModuleServiceUtils.DATA);
            mIsHide = args.getBoolean(ModuleServiceUtils.IS_HIDE);
        }

        topBarTvRight.setVisibility(mIsHide?View.GONE: View.VISIBLE);
        topBarTvRight.setText(R.string.physical_assets_tran);

        presenter.getDetail(mGodId);
    }

    /**
     * 查看鉴定证书
     */
    private void authenticationCertificate(){
        Bundle args = new Bundle();
        args.putString(CertificateActivity.PDF_URL,nftInfoBean.getNft_certificate());
        ActivityUtil.startActivity(CertificateActivity.class,false,args);
    }

    @Override
    public void getListSuccess(List<PhysicalDetailBean.TransferBean> listBeans) {
        if(listBeans != null && listBeans.size() > 0){
            trans_layout.setVisibility(View.VISIBLE);
            if(adapter == null){
                adapter = new PhysicalTranAdapter(this,R.layout.item_physical_tran_layout,listBeans);
                transRecycler.setAdapter(adapter);
            }else {
                adapter.setNewData(listBeans);
            }
        }else{
            trans_layout.setVisibility(View.GONE);
        }
        finishLoad();
    }

    private PhysicalDetailBean.NftInfoBean nftInfoBean;
    @Override
    public void getDetailSuccess(PhysicalDetailBean.NftInfoBean nftInfoBean) {
        if(nftInfoBean == null) return;
        emptyView.setVisibility(View.GONE);
        ultra_pull.setVisibility(View.VISIBLE);
        this.nftInfoBean = nftInfoBean;
        topBarTvTitle.setText(nftInfoBean.getNft_name());
        initBanner(nftInfoBean.getNft_img());
        ownnerAddressTv.setText(nftInfoBean.getOwner_address());
        contractAddressTv.setText(nftInfoBean.getContract_address());
        tokenIdTv.setText(mGodId+"");
        physical_intro_tv.setText(nftInfoBean.getNft_introduce());
        List<String> descList = nftInfoBean.getNft_details();
        if(descList != null && descList.size() > 0) {
            physical_desc_layout.setVisibility(View.VISIBLE);
            if (physicalDescAdapter == null) {
                physicalDescAdapter = new PhysicalDescAdapter(this, R.layout.item_physical_desc_layout, descList);
                physical_desc_recycler.setAdapter(physicalDescAdapter);
            } else {
                physicalDescAdapter.setNewData(descList);
            }
        }else{
            physical_desc_layout.setVisibility(View.GONE);
        }
        finishLoad();
    }

    @Override
    public void getDetailFail(String msg) {
        emptyView.setVisibility(View.VISIBLE);
        ultra_pull.setVisibility(View.GONE);
    }

    /**
     * 处理banner数据
     * @param nft_img
     */
    private void initBanner(List<String> nft_img) {
        if(nft_img != null && nft_img.size() > 0){
            img_banner.setVisibility(View.VISIBLE);
            img_banner.setPages((CBViewHolderCreator<NetworkImageHolderView>) () -> new NetworkImageHolderView (), nft_img);
            img_banner.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Bundle args = new Bundle();
                    args.putStringArrayList(PhotosActivity.DATA, (ArrayList<String>) nft_img);
                    args.putInt(PhotosActivity.POSITION, position);
                    ActivityUtil.startActivity(PhotosActivity.class,false,args);
                }
            });
//        设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//            img_banner.setPageIndicator(new int[]{R.mipmap.point_unfocused, R.mipmap.point_focused});
            img_banner.setCanLoop(nft_img.size() > 1);
            img_banner.setScrollDuration(1000);//滚动速度
            img_banner.startTurning(4000);//间隔
        }else{
            img_banner.setVisibility(View.GONE);
        }
    }

    /**
     * 校验密码结果
     * @param isPass
     * @param toAddress
     * @param psw
     * @param keysInfo
     */
    @Override
    public void checkPswResult(boolean isPass,PhysicalDetailBean.NftInfoBean nftInfoBean,String toAddress, String psw, KeysInfo keysInfo) {
        if(selectCallbackListener != null){
            selectCallbackListener.checkPswResult(isPass);
        }
        if(isPass){//验证通过
            dismissInputDialog();
            //资产转移
            presenter.physicalTran(keysInfo.getPrivateKey(),nftInfoBean,toAddress);
        }else{
            ToastUtil.showCenterToast(getString(R.string.physical_tran_psw_err));
        }
    }

    /**
     * 取消弹窗
     */
    private void dismissInputDialog() {
        if(physicalTranDialog != null && physicalTranDialog.isAdded())
            physicalTranDialog.dismiss();
    }

    @Override
    public void onLoadFail(String msg) {
        finishLoad();
        ToastUtil.showCenterToast(msg);
    }

    private void finishLoad(){
        ultra_pull.finishRefresh();
        ultra_pull.finishLoadMore();
    }

    /**
     * 资产转移提交成功
     */
    @Override
    public void physicalTranSuccess() {
        ToastUtil.showCenterToast("转移提交成功");
    }

    @OnClick(R2.id.base_topBar_tv_right)
    public void onViewClicked() {
        if(nftInfoBean != null) showTranDialog();
    }

    private PhysicalTranDialog.SelectCallbackListener selectCallbackListener;

    private PhysicalTranDialog physicalTranDialog;

    private void showTranDialog() {
        if(physicalTranDialog == null){
            physicalTranDialog = new PhysicalTranDialog.Builder()
                    .setOwnnerAddress(nftInfoBean.getOwner_address())
                    .setClickListener(new PhysicalTranDialog.ClickListener() {
                        @Override
                        public void onSure(String toAddress, String pswStr,PhysicalTranDialog.SelectCallbackListener selectCallbackListener) {
                            PhysicalDetailActivity.this.selectCallbackListener = selectCallbackListener;
                            presenter.checkPsw(nftInfoBean,toAddress,pswStr,getString(R.string.physical_tran_psw_err));
                        }
                        @Override
                        public void selectAddress() {
                            ARouter.getInstance()
                                    .build(ModuleRouterTable.ADDRESS_LIST_PAGE)
                                    .withBoolean(ModuleServiceUtils.IS_SELECT_ADDRESS,true)
                                    .navigation(PhysicalDetailActivity.this,ModuleServiceUtils.REQ_SELECT_ADDRESS);
                        }
                    })
                    .build();
        }
        if(!physicalTranDialog.isAdded()) physicalTranDialog.show(mFragmentManager,PhysicalTranDialog.TAG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ModuleServiceUtils.REQ_SELECT_ADDRESS://处理选择地址结果
                if(resultCode == ModuleServiceUtils.RESULT_CODE_SELECT_ADDRESS){
                    if(data != null){
                        SelectAddressBean addressBean = data.getParcelableExtra(ModuleServiceUtils.INTENT_EXTRA_KEY_SELECT_ADDRESS);
                        String selectAddress = addressBean.getAddress();
                        setCallBack(selectCallbackListener,selectAddress);
                    }
                }
                break;
        }
    }

    private void setCallBack(PhysicalTranDialog.SelectCallbackListener selectCallbackListener,String address){
        if(selectCallbackListener != null){
            selectCallbackListener.selectResult(address);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        presenter.getDetail(mGodId);
    }
}
