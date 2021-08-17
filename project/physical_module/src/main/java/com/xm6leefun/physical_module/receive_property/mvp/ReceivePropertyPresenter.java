package com.xm6leefun.physical_module.receive_property.mvp;

import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.common.db.bean.Wallet_Main;

public class ReceivePropertyPresenter extends BasePresenter<ReceivePropertyContract.IView> {

    private ReceivePropertyContract.IModel model;

    public ReceivePropertyPresenter(ReceivePropertyContract.IView baseView) {
        super(baseView);
        model = new ReceivePropertyModelImp();
    }
    /**
     * 获取当前选中地址
     */
    public void getCurrWallet(){
        //从数据库读取数据
        addDisposableObserveOnMain(model.getCurrWallet(), new BaseObserver<Wallet_Main>() {
            @Override
            public void onSuccess(Wallet_Main walletMain) {
                baseView.getCurrWalletSuccess(walletMain);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });

}
}