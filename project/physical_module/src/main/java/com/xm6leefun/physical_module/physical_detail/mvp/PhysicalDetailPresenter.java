package com.xm6leefun.physical_module.physical_detail.mvp;

import com.xm6leefun.common.base.BaseNftDataBean;
import com.xm6leefun.common.base.BaseObserver;
import com.xm6leefun.common.base.BasePresenter;
import com.xm6leefun.common.wallet.KeysInfo;
import com.xm6leefun.physical_module.physical_detail.api.PhysicalDetailApiService;
import com.xm6leefun.physical_module.physical_detail.bean.PhysicalDetailBean;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/3/29 14:50
 */
public class PhysicalDetailPresenter extends BasePresenter<PhysicalDetailContract.IView> {
    private  PhysicalDetailContract.IModel iModel;

    public PhysicalDetailPresenter(PhysicalDetailContract.IView baseView) {
        super(baseView);
        iModel = new PhysicalDetailModelImp(retrofitManager.createRs(PhysicalDetailApiService.class));
    }


    /**
     *
     * @param godId
     */
    public void getDetail(long godId){
        addDisposableObserveOnMain(iModel.getPhysicalDetail(godId), new BaseObserver<BaseNftDataBean<PhysicalDetailBean>>(baseView,true){
            @Override
            public void onSuccess(BaseNftDataBean<PhysicalDetailBean> o) {
                PhysicalDetailBean physicalDetailBean = o.getResult();

                if (physicalDetailBean == null){
                    baseView.getDetailFail("data null");
                    return;
                }

                if (physicalDetailBean.getNftInfo()!= null){
                    baseView.getDetailSuccess(physicalDetailBean.getNftInfo());
                }else{
                    baseView.getDetailFail("data null");
                }
                baseView.getListSuccess(physicalDetailBean.getTransfer());
            }

            @Override
            public void onError(int code, String msg) {
                baseView.getDetailFail(msg);
            }
        });
    }

    /**
     * 检验密码
     * @param nftInfoBean
     * @param toAddress
     * @param psw
     * @param wrongTipStr
     */
    public void checkPsw(PhysicalDetailBean.NftInfoBean nftInfoBean,String toAddress,String psw,String wrongTipStr){
        addDisposableObserveOnMain(iModel.checkPsw(psw,wrongTipStr), new BaseObserver<KeysInfo>() {
            @Override
            public void onSuccess(KeysInfo keysInfo) {
                baseView.checkPswResult(true,nftInfoBean,toAddress,psw,keysInfo);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.checkPswResult(false,nftInfoBean,toAddress,psw,null);
            }
        });
    }

    /**
     * 资产转移前處理數據
     * @param privateKey
     * @param nftInfoBean
     * @param toAddress
     */
    public void physicalTran(String privateKey, PhysicalDetailBean.NftInfoBean nftInfoBean, String toAddress){
        addDisposableObserveOnMain(iModel.dealPhysicalTranData(privateKey,nftInfoBean,toAddress), new BaseObserver<String>(baseView,true) {
            @Override
            public void onSuccess(String action) {
                subPhysicalTran(action);
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }
    /**
     * 资产转移
     */
    private void subPhysicalTran(String action){
        addDisposableObserveOnMain(iModel.physicalTran(action), new BaseObserver<BaseNftDataBean<String>>(baseView,true) {
            @Override
            public void onSuccess(BaseNftDataBean<String> o) {
                baseView.physicalTranSuccess();
            }

            @Override
            public void onError(int code, String msg) {
                baseView.onLoadFail(msg);
            }
        });
    }
}
