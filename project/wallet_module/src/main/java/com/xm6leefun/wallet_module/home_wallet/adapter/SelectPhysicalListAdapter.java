package com.xm6leefun.wallet_module.home_wallet.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xm6leefun.common.utils.ImageLoader;
import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.home_wallet.mvp.PhysicalListBean;
import java.util.List;
import androidx.annotation.Nullable;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2020/9/23 14:57
 */
public class SelectPhysicalListAdapter extends BaseQuickAdapter<PhysicalListBean, BaseViewHolder> {
    private Context context;
    public SelectPhysicalListAdapter(Context context, int layoutResId, @Nullable List<PhysicalListBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, PhysicalListBean item) {
        ImageView img = helper.getView(R.id.img);
        TextView token_id_tv = helper.getView(R.id.token_id_tv);
        TextView name_tv = helper.getView(R.id.name_tv);
        TextView contract_address_tv = helper.getView(R.id.contract_address_tv);
        ImageLoader.loadRadiusImage(context,item.getNft_img(),30,img);
        name_tv.setText(item.getNft_name());
        token_id_tv.setText(context.getString(R.string.home_token_id_data_str,item.getId()+""));
        contract_address_tv.setText(context.getString(R.string.home_contract_address_str,item.getContract_address()));

    }
}
