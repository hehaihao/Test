package com.xm6leefun.wallet_module.home_wallet;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xm6leefun.common.app.fragment.BaseComFragment;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.refresh_view.layoutmanager.WrapContentLinearLayoutManager;
import com.xm6leefun.common.widget.EmptyDataRecyclerView;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.R2;
import com.xm6leefun.wallet_module.home_wallet.adapter.HomeAssetListAdapter;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/4/16 17:03
 */
public class HomePointsFragment extends BaseComFragment implements BaseQuickAdapter.OnItemClickListener{
    public static final String TAG = HomePointsFragment.class.getSimpleName();
    @BindView(R2.id.points_count_tv)
    TextView points_count_tv;
    @BindView(R2.id.add_assets_iv)
    ImageView add_assets_iv;
    @BindView(R2.id.transfer_layout)
    LinearLayout transfer_layout;
    @BindView(R2.id.receive_layout)
    LinearLayout receive_layout;
    @BindView(R2.id.assets_rec_layout)
    EmptyDataRecyclerView assets_rec_layout;
    RecyclerView assets_rec;

    private HomeAssetListAdapter adapter;
    public static HomePointsFragment getInstance(){
        HomePointsFragment fragment = new HomePointsFragment();
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_points_layout;
    }

    @Override
    protected void initView() {
        transfer_layout.setOnClickListener(this);
        receive_layout.setOnClickListener(this);
        add_assets_iv.setOnClickListener(this);
        assets_rec = assets_rec_layout.getmRecyclerView();
        assets_rec.setLayoutManager(new WrapContentLinearLayoutManager(getContext()));
    }

    @Override
    protected void initData() {

    }

    private Wallet_Main mWalletMain;

    /**
     * 更新当前选中钱包
     * @param walletMainData
     */
    public void setWalletMainData(Wallet_Main walletMainData){
        mWalletMain = walletMainData;
    }

    private List<HomeDataResultBean.FtBean> ftBeanList;
    /**
     * 设置ft资产
     * @param ftBeanList
     */
    public void setFt(List<HomeDataResultBean.FtBean> ftBeanList) {
        this.ftBeanList = ftBeanList;
        if(isAdded()) {
            if (ftBeanList != null && ftBeanList.size() > 0) {
                int count = ftBeanList.size();
                points_count_tv.setText("("+count+")");
                assets_rec_layout.showEmptyView(count,1,false);
                if (adapter == null) {
                    adapter = new HomeAssetListAdapter(getContext(), R.layout.item_home_assets_list, ftBeanList);
                    adapter.setOnItemClickListener(this);
                    assets_rec.setAdapter(adapter);
                } else {
                    adapter.replaceData(ftBeanList);
                }
            } else {
                points_count_tv.setText("(0)");
                assets_rec_layout.showEmptyView(0,1,false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.transfer_layout){//发送积分
            if(ftBeanList != null && ftBeanList.size() > 0) {
                HomeDataResultBean.FtBean homeAssetsListBean = ftBeanList.get(0);//默认选择第一个积分类型
                ModuleServiceUtils.navigateSendPointsPage(homeAssetsListBean, mWalletMain.getAddress());
            }else{
                ToastUtil.showCenterToast(getString(R.string.points_empty_str));
            }
        }else if(id == R.id.receive_layout){//接收
            ModuleServiceUtils.navigateReceivePointsPage();
        }else if(id == R.id.add_assets_iv){//添加资产
            ModuleServiceUtils.navigatePointPropertyPage(mWalletMain.getAddress());
        }
    }

    /**
     * 资产交易列表
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        HomeDataResultBean.FtBean homeAssetsListBean = (HomeDataResultBean.FtBean) adapter.getData().get(position);
        ModuleServiceUtils.navigateTransactionPage(homeAssetsListBean);
    }
}
