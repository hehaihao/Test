package com.xm6leefun.points_module.receive_code.mvp;

import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.common.db.bean.Wallet_Main;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:50
 */
public class ReceiveQrCodePresenter extends BasePresenter<ReceiveQrCodeContract.IView> {
    private  ReceiveQrCodeContract.IModel iModel;
    public ReceiveQrCodePresenter(ReceiveQrCodeContract.IView baseView) {
        super(baseView);
        iModel = new ReceiveQrCodeModelImp();
    }

    /**
     * 获取当前选中地址
     */
    public void getCurrWallet(){
        //从数据库读取数据
        addDisposableObserveOnMain(iModel.getCurrWallet(), new BaseObserver<Wallet_Main>() {
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
