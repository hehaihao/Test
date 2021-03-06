package com.xm6leefun.wallet_module.home_wallet;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xm6leefun.common.app.fragment.BasePresenterFragment;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.refresh_view.layoutmanager.SnapLayoutManager;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.utils.DoubleClickUtils;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.common.utils.status_bar.StatusBarUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.bean.HomeDataBusBean;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.export_module.busbean.RefreshWalletDataBusBean;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.R2;
import com.xm6leefun.wallet_module.backup_tips.BackupTipsActivity;
import com.xm6leefun.wallet_module.backup_tips.mvp.CusCreateWalletBean;
import com.xm6leefun.common.wallet.KeysInfo;
import com.xm6leefun.wallet_module.home_wallet.adapter.HomeTopListAdapter;
import com.xm6leefun.wallet_module.home_wallet.dialog.SelectPhysicalBottomSheetFragment;
import com.xm6leefun.wallet_module.home_wallet.dialog.SwitchWalletBottomSheetFragment;
import com.xm6leefun.wallet_module.home_wallet.listener.NftContentListener;
import com.xm6leefun.wallet_module.home_wallet.mvp.HomeWalletContract;
import com.xm6leefun.wallet_module.home_wallet.mvp.HomeWalletPresenter;
import com.xm6leefun.wallet_module.home_wallet.mvp.PhysicalListBean;
import com.xm6leefun.wallet_module.points_property.add_property.bean.RefreshPropertyBunBean;
import com.xm6leefun.wallet_module.wallet_setting.dialog.InputDialog;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @Description:??????
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:47
 */
@Route(path = ModuleRouterTable.HOME_WALLET_FRAGMENT)
public class HomeWalletFragment extends BasePresenterFragment<HomeWalletPresenter> implements HomeWalletContract.IView, OnRefreshListener,
        HomeTopListAdapter.ClickListener, BaseQuickAdapter.OnItemClickListener, NftContentListener {
    @BindView(R2.id.root_view)
    RelativeLayout root_view;
    @BindView(R2.id.wallet_title_tv)
    TextView wallet_title_tv;
    @BindView(R2.id.top_rec)
    RecyclerView top_rec;
    @BindView(R2.id.wallet_iv)
    ImageView wallet_iv;
    @BindView(R2.id.scan_iv)
    ImageView scan_iv;
    @BindView(R2.id.ultra_pull)
    SmartRefreshLayout ultra_pull;
    @BindView(R2.id.backup_layout)
    RelativeLayout backup_layout;


    public static HomeWalletFragment getInstance(){
        HomeWalletFragment fragment = new HomeWalletFragment();
        return fragment;
    }
    @Override
    protected HomeWalletPresenter createPresenter() {
        return new HomeWalletPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_wallet_layout;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setLightModeFull(getActivity());//??????????????????????????????
        root_view.setPadding(0,getStatusBarHeight(),0,0);
        EventBus.getDefault().register(this);
        wallet_iv.setOnClickListener(this);
        scan_iv.setOnClickListener(this);
        backup_layout.setOnClickListener(this);
        ultra_pull.setRefreshHeader(new ClassicsHeader(getContext()));
        ultra_pull.setOnRefreshListener(this);
        setupTopRec(top_rec);
        homePointsFragment = HomePointsFragment.getInstance();
        homePhysicalFragment = HomePhysicalFragment.getInstance();
        switchFragment(mFragment, homePointsFragment, "fragment1");

    }
    private HomePointsFragment homePointsFragment;
    private HomePhysicalFragment homePhysicalFragment;

    /**
     * ????????????????????????
     * @param top_rec
     */
    private void setupTopRec(RecyclerView top_rec) {
        SnapLayoutManager myLayoutManager = new SnapLayoutManager(getActivity(), OrientationHelper.HORIZONTAL,false);
        myLayoutManager.setOnViewPagerListener((position, isBottom) -> {
            //??????
            if(position == 0){
                switchFragment(mFragment, homePointsFragment, HomePointsFragment.TAG);
                wallet_title_tv.setText(R.string.home_wallet_points_title);
            } else {
                switchFragment(mFragment, homePhysicalFragment, HomePhysicalFragment.TAG);
                wallet_title_tv.setText(R.string.home_wallet_physical_title);
            }
        });
        top_rec.setLayoutManager(myLayoutManager);
        top_rec.setHasFixedSize(true);
        top_rec.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.wallet_iv){//????????????
            ModuleServiceUtils.navigateWalletManagePage();
        }else if(id == R.id.scan_iv){//?????????
            checkPermissionRequest(getActivity(), Manifest.permission.CAMERA, isAllow -> {
                if (isAllow)
                    ARouter.getInstance()
                            .build(ModuleRouterTable.SCAN_PAGE)
                            .withBoolean(ModuleServiceUtils.FROM_INDEX,true)
                            .navigation(getActivity(),ModuleServiceUtils.REQ_QR_CODE);
                else ToastUtil.showCenterToast("????????????????????????");
            });
        }else if(id == R.id.backup_layout){//???????????????
            if (DoubleClickUtils.isNoDoubleClick()) {
                inputDialog = getBuilder().setDialogType(InputDialog.Wallet_words_backup)
                        .build();
                inputDialog.show(mChildFragmentManager,InputDialog.TAG);
            }
        }
    }
    private InputDialog.CheckPswListener checkPswListener;
    private InputDialog inputDialog;
    private InputDialog.Builder builder;
    private InputDialog.Builder getBuilder() {
        if(builder == null) builder = new InputDialog.Builder();
        builder.setClickListener((inputTye, content, listener) -> {
            switch (inputTye){
                case InputDialog.Wallet_words_backup:
                    this.checkPswListener = listener;
                    presenter.checkPsw(mWalletMain.getAddress(), content, getString(R.string.password_wrong));
                break;
            }
        });
        return builder;
    }

    /**
     * ????????????
     */
    private void dismissInputDialog() {
        if(inputDialog != null && inputDialog.isAdded())
            inputDialog.dismiss();
    }

    private Wallet_Main mWalletMain;

    /**
     * ??????????????????ft?????????nft??????
     * @param homeDataResultBean
     */
    @Override
    public void getHomeDataSuccess(HomeDataResultBean homeDataResultBean,String requestAddress) {
        ultra_pull.finishRefresh();
        if(requestAddress.equals(mWalletMain.getAddress())){//?????????????????????????????????????????????????????????
            if(homePhysicalFragment != null) homePhysicalFragment.setNft(homeDataResultBean.getNft(),homeDataResultBean.getNftNums(),this);
        }
    }

    @Override
    public void getHomeDataFail(String msg,String requestAddress) {
        ultra_pull.finishRefresh();
        if(requestAddress.equals(mWalletMain.getAddress())) {//?????????????????????????????????????????????????????????
            if (homePhysicalFragment != null) homePhysicalFragment.setNft(null,null,this);
        }
    }

    /**
     * ???????????????????????????????????????????????????
     * @param ftBeans
     * @param requestAddress
     */
    @Override
    public void getDbFtListSuccess(List<HomeDataResultBean.FtBean> ftBeans, String requestAddress) {
        if(requestAddress.equals(mWalletMain.getAddress())){//?????????????????????????????????????????????????????????
            if(homePointsFragment != null) homePointsFragment.setFt(ftBeans);
        }
    }

    @Override
    public void getDbFtListFail(String msg, String requestAddress) {
        if(requestAddress.equals(mWalletMain.getAddress())) {//?????????????????????????????????????????????????????????
            if (homePointsFragment != null) homePointsFragment.setFt(null);
        }
    }

    @Override
    public void checkPswResult(boolean isPass, String psw, KeysInfo keysInfo) {
        if(checkPswListener != null)checkPswListener.checkPswResult(isPass);
        if(isPass){//????????????
            dismissInputDialog();
            dealResult(psw,keysInfo);
        }else{
            ToastUtil.showCenterToast(getString(R.string.import_by_keystore_psw_error));
        }
    }

    /**
     * ?????????????????????????????????
     * @param keysInfo
     */
    private void dealResult(String psw,KeysInfo keysInfo) {
        Bundle bundle = new Bundle();
        CusCreateWalletBean cusCreateWallet = new CusCreateWalletBean();
        cusCreateWallet.setKeysInfo(keysInfo);
        cusCreateWallet.setPwd(psw);
        cusCreateWallet.setAddress(mWalletMain.getAddress());
        cusCreateWallet.setWhere(2);  // 0 ?????????1 ?????????2 ??????
        cusCreateWallet.setType(0);//?????????
        bundle.putParcelable(BackupTipsActivity.CUS_DATA, cusCreateWallet);
        ActivityUtil.startActivity(BackupTipsActivity.class, false, bundle);
    }

    private HomeTopListAdapter homeTopListAdapter;
    private List<Wallet_Main> wallet_mains = new ArrayList<>();
    @Override
    public void getCurrWalletSuccess(Wallet_Main walletMain) {
        this.mWalletMain = walletMain;
        wallet_mains.clear();
        wallet_mains.add(walletMain);
        wallet_mains.add(walletMain);
        //????????????????????????
        if(homeTopListAdapter == null){
            homeTopListAdapter = new HomeTopListAdapter(getContext(),R.layout.item_home_top_layout,wallet_mains,this);
            homeTopListAdapter.setOnItemClickListener(this);
            top_rec.setAdapter(homeTopListAdapter);
        }else{
            homeTopListAdapter.setNewData(wallet_mains);
        }
        //????????????????????????
        if(homePointsFragment != null) homePointsFragment.setWalletMainData(walletMain);
        if(homePhysicalFragment != null) homePhysicalFragment.setWalletMainData(walletMain);


        backup_layout.setVisibility((walletMain.getIsBackup() == 0 && !StrUtils.isEmpty(walletMain.getWordsJson())) ? View.VISIBLE : View.GONE);
    }

    private SwitchWalletBottomSheetFragment.Builder walletBuilder;

    /**
     * ???????????????????????????????????????????????????
     * @param idWalletList
     * @param normalWalletList
     */
    @Override
    public void getWalletListSuccess(List<Wallet_Main> idWalletList,List<Wallet_Main> normalWalletList) {
        if(mWalletMain != null) {
            if(walletBuilder == null) {
                walletBuilder = new SwitchWalletBottomSheetFragment.Builder()
                        .setIdWalletList(idWalletList)
                        .setNormalWalletList(normalWalletList)
                        .setSelectAddress(mWalletMain.getAddress())
                        .setClickListener(new SwitchWalletBottomSheetFragment.ClickListener() {
                            @Override
                            public void switchWallet(Wallet_Main walletMain) {
                                presenter.setAddress(walletMain.getAddress(), mWalletMain.getAddress());
                            }
                            @Override
                            public void toManager() {
                                ModuleServiceUtils.navigateWalletManagePage();
                            }
                        });
            }else{
                walletBuilder.setIdWalletList(idWalletList).setNormalWalletList(normalWalletList).setSelectAddress(mWalletMain.getAddress());
            }
            SwitchWalletBottomSheetFragment switchWalletBottomSheetFragment = walletBuilder.build();
            switchWalletBottomSheetFragment.show(mChildFragmentManager,SwitchWalletBottomSheetFragment.TAG);
        }
    }
    /**
     * ??????????????????
     * @param walletMain
     */
    @Override
    public void setAddressSuccess(Wallet_Main walletMain) {
        EventBus.getDefault().post(new RefreshWalletDataBusBean());
    }

    /**
     * ????????????????????????
     * @param address
     */
    @Override
    public void getNftList(String address) {
        presenter.getNftList(address);
    }
    /**
     * ?????????????????????????????????????????????????????????????????????????????????
     * @param physicalListBeans
     */
    @Override
    public void getNftListSuccess(List<PhysicalListBean> physicalListBeans) {
        showSelectDialog(physicalListBeans);
    }

    @Override
    public void getNftListFail(String msg) {
        ToastUtil.showCenterToast(msg);
    }

    private SelectPhysicalBottomSheetFragment.Builder selectBuider;
    /**
     * ????????????
     * @param physicalListBeans
     */
    private void showSelectDialog(List<PhysicalListBean> physicalListBeans) {
        if(selectBuider == null) {
            selectBuider = new SelectPhysicalBottomSheetFragment.Builder()
                    .setNftBeans(physicalListBeans)
                    .setClickListener(nftBean -> ModuleServiceUtils.navigateSendPropertyPage(mWalletMain.getAddress(), nftBean.getNft_txid(),nftBean.getContract_address(),nftBean.getNft_img()));
        }else{
            selectBuider.setNftBeans(physicalListBeans);
        }
        SelectPhysicalBottomSheetFragment selectPhysicalBottomSheetFragment = selectBuider.build();
        selectPhysicalBottomSheetFragment.show(mChildFragmentManager,SelectPhysicalBottomSheetFragment.TAG);
    }

    @Override
    public void onLoadFail(String msg) {
        ultra_pull.finishRefresh();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        presenter.getCurrWallet();
        //??????????????????
        presenter.getHomeData(mWalletMain);
    }

    /**
     * ??????????????????????????????????????????
     * @param walletMain
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void refreshWalletDataSticky(Wallet_Main walletMain){
        getCurrWalletSuccess(walletMain);
    }

    /**
     * ??????????????????????????????????????????
     * @param homeDataBusBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void refreshWalletDataSticky(HomeDataBusBean homeDataBusBean){
        String requestAddress = homeDataBusBean.getRequestAddress();
        getHomeDataSuccess(homeDataBusBean.getHomeDataResultBean(),requestAddress);
        HomeDataResultBean homeDataResultBean = homeDataBusBean.getHomeDataResultBean();
        if(homeDataResultBean.getFt() != null && homeDataResultBean.getFt().size() > 0){
            presenter.savePointsAssets(requestAddress,homeDataResultBean.getFt());
        }else{
            presenter.getDbPointsAssets(requestAddress);//?????????????????????????????????
        }
    }

    /**
     * ????????????????????????
     * @param refreshPropertyBunBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshDatas(RefreshPropertyBunBean refreshPropertyBunBean) {
        //??????????????????
        presenter.getHomeData(mWalletMain);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    /**
     * ????????????
     */
    @Override
    public void switchWallet() {
        presenter.getWalletList();
    }

    /**
     * ????????????
     * @param address
     */
    @Override
    public void copy(String address) {
        copyStrings(address);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            StatusBarUtil.setLightModeFull(getActivity());//??????????????????????????????
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Wallet_Main wallet_main = (Wallet_Main) adapter.getData().get(position);
        if(wallet_main == null) return;
        ModuleServiceUtils.navigateWalletSettingPage(wallet_main.getAddress());
    }
}
