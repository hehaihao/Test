package com.xm6leefun.wallet_module.wallet_manage;

import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.refresh_view.itemdecora.SpacesItemDecoration;
import com.xm6leefun.common.refresh_view.layoutmanager.WrapContentLinearLayoutManager;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.R2;
import com.xm6leefun.wallet_module.first.dialog.ImportWayDialog;
import com.xm6leefun.wallet_module.importby.keystore.ImportByKeystoreActivity;
import com.xm6leefun.wallet_module.importby.prikey.ImportByPrikeyActivity;
import com.xm6leefun.wallet_module.importby.words.ImportByWordsActivity;
import com.xm6leefun.wallet_module.wallet_manage.adapter.WalletListAdapter;
import com.xm6leefun.wallet_module.wallet_manage.mvp.WalletManageContract;
import com.xm6leefun.wallet_module.wallet_manage.mvp.WalletManagerPresenter;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/30 10:14
 */
@Route(path = ModuleRouterTable.HOME_WALLET_MANAGE)
public class WalletManageActivity extends BaseToolbarPresenterActivity<WalletManagerPresenter> implements WalletManageContract.IView, OnRefreshListener, BaseQuickAdapter.OnItemClickListener {
    @BindView(R2.id.ultra_pull)
    SmartRefreshLayout ultra_pull;
    @BindView(R2.id.id_wallet_rec)
    RecyclerView id_wallet_rec;
    @BindView(R2.id.wallet_rec)
    RecyclerView wallet_rec;
    @BindView(R2.id.import_layout)
    LinearLayout import_layout;

    private WalletListAdapter idWalletAdapter,normalWalletAdapter;
    @Override
    protected WalletManagerPresenter createPresenter() {
        return new WalletManagerPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wallet_manager_layout;
    }

    @Override
    protected void initView() {
        topBarTvTitle.setText(R.string.manager_wallet_title);
        addOnClickListeners(R.id.import_wallet_tv_create,R.id.create_wallet_tv_create);
        ultra_pull.setRefreshHeader(new ClassicsHeader(this));
        ultra_pull.setOnRefreshListener(this);
        id_wallet_rec.setLayoutManager(new WrapContentLinearLayoutManager(this));
        id_wallet_rec.addItemDecoration(new SpacesItemDecoration(40));
        wallet_rec.setLayoutManager(new WrapContentLinearLayoutManager(this));
        wallet_rec.addItemDecoration(new SpacesItemDecoration(40));
    }

    @Override
    protected void initData() {
        presenter.getWalletList();
    }

    @Override
    public void getWalletListSuccess(List<Wallet_Main> idWalletList,List<Wallet_Main> normalWalletList) {
        ultra_pull.finishRefresh();
        if(idWalletAdapter == null){
            idWalletAdapter = new WalletListAdapter(R.layout.item_list_wallet, idWalletList);
            idWalletAdapter.setOnItemClickListener(this);
            id_wallet_rec.setAdapter(idWalletAdapter);
        }else{
            idWalletAdapter.setNewData(idWalletList);
        }

        if(normalWalletList.size()>0) {
            import_layout.setVisibility(View.VISIBLE);
            if (normalWalletAdapter == null) {
                normalWalletAdapter = new WalletListAdapter(R.layout.item_list_wallet, normalWalletList);
                normalWalletAdapter.setOnItemClickListener(this);
                wallet_rec.setAdapter(normalWalletAdapter);
            } else {
                normalWalletAdapter.setNewData(normalWalletList);
            }
        }else{
            import_layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoadFail(String msg) {
        ultra_pull.finishRefresh();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if(id == R.id.import_wallet_tv_create){//导入钱包
            showImportWayDialog();
        }else if(id == R.id.create_wallet_tv_create){//添加钱包
            ModuleServiceUtils.navigateFirstPage(false);
        }
    }

    private ImportWayDialog importWayDialog;

    /**
     * 选择导入方式
     */
    private void showImportWayDialog() {
        if(importWayDialog == null)
            importWayDialog = new ImportWayDialog.Builder().setClickListener(importType -> {
                switch (importType){
                    case ImportWayDialog.WORDS:
                        ActivityUtil.startActivity(ImportByWordsActivity.class,false);
                        break;
                    case ImportWayDialog.PRI_KEY:
                        ActivityUtil.startActivity(ImportByPrikeyActivity.class,false);
                        break;
                    case ImportWayDialog.KEYSTORE:
                        ActivityUtil.startActivity(ImportByKeystoreActivity.class,false);
                        break;
                }
            }).build();
        if(!importWayDialog.isAdded()) importWayDialog.show(mFragmentManager,ImportWayDialog.TAG);
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        presenter.getWalletList();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Wallet_Main walletMain = (Wallet_Main) adapter.getData().get(position);
        ModuleServiceUtils.navigateWalletSettingPage(walletMain.getAddress());
    }
}
