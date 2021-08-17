package com.xm6leefun.wallet_module.first;

import android.view.View;
import android.widget.LinearLayout;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.xm6leefun.common.app.activity.BaseComActivity;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.export_module.ModuleServiceUtils;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.R2;
import com.xm6leefun.wallet_module.first.dialog.ImportWayDialog;
import com.xm6leefun.wallet_module.importby.keystore.ImportByKeystoreActivity;
import com.xm6leefun.wallet_module.importby.prikey.ImportByPrikeyActivity;
import com.xm6leefun.wallet_module.importby.words.ImportByWordsActivity;

import butterknife.BindView;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/25 10:49
 */
@Route(path = ModuleRouterTable.WALLET_FIRST_PAGE)
public class FirstEntryActivity extends BaseComActivity{
    @BindView(R2.id.first_entry_create)
    LinearLayout first_entry_create;
    @BindView(R2.id.first_entry_import)
    LinearLayout first_entry_import;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_first_entry_layout;
    }


    @Override
    protected void initView() {
        addOnClickListeners(R.id.first_entry_create,R.id.first_entry_import);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.first_entry_create) {
            ModuleServiceUtils.navigateFirstPage(false);
        } else if (id == R.id.first_entry_import) {
            showImportWayDialog();

        }
    }

    private ImportWayDialog importWayDialog;
    private void showImportWayDialog() {
        if(importWayDialog == null)
            importWayDialog = new ImportWayDialog.Builder().setClickListener(importType -> {
                switch (importType){
                    case ImportWayDialog.WORDS:
                        ActivityUtil.startActivity(ImportByWordsActivity.class,false);
                        break;
                    case ImportWayDialog.PRI_KEY:
                        ActivityUtil.startActivity(ImportByPrikeyActivity.class,false);
                        break;
                    case ImportWayDialog.KEYSTORE:
                        ActivityUtil.startActivity(ImportByKeystoreActivity.class,false);
                        break;
                }
            }).build();
        if(!importWayDialog.isAdded()) importWayDialog.show(mFragmentManager,ImportWayDialog.TAG);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
