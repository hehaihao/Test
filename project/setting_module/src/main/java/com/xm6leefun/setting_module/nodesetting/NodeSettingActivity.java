package com.xm6leefun.setting_module.nodesetting;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xm6leefun.common.BuildConfig;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.refresh_view.layoutmanager.WrapContentLinearLayoutManager;
import com.xm6leefun.common.utils.ConstantValue;
import com.xm6leefun.common.utils.SharePreferenceUtil;
import com.xm6leefun.common.widget.EmptyDataRecyclerView;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.setting_module.R;
import com.xm6leefun.setting_module.R2;
import com.xm6leefun.setting_module.nodesetting.adapter.NodeSettingAdapter;
import com.xm6leefun.setting_module.nodesetting.mvp.NodeBean;
import com.xm6leefun.setting_module.nodesetting.mvp.NodeSettingContract;
import com.xm6leefun.setting_module.nodesetting.mvp.NodeSettingPresenter;
import org.onlychain.wallet.base.ApiConfig;
import java.util.List;
import butterknife.BindView;


/**
 * @Description:节点设置
 * @Author: cyh
 * @CreateDate: 2021/4/12
 */

@Route(path = ModuleRouterTable.NODE_SETTING)
public class NodeSettingActivity extends BaseToolbarPresenterActivity<NodeSettingPresenter> implements NodeSettingContract.IView, BaseQuickAdapter.OnItemClickListener {


    @BindView(R2.id.node_rec_layout)
    EmptyDataRecyclerView node_rec_layout;
    private RecyclerView nodeRec;

    NodeSettingAdapter adapter;

    @Override
    protected NodeSettingPresenter createPresenter() {
        return new NodeSettingPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_node_setting;
    }

    @Override
    protected void initView() {
        topBarTvTitle.setText(R.string.node_setting_str);
        nodeRec = node_rec_layout.getmRecyclerView();
        nodeRec.setLayoutManager(new WrapContentLinearLayoutManager(this));
        mCurrNode = ApiConfig.SUB_IP.replace(BuildConfig.SUB_VERSION,"");
    }

    @Override
    protected void initData() {
        presenter.getList();
    }

    private String mCurrNode = "";
    @Override
    public void getListSuccess(List<NodeBean> listBeans) {
        if(listBeans != null && listBeans.size() > 0) {
            node_rec_layout.showEmptyView(listBeans.size(),1,false);
            if(adapter == null){
                adapter = new NodeSettingAdapter(R.layout.list_item_node_setting,listBeans,mCurrNode);
                adapter.setOnItemClickListener(this);
                nodeRec.setAdapter(adapter);
            }else{
                adapter.setNewData(listBeans);
            }
        }else{
            node_rec_layout.showEmptyView(0,1,false);
        }
    }

    @Override
    public void onLoadFail(String msg) {
        ToastUtil.showCenterToast(msg);
        node_rec_layout.showEmptyView(0,1,false);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        NodeSettingAdapter nodeSettingAdapter = (NodeSettingAdapter) adapter;
        NodeBean bean = nodeSettingAdapter.getData().get(position);
        String nodeUrl = bean.getNodeUrl();
        if(mCurrNode.equals(nodeUrl)){
            return;
        }
        mCurrNode = nodeUrl;
        nodeSettingAdapter.setmCurrNode(mCurrNode);
        adapter.notifyDataSetChanged();
        //设置节点，切换子链，侧链不需要变化
        ApiConfig.init(BuildConfig.SIDE_IP + BuildConfig.SIDE_VERSION,mCurrNode + BuildConfig.SUB_VERSION);
        SharePreferenceUtil.setString(ConstantValue.CURRENT_NODE,mCurrNode);
    }
}
