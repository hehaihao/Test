package com.xm6leefun.wallet_module.home_wallet.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xm6leefun.common.utils.AppAllKey;
import com.xm6leefun.common.utils.ImageLoader;
import com.xm6leefun.common.utils.SharePreferenceUtil;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.wallet_module.R;
import java.util.List;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/30 9:49
 */
public class HomeAssetListAdapter extends BaseQuickAdapter<HomeDataResultBean.FtBean, BaseViewHolder> {
    private Context context;
    public HomeAssetListAdapter(Context context,int layoutResId, @Nullable List<HomeDataResultBean.FtBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeDataResultBean.FtBean item) {
        ImageView icon = helper.getView(R.id.icon);
        TextView name_tv = helper.getView(R.id.name_tv);
        TextView balance_tv = helper.getView(R.id.balance_tv);
        name_tv.setText(item.getToken_name());
        balance_tv.setText(item.getNum()+"");

        ImageLoader.loadCircleImage(context, item.getToken_logo(),icon);

    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }
}
