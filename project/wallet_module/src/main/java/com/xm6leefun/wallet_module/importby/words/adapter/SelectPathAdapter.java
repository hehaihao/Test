package com.xm6leefun.wallet_module.importby.words.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xm6leefun.wallet_module.R;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2020/9/24 14:31
 */
public class SelectPathAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private String mCurrSelectPath;
    private Context context;
    public SelectPathAdapter(int layoutResId, @Nullable List<String> data, String mCurrSelectPath, Context context) {
        super(layoutResId, data);
        this.mCurrSelectPath = mCurrSelectPath;
        this.context = context;
    }

    public void setCurrSelectPath(String currSelectPath){
        this.mCurrSelectPath = currSelectPath;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView path_tv = helper.getView(R.id.path_tv);
        TextView default_tag = helper.getView(R.id.default_tag);
        default_tag.setVisibility(View.GONE);  // 因为与助记词导入路径共用，所以在此屏蔽该标志
        path_tv.setText(item);
        if(item.equals(mCurrSelectPath)){
            path_tv.setTextColor(ContextCompat.getColor(context,R.color.color_058cd9));
        }else{
            path_tv.setTextColor(ContextCompat.getColor(context,R.color.black));
        }
    }
}
