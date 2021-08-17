package com.xm6leefun.wallet_module.home_wallet.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xm6leefun.common.utils.ImageLoader;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.wallet_module.R;

import java.util.List;

import androidx.annotation.Nullable;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/30 9:49
 */
public class HomePhysicalListAdapter extends BaseQuickAdapter<HomeDataResultBean.NftBean, BaseViewHolder> {
    private Context context;
    public HomePhysicalListAdapter(Context context, int layoutResId, @Nullable List<HomeDataResultBean.NftBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeDataResultBean.NftBean item) {
        ImageView img = helper.getView(R.id.img);
        TextView contract_address_tv = helper.getView(R.id.contract_address_tv);
        TextView name_tv = helper.getView(R.id.name_tv);

        ImageLoader.loadRadiusCenterInsideImage(context, item.getToken_logo(),img);
        name_tv.setText(context.getString(R.string.home_contract_name_str,item.getChain_name()));
        contract_address_tv.setText(context.getString(R.string.home_contract_address_str,item.getContract_address()));

    }
}
