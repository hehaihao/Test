package com.xm6leefun.wallet_module.points_property.property_manager;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;

import com.xm6leefun.common.refresh_view.layoutmanager.WrapContentLinearLayoutManager;

import com.xm6leefun.common.widget.EmptyDataRecyclerView;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.bean.HomeDataResultBean;

import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.R2;
import com.xm6leefun.wallet_module.points_property.add_property.bean.RefreshPropertyBunBean;
import com.xm6leefun.wallet_module.points_property.property_manager.adapter.PropertyManagerAdapter;
import com.xm6leefun.wallet_module.points_property.property_manager.mvp.PropertyManagerContract;
import com.xm6leefun.wallet_module.points_property.property_manager.mvp.PropertyManagerPresenter;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;



public class PropertyManagerActivity extends BaseToolbarPresenterActivity<PropertyManagerPresenter> implements PropertyManagerContract.IView, PropertyManagerAdapter.CheckItemListener,OnItemDragListener {


    @BindView(R2.id.select_contract_address_tv)
    EditText selectContractAddressTv;
    @BindView(R2.id.rec_layout)
    EmptyDataRecyclerView recLayout;


    RecyclerView rec_property;

    private PropertyManagerAdapter adapter;


    private List<HomeDataResultBean.FtBean> removeList = new ArrayList<>();

    @Override
    protected PropertyManagerPresenter createPresenter() {
        return new PropertyManagerPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_property_manager;
    }

    @Override
    protected void initView() {
        topBarTvTitle.setText(R.string.property_manager_title);
        topBarTvRight.setText(getString(R.string.edit));
        topBarTvRight.setVisibility(View.VISIBLE);
        rec_property = recLayout.getmRecyclerView();
        rec_property.setLayoutManager(new WrapContentLinearLayoutManager(this));
        addOnClickListeners(R.id.base_topBar_tv_right,R.id.search_iv);
    }

    private String mAddress = "";
    @Override
    protected void initData() {
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            mAddress = extras.getString(ModuleServiceUtils.ADDRESS,"");
        }
        presenter.getList("");
    }


    @Override
    public void getListSuccess(List<HomeDataResultBean.FtBean> listBean) {
        if (adapter == null) {
            adapter = new PropertyManagerAdapter(listBean, this);
            rec_property.setAdapter(adapter);
            ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adapter);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
            itemTouchHelper.attachToRecyclerView(rec_property);
            // 开启拖拽
            adapter.enableDragItem(itemTouchHelper,R.id.iv_edit,true);
            adapter.setOnItemDragListener(this);
        } else {
            adapter.setNewData(listBean);
        }
    }

    @Override
    public void onLoadFail(String msg) {
        ToastUtil.showCenterToast(msg);
    }

    @Override
    public void deleteListSuccess() {
        adapter.isEditModel = false;
        adapter.notifyDataSetChanged();
        topBarTvRight.setText(getString(R.string.edit));
        removeList.clear();
        presenter.getList(selectContractAddressTv.getText().toString());
        EventBus.getDefault().post(new RefreshPropertyBunBean());//刷新
    }

    @Override
    public void deleteListFail(String msg) {
        ToastUtil.showCenterToast(msg);
    }

    @Override
    public void sortListSuccess(String msg) {
        presenter.getList(selectContractAddressTv.getText().toString());
    }

    @Override
    public void sortListFail(String msg) {

    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();

        if (id == R.id.search_iv) {

            presenter.getList(selectContractAddressTv.getText().toString());

        }else if (id == R.id.base_topBar_tv_right) {

            if (adapter == null) return;

            if (!adapter.isEditModel) {

                adapter.isEditModel = true;

                adapter.notifyDataSetChanged();

                topBarTvRight.setText(getString(R.string.delete));
            }

            if (adapter.isEditModel) {

                if (adapter.getData() == null) return;

                if (adapter.getData().size() == 0) return;

                if (removeList.size() == 0) return;

                presenter.deleteList(removeList,mAddress);
            }
        }
    }

    @Override
    public void checked(HomeDataResultBean.FtBean item) {
        removeList.add(item);
    }

    @Override
    public void notChecked(HomeDataResultBean.FtBean item) {
        removeList.remove(item);
    }


    @Override
    public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int i) {


    }

    @Override
    public void onItemDragMoving(RecyclerView.ViewHolder viewHolder, int i, RecyclerView.ViewHolder viewHolder1, int i1) {

    }

    @Override
    public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int i) {
        if (adapter.getData()!=null){
            for (int j = 0 ; j < adapter.getData().size() ; j++){
                adapter.getData().get(j).setPosition(j);
            }
            presenter.sortList(adapter.getData());
        }
    }


}
