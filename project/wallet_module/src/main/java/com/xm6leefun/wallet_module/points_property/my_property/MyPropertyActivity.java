package com.xm6leefun.wallet_module.points_property.my_property;

import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.refresh_view.layoutmanager.WrapContentLinearLayoutManager;
import com.xm6leefun.common.widget.EmptyDataRecyclerView;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.R2;
import com.xm6leefun.wallet_module.points_property.my_property.adapter.MyPropertyAdapter;
import com.xm6leefun.wallet_module.points_property.my_property.mvp.MyPropertyContract;
import com.xm6leefun.wallet_module.points_property.my_property.mvp.MyPropertyPresenter;


import java.util.List;

import butterknife.BindView;

public class MyPropertyActivity extends BaseToolbarPresenterActivity<MyPropertyPresenter> implements MyPropertyContract.IView {


    @BindView(R2.id.select_contract_address_tv)
    EditText selectContractAddressTv;

    @BindView(R2.id.rec_layout)
    EmptyDataRecyclerView recLayout;

    RecyclerView rec_my_property;

    private MyPropertyAdapter adapter;

    @Override
    protected MyPropertyPresenter createPresenter() {
        return new MyPropertyPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_property;
    }

    @Override
    protected void initView() {
        topBarTvTitle.setText(R.string.my_property_title);
        rec_my_property = recLayout.getmRecyclerView();
        rec_my_property.setLayoutManager(new WrapContentLinearLayoutManager(this));
        addOnClickListeners(R.id.search_iv);
    }

    @Override
    protected void initData() {
        presenter.getList("");
    }


    @Override
    public void getListSuccess(List<HomeDataResultBean.FtBean> listBean) {
        if (adapter == null) {
            adapter = new MyPropertyAdapter(listBean);
            rec_my_property.setAdapter(adapter);
        } else {
            adapter.setNewData(listBean);
        }

    }

    @Override
    public void onLoadFail(String msg) {

    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.search_iv) {
            presenter.getList(selectContractAddressTv.getText().toString());
        }
    }
}
