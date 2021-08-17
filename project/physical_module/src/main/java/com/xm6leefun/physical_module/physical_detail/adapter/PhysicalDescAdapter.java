package com.xm6leefun.physical_module.physical_detail.adapter;

import android.content.Context;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xm6leefun.physical_module.R;
import java.util.List;
import androidx.annotation.Nullable;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2020/9/23 14:57
 */
public class PhysicalDescAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;
    public PhysicalDescAdapter(Context context, int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView desc_tv = helper.getView(R.id.desc_tv);
        desc_tv.setText(item);
    }
}
