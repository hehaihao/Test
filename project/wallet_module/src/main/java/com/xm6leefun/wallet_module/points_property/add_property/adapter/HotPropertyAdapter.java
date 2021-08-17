package com.xm6leefun.wallet_module.points_property.add_property.adapter;


import android.widget.ImageView;

import androidx.annotation.Nullable;
import io.realm.Realm;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xm6leefun.common.db.bean.Points_Assets;
import com.xm6leefun.common.db.utils.RealmUtils;
import com.xm6leefun.common.utils.ImageLoader;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.wallet_module.R;



import java.util.List;


/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/15
 */
public class HotPropertyAdapter extends BaseQuickAdapter<HomeDataResultBean.FtBean, BaseViewHolder> {

    public HotPropertyAdapter(@Nullable List<HomeDataResultBean.FtBean> data,HotPropertyAddListener listener) {
        super(R.layout.list_item_hot_property, data);
        this.listener = listener;
    }

    /**
     * 数据库中存在的资产id
     */
    private List<Long> ids;
    public void setIds(List<Long> ids){
        this.ids = ids;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeDataResultBean.FtBean item) {
        helper.setText(R.id.tv_property, item.getToken_name());

        helper.setText(R.id.tv_hash, item.getContract_address());

        ImageView iv_icon = helper.getView(R.id.iv_icon);

        ImageView iv_add = helper.getView(R.id.iv_add);

        ImageLoader.loadCircleImage(mContext, item.getToken_logo(), iv_icon);

        int imgRes;
        if(ids !=null && ids.contains(item.getId())){//数据库中包含id
            imgRes = R.mipmap.icon_check_selected;
        }else{
            imgRes = R.mipmap.icon_add;
        }
        iv_add.setImageResource(imgRes);
        iv_add.setOnClickListener(v -> {
            if (listener!=null && imgRes == R.mipmap.icon_add){//避免重复请求添加接口
                listener.addProperty(item);
            }
        });
    }

    private HotPropertyAddListener listener;
    public interface HotPropertyAddListener{
        void addProperty(HomeDataResultBean.FtBean item);

    }
}