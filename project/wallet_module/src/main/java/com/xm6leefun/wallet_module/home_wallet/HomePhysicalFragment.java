package com.xm6leefun.wallet_module.home_wallet;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xm6leefun.common.app.fragment.BaseComFragment;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.refresh_view.layoutmanager.WrapContentLinearLayoutManager;
import com.xm6leefun.common.widget.EmptyDataRecyclerView;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.R2;
import com.xm6leefun.wallet_module.home_wallet.adapter.HomePhysicalListAdapter;
import com.xm6leefun.wallet_module.home_wallet.dialog.SelectPhysicalBottomSheetFragment;
import com.xm6leefun.wallet_module.home_wallet.listener.NftContentListener;

import java.util.List;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/4/16 17:03
 */
public class HomePhysicalFragment extends BaseComFragment implements BaseQuickAdapter.OnItemClickListener{
    public static final String TAG = HomePhysicalFragment.class.getSimpleName();
    @BindView(R2.id.physical_count_tv)
    TextView physical_count_tv;
    @BindView(R2.id.transfer_layout)
    LinearLayout transfer_layout;
    @BindView(R2.id.receive_layout)
    LinearLayout receive_layout;
    @BindView(R2.id.add_assets_iv)
    TextView add_assets_iv;
    @BindView(R2.id.physical_rec_layout)
    EmptyDataRecyclerView physical_rec_layout;
    RecyclerView physical_rec;
    private HomePhysicalListAdapter adapter;

    public static HomePhysicalFragment getInstance(){
        HomePhysicalFragment fragment = new HomePhysicalFragment();
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_physical_layout;
    }

    @Override
    protected void initView() {
        add_assets_iv.setOnClickListener(this);
        transfer_layout.setOnClickListener(this);
        receive_layout.setOnClickListener(this);
        physical_rec = physical_rec_layout.getmRecyclerView();
        physical_rec.setLayoutManager(new WrapContentLinearLayoutManager(getContext()));
    }

    @Override
    protected void initData() {
        initNftData(nftBeanList,nftNumBean);
    }

    private Wallet_Main mWalletMain;
    /**
     * 更新当前选中钱包
     * @param walletMainData
     */
    public void setWalletMainData(Wallet_Main walletMainData){
        mWalletMain = walletMainData;
    }

    private List<HomeDataResultBean.NftBean> nftBeanList;
    private HomeDataResultBean.NftNumBean nftNumBean;
    private NftContentListener listener;
    /**
     * 设置nft资产
     * @param nftBeanList
     */
    public void setNft(List<HomeDataResultBean.NftBean> nftBeanList,HomeDataResultBean.NftNumBean nftNumBean, NftContentListener listener) {
        this.nftBeanList = nftBeanList;
        this.nftNumBean = nftNumBean;
        this.listener = listener;
        initNftData(nftBeanList,nftNumBean);
    }

    private void initNftData(List<HomeDataResultBean.NftBean> nftBeanList,HomeDataResultBean.NftNumBean nftNumBean){
        if(isAdded()) {
            if (nftBeanList != null && nftBeanList.size() > 0) {
                int count = nftBeanList.size();
                if(nftNumBean != null)
                    physical_count_tv.setText(getString(R.string.home_wallet_physical_count,nftNumBean.getContractAddressNum(),nftNumBean.getCredentialsNum()));
                else
                    physical_count_tv.setText(getString(R.string.home_wallet_physical_count,0,0));
                physical_rec_layout.showEmptyView(count,1,false);
                if (adapter == null) {
                    adapter = new HomePhysicalListAdapter(getContext(), R.layout.item_home_list_physical, nftBeanList);
                    adapter.setOnItemClickListener(this);
                    physical_rec.setAdapter(adapter);
                } else {
                    adapter.replaceData(nftBeanList);
                }
            } else {
                physical_count_tv.setText(getString(R.string.home_wallet_physical_count,0,0));
                physical_rec_layout.showEmptyView(0,1,false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.transfer_layout){//发送
            if(listener == null) {
                return;
            }
            listener.getNftList(mWalletMain.getAddress());
        }else if(id == R.id.receive_layout){//接收
            ModuleServiceUtils.navigateReceivePropertyPage();
        }else if(id == R.id.add_assets_iv){//转移记录
            ModuleServiceUtils.navigatePhysicalRecordListPage(mWalletMain.getAddress());
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        HomeDataResultBean.NftBean data = (HomeDataResultBean.NftBean) adapter.getData().get(position);
        ModuleServiceUtils.navigatePhysicalListPage(data,mWalletMain.getAddress());
    }
}
