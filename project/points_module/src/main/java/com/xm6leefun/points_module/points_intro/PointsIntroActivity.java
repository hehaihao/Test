package com.xm6leefun.points_module.points_intro;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.points_module.R;
import com.xm6leefun.points_module.R2;
import com.xm6leefun.points_module.points_intro.mvp.PointsIntroBean;
import com.xm6leefun.points_module.points_intro.mvp.PointsIntroContract;
import com.xm6leefun.points_module.points_intro.mvp.PointsIntroPresenter;

import androidx.core.widget.NestedScrollView;
import butterknife.BindView;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/4/6 16:20
 */
public class PointsIntroActivity extends BaseToolbarPresenterActivity<PointsIntroPresenter> implements PointsIntroContract.IView {
    public static final String NAME = "name";
    public static final String ID = "id";
    @BindView(R2.id.emptyView)
    LinearLayout emptyView;
    @BindView(R2.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R2.id.inc_tv)
    TextView inc_tv;
    @BindView(R2.id.contract_address_tv)
    TextView contract_address_tv;
    @BindView(R2.id.introduction_tv)
    TextView introduction_tv;
    @BindView(R2.id.status_tv)
    TextView status_tv;
    @BindView(R2.id.issued_date_tv)
    TextView issued_date_tv;

    @Override
    protected PointsIntroPresenter createPresenter() {
        return new PointsIntroPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_points_intro_layout;
    }

    @Override
    protected void initView() {

    }

    private String name = "";
    private String id = "";
    @Override
    protected void initData() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            name = extras.getString(NAME, "");
            id = extras.getString(ID, "");
        }
        topBarTvTitle.setText(name);
        presenter.getInfo(id);
    }

    @Override
    public void getInfoSuccess(PointsIntroBean pointsIntroBean) {
        emptyView.setVisibility(View.GONE);
        nestedScrollView.setVisibility(View.VISIBLE);
        inc_tv.setText(pointsIntroBean.getCompany_name());
        contract_address_tv.setText(pointsIntroBean.getContract_address());
        introduction_tv.setText(pointsIntroBean.getToken_introduce());
        status_tv.setText(pointsIntroBean.getStatus());
        issued_date_tv.setText(pointsIntroBean.getCreate_time());
    }

    @Override
    public void onLoadFail(String msg) {
        emptyView.setVisibility(View.VISIBLE);
        nestedScrollView.setVisibility(View.GONE);
        ToastUtil.showCenterToast(msg);
    }
}
