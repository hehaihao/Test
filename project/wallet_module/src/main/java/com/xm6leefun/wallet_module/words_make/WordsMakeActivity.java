package com.xm6leefun.wallet_module.words_make;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.db.bean.Wallet_Main;
import com.xm6leefun.common.refresh_view.itemdecora.ItemGridDecoration;
import com.xm6leefun.common.refresh_view.itemdecora.SpacesItemDecoration;
import com.xm6leefun.common.utils.AESUtil;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.utils.DoubleClickUtils;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.common.utils.status_bar.StatusBarUtil;
import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.R2;
import com.xm6leefun.wallet_module.backup_tips.mvp.CusCreateWalletBean;
import com.xm6leefun.common.wallet.KeysInfo;
import com.xm6leefun.common.wallet.WalletUtil;
import com.xm6leefun.wallet_module.words_confirm.WordsConfirmActivity;
import com.xm6leefun.wallet_module.words_make.adapter.MnemonicAdapter;
import com.xm6leefun.wallet_module.words_make.bean.CusMnemonic;
import com.xm6leefun.wallet_module.words_make.mvp.WordsMakeContract;
import com.xm6leefun.wallet_module.words_make.mvp.WordsMakePresenter;

import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @Description: 抄写助记词
 * @Author: hhh
 * @CreateDate: 2021/3/26 16:38
 */
public class WordsMakeActivity extends BaseToolbarPresenterActivity<WordsMakePresenter> implements WordsMakeContract.IView {
    public static final String CUS_DATA = "cus_data";

    @BindView(R2.id.words_make_rv)
    RecyclerView mRecyclerView;

    private CusCreateWalletBean cusCreateWallet;
    private KeysInfo ocKeysInfo = new KeysInfo();
    private List<String> wordsString = new ArrayList<>();
    private List<CusMnemonic> cusMnemonicList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_words_make_layout;
    }

    @Override
    protected void initView() {
        topBarTvTitle.setText(R.string.words_make_title);
        GridLayoutManager mLayoutManager = new GridLayoutManager(WordsMakeActivity.this, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new ItemGridDecoration(this, 1,R.color.color_dbdbdb));
        addOnClickListeners(R.id.words_make_tv_confirm);
    }

    private MnemonicAdapter wordsAdapter = null;

    @Override
    protected void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            cusCreateWallet = bundle.getParcelable(CUS_DATA);
            dealWalletData(cusCreateWallet);
        }
    }

    /**
     * 处理钱包数据
     * @param cusCreateWallet
     */
    private void dealWalletData(CusCreateWalletBean cusCreateWallet) {
        if (cusCreateWallet != null) {
            int where = cusCreateWallet.getWhere();  // 0 创建，1 导入，2 备份
            ocKeysInfo = cusCreateWallet.getKeysInfo();
            switch (where){
                case 0:  // 0 创建
                    for (int i = 0; i < ocKeysInfo.getWords().size(); i++){
                        CusMnemonic cusMnemonic = new CusMnemonic(ocKeysInfo.getWords().get(i), false, true, i);
                        cusMnemonicList.add(cusMnemonic);
                    }
                    initAdapter();
                    break;
                case 2:  // 2 备份
                    presenter.getCurrWallet(cusCreateWallet.getAddress());
                    break;
            }
        }
    }

    private void initAdapter() {
        if (wordsAdapter == null){
            wordsAdapter = new MnemonicAdapter(WordsMakeActivity.this, R.layout.item_words, cusMnemonicList, false);
        }
        mRecyclerView.setAdapter(wordsAdapter);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.words_make_tv_confirm) {
            if (DoubleClickUtils.isNoDoubleClick()) {
                cusCreateWallet.setKeysInfo(ocKeysInfo);
                Bundle bundle = new Bundle();
                bundle.putParcelable(WordsConfirmActivity.CUS_DATA, cusCreateWallet);
                ActivityUtil.startActivity(WordsConfirmActivity.class, false, bundle);
            }
        }
    }

    @Override
    protected WordsMakePresenter createPresenter() {
        return new WordsMakePresenter(this);
    }

    @Override
    public void getCurrWalletSuccess(Wallet_Main walletMain) {
        String wordsKeystore = walletMain.getWordsJson();
        if (!StrUtils.isEmpty(wordsKeystore)) {
            String decryptWords = AESUtil.decrypt(cusCreateWallet.getPwd(), wordsKeystore);
            if (!StrUtils.isEmpty(decryptWords)) {
                wordsString = new Gson().fromJson(decryptWords, ArrayList.class);
                if (wordsString != null && wordsString.size() > 0) {
                    for (int i = 0; i < wordsString.size(); i++){
                        CusMnemonic cusMnemonic = new CusMnemonic(wordsString.get(i), false, true, i);
                        cusMnemonicList.add(cusMnemonic);
                    }
                    initAdapter();
                    this.ocKeysInfo = WalletUtil.importWalletByWords(wordsString);
                }
            }
        }
    }

    @Override
    public void onLoadFail(String msg) {
        ToastUtil.showCenterToast(msg);
    }
}
