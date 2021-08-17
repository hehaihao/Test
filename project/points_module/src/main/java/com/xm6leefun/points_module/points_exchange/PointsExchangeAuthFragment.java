package com.xm6leefun.points_module.points_exchange;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xm6leefun.common.app.fragment.BaseComFragment;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.utils.status_bar.StatusBarUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.points_module.R;
import com.xm6leefun.points_module.R2;

import butterknife.BindView;

/**
 * @Description:积分兑换授权
 * @Author: hhh
 * @CreateDate: 2021/4/7 17:45
 */
@Route(path = ModuleRouterTable.HOME_POINTS_EXCHANGE_AUTH_FRAGMENT)
public class PointsExchangeAuthFragment extends BaseComFragment {
    @BindView(R2.id.base_topBar_iv_back)
    ImageView base_topBar_iv_back;
    @BindView(R2.id.base_topBar_tv_title)
    TextView base_topBar_tv_title;
    @BindView(R2.id.root_view)
    LinearLayout root_view;
    @BindView(R2.id.bind_acount_tv)
    TextView bind_acount_tv;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_points_auth;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setLightModeFull(getActivity());//亮色模式，字体为暗色
        root_view.setPadding(0,getStatusBarHeight(),0,0);
        base_topBar_iv_back.setVisibility(View.GONE);
        base_topBar_tv_title.setText(R.string.points_toptitle_str);
        bind_acount_tv.setOnClickListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.bind_acount_tv){
            ModuleServiceUtils.navigateLoginPage(false);
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
