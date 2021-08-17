package com.xm6leefun.points_module.points_type;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.refresh_view.anim.ScaleInRightAnimator;
import com.xm6leefun.common.refresh_view.layoutmanager.WrapContentLinearLayoutManager;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.widget.EmptyDataRecyclerView;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.export_module.bean.SelectAddressBean;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.points_module.R;
import com.xm6leefun.points_module.R2;
import com.xm6leefun.points_module.points_type.adapter.PointsTypeAdapter;
import com.xm6leefun.points_module.points_type.mvp.PointsTypeContract;
import com.xm6leefun.points_module.points_type.mvp.PointsTypePresenter;

import java.util.List;

import butterknife.BindView;

/**
 * 选择积分/币种类型
 */
@Route(path = ModuleRouterTable.POINTS_TYPE_PAGE)
public class PointsTypeActivity extends BaseToolbarPresenterActivity<PointsTypePresenter> implements PointsTypeContract.IView, BaseQuickAdapter.OnItemClickListener {
    @BindView(R2.id.recycler)
    EmptyDataRecyclerView recycler;
    private RecyclerView typeRec;
    private PointsTypeAdapter adapter;
    private String mCurAddress = "";

    @Override
    protected PointsTypePresenter createPresenter() {
        return new PointsTypePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_points_type;
    }

    @Override
    protected void initView() {
        topBarTvTitle.setText("积分列表");
        typeRec = recycler.getmRecyclerView();
        typeRec.setItemAnimator(new ScaleInRightAnimator(new OvershootInterpolator(1.0f)));
        typeRec.setLayoutManager(new WrapContentLinearLayoutManager(this));

    }

    @Override
    protected void initData() {
        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            mCurAddress = extras.getString(ModuleServiceUtils.ADDRESS,"");
            presenter.getList(mCurAddress);
        }

    }

    @Override
    public void getListSuccess(List<HomeDataResultBean.FtBean> listBean) {
        if (adapter == null){
            adapter = new PointsTypeAdapter(listBean);
            adapter.setOnItemClickListener(this);
            typeRec.setAdapter(adapter);
        }else{
            adapter.setNewData(listBean);
        }
    }

    @Override
    public void onLoadFail(String msg) {
        ToastUtil.showCenterToast(msg);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        HomeDataResultBean.FtBean ftBean = (HomeDataResultBean.FtBean) adapter.getData().get(position);
        Intent resultIntent = new Intent();
        resultIntent.putExtra(ModuleServiceUtils.INTENT_EXTRA_KEY_SELECT_POINTS, ftBean);
        setResult(ModuleServiceUtils.RESULT_CODE_SELECT_POINTS, resultIntent);
        ActivityUtil.finishActivity(this);
    }
}
