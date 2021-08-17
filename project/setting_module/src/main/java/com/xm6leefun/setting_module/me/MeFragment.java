package com.xm6leefun.setting_module.me;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xm6leefun.common.app.fragment.BaseComFragment;
import com.xm6leefun.common.utils.status_bar.StatusBarUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.setting_module.R;
import com.xm6leefun.setting_module.R2;

import butterknife.BindView;

/**
 * @Description:我的
 * @Author: hhh
 * @CreateDate: 2021/4/19 11:32
 */
@Route(path = ModuleRouterTable.HOME_ME_FRAGMENT)
public class MeFragment extends BaseComFragment {
    @BindView(R2.id.root_view)
    LinearLayout root_view;
    @BindView(R2.id.base_topBar_iv_back)
    ImageView base_topBar_iv_back;
    @BindView(R2.id.base_topBar_iv_right)
    ImageView base_topBar_iv_right;
    @BindView(R2.id.base_topBar_tv_title)
    TextView base_topBar_tv_title;
    @BindView(R2.id.wallet_layout)
    RelativeLayout wallet_layout;
    @BindView(R2.id.address_layout)
    RelativeLayout address_layout;
    @BindView(R2.id.system_block_browser_layout)
    RelativeLayout system_block_browser_layout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me_layout;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setLightModeFull(getActivity());//亮色模式，字体为暗色
        root_view.setPadding(0,getStatusBarHeight(),0,0);
        base_topBar_tv_title.setText(R.string.me);
        base_topBar_iv_back.setVisibility(View.GONE);
        base_topBar_iv_right.setImageResource(R.mipmap.setting_icon);
        base_topBar_iv_right.setOnClickListener(this);
        wallet_layout.setOnClickListener(this);
        address_layout.setOnClickListener(this);
        system_block_browser_layout.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.base_topBar_iv_right){
            ARouter.getInstance().build(ModuleRouterTable.SYSTEM_SETTING).navigation();
        }else if(id == R.id.wallet_layout){
            ModuleServiceUtils.navigateWalletManagePage();
        }else if(id == R.id.address_layout){
            ARouter.getInstance().build(ModuleRouterTable.ADDRESS_LIST_PAGE).navigation();
        }else if(id == R.id.system_block_browser_layout){
            ARouter.getInstance().build(ModuleRouterTable.BLOCK_BROWSER_PAGE).navigation();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            StatusBarUtil.setLightModeFull(getActivity());//亮色模式，字体为暗色
        }
    }
}
