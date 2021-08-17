package com.xm6leefun.wallet_module.wallet_manage.mvp;

import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.common.db.bean.Wallet_Main;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/26 10:06
 */
public class WalletManagerPresenter extends BasePresenter<WalletManageContract.IView> {
    private WalletManageContract.IModel model;
    public WalletManagerPresenter(WalletManageContract.IView baseView) {
        super(baseView);
        model = new WalletManageModelImpl();
    }

    /**
     * 获取钱包列表
     */
    public void getWalletList() {
        addDisposableObserveOnMain(model.getWalletList(), new BaseObserver<List<Wallet_Main>>() {
            @Override
            public void onSuccess(List<Wallet_Main> mainList) {
                List<Wallet_Main> idWalletList = new ArrayList<>();
                List<Wallet_Main> normalWalletList = new ArrayList<>();
                for(Wallet_Main wallet_main : mainList){
                    if(wallet_main.isIdWallet())
                        idWalletList.add(wallet_main);
                    else
                        normalWalletList.add(wallet_main);
                }
                baseView.getWalletListSuccess(idWalletList,normalWalletList);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }
}
