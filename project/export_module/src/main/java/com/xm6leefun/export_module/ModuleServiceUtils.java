package com.xm6leefun.export_module;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xm6leefun.export_module.bean.HomeDataResultBean;
import com.xm6leefun.export_module.router.ModuleRouterTable;

/**
 * @Description: 组件服务工具类，其他组件直接使用此类即可：页面跳转、获取服务。
 * @Author: hhh
 * @CreateDate: 2021/3/25 14:46
 */
public class ModuleServiceUtils {

    public static final String IS_SWITCH_USER = "is_switch_user";
    public static final String ADDRESS = "address";
    public static final String TO_ADDRESS = "toAddress";
    public static final String POINTS = "points";
    public static final String POINTS_NAME = "points_name";


    public static final String HEIGHT ="height";

    public static final String HASH = "hash";

    /**
     * 跳转到钱包设置页面
     * @param address
     */
    public static void navigateWalletSettingPage(String address){
        ARouter.getInstance()
                .build(ModuleRouterTable.HOME_WALLET_SETTING)
                .withString(ADDRESS,address)
                .navigation();
    }


    public static final String IS_FIRST = "isFirst";
    /**
     * 跳转到创建钱包页面
     */
    public static void navigateFirstPage(boolean isFirst){
        Bundle args = new Bundle();
        args.putBoolean(IS_FIRST,isFirst);
        ARouter.getInstance()
                .build(ModuleRouterTable.CREATE_WALLET_PAGE)
                .with(args)
                .navigation();
    }
    /**
     * 跳转到首页
     */
    public static void navigateHomePage(){
        ARouter.getInstance()
                .build(ModuleRouterTable.HOME_INDEX_PAGE)
                .navigation();
    }
    /**
     * 跳转到发送积分界面
     */
    public static void navigateSendPointsPage(HomeDataResultBean.FtBean ftBean,String mCurrAddress){
        Bundle args = new Bundle();
        args.putString(ADDRESS,mCurrAddress);
        args.putParcelable(DATA,ftBean);
        ARouter.getInstance()
                .build(ModuleRouterTable.SEND_POINTS_PAGE)
                .with(args)
                .navigation();
    }
    public static void navigateSendPointsPage(HomeDataResultBean.FtBean ftBean,String mCurrAddress,String toAddress,String points){
        Bundle args = new Bundle();
        args.putString(ADDRESS,mCurrAddress);
        args.putString(TO_ADDRESS,toAddress);
        args.putString(POINTS,points);
        args.putParcelable(DATA,ftBean);
        ARouter.getInstance()
                .build(ModuleRouterTable.SEND_POINTS_PAGE)
                .with(args)
                .navigation();
    }
    /**
     * 跳转到接收积分界面
     */
    public static void navigateReceivePointsPage(){
        ARouter.getInstance()
                .build(ModuleRouterTable.RECEIVE_POINTS_PAGE)
                .navigation();
    }
    /**
     * 跳转到接收积分界面
     */
    public static void navigateReceivePointsPage(String pointsName){
        Bundle args = new Bundle();
        args.putString(POINTS_NAME,pointsName);
        ARouter.getInstance()
                .build(ModuleRouterTable.RECEIVE_POINTS_PAGE)
                .with(args)
                .navigation();
    }


    public static final String TX_ID = "tx_id";
    public static final String NFT_IMG = "nft_img";
    public static final String CONTRACT_ADDRESS = "contract_address";
    public static void navigateSendPropertyPage(String mCurrAddress, String txId,String contract_address,String nftImg){
        Bundle args = new Bundle();
        args.putString(ADDRESS,mCurrAddress);
        args.putString(TX_ID,txId);
        args.putString(CONTRACT_ADDRESS,contract_address);
        args.putString(NFT_IMG,nftImg);
        ARouter.getInstance()
                .build(ModuleRouterTable.SEND_PROPERTY_PAGE)
                .with(args)
                .navigation();
    }


    /**
     * 接收资产
     */
    public static void navigateReceivePropertyPage(){
        ARouter.getInstance()
                .build(ModuleRouterTable.RECEIVE_PROPERTY_PAGE)
                .navigation();
    }

    /**
     * 跳转到钱包管理界面
     */
    public static void navigateWalletManagePage(){
        ARouter.getInstance()
                .build(ModuleRouterTable.HOME_WALLET_MANAGE)
                .navigation();
    }

    public static final String FROM_INDEX = "from_index"; //首页路由
    public static final int REQ_QR_CODE = 11002; // // 打开扫描界面请求码
    public static final int RESULT_CODE_QR_SCAN = 0xA1;//扫一扫返回码
    public static final String INTENT_EXTRA_KEY_QR_SCAN = "qr_scan_result";
    public static final String INTENT_EXTRA_KEY_POINTS_SCAN = "points_scan_result";
    public static final String ADDRESS_TYPE = "address_type";//1表示存储地址，2表示转账


    public static final String IS_SELECT_ADDRESS = "is_select_address";
    public static final int REQ_SELECT_ADDRESS = 11003; // // 打开选择地址界面请求码
    public static final int RESULT_CODE_SELECT_ADDRESS = 0xA2;//选择地址返回码
    public static final String INTENT_EXTRA_KEY_SELECT_ADDRESS = "select_address_result";//选择地址返回数据

    public static final int REQ_SELECT_POINTS = 11004; // // 打开选择积分类型界面请求码
    public static final int RESULT_CODE_SELECT_POINTS = 0xA3;//选择积分类型返回码
    public static final String INTENT_EXTRA_KEY_SELECT_POINTS = "select_points_result";//选择积分类型返回数据


    public static final String NAME = "name";
    public static final String VALUE = "value";
    /**
     * 跳转到交易列表
     */
    public static void navigateTransactionPage(HomeDataResultBean.FtBean ftBean){
        Bundle args = new Bundle();
        args.putParcelable(DATA,ftBean);
        ARouter.getInstance()
                .build(ModuleRouterTable.HOME_TRANSACTION_PAGE)
                .with(args)
                .navigation();
    }


    public static final String DATA = "data";

    public static final String IS_HIDE = "is_hide";
    /**
     * 跳转到交易详情
     */
    public static void navigateTransactionDetailPage(Parcelable parcelable){
        Bundle args = new Bundle();
        args.putParcelable(DATA,parcelable);
        ARouter.getInstance()
                .build(ModuleRouterTable.TRANSACTION_DETAIL_PAGE)
                .with(args)
                .navigation();
    }


    public static final String IS_ADD = "is_add";
    /**
     * 跳转到交易详情
     */
    public static void navigateEditAddressPage(Parcelable parcelable){
        Bundle args = new Bundle();
        args.putParcelable(DATA,parcelable);
        ARouter.getInstance()
                .build(ModuleRouterTable.EDIT_ADDRESS_PAGE)
                .with(args)
                .navigation();
    }
    public static void navigateEditAddressPage(boolean isAdd){
        Bundle args = new Bundle();
        args.putBoolean(IS_ADD,isAdd);
        ARouter.getInstance()
                .build(ModuleRouterTable.EDIT_ADDRESS_PAGE)
                .with(args)
                .navigation();
    }
    public static void navigateEditAddressPage(boolean isAdd,String address){
        Bundle args = new Bundle();
        args.putBoolean(IS_ADD,isAdd);
        args.putString(ADDRESS,address);
        ARouter.getInstance()
                .build(ModuleRouterTable.EDIT_ADDRESS_PAGE)
                .with(args)
                .navigation();
    }


    public static void navigatePointPropertyPage(String address){
        Bundle args = new Bundle();;
        args.putString(ADDRESS,address);
        ARouter.getInstance()
                .build(ModuleRouterTable.POINTS_PROPERTY_PAGE)
                .with(args)
                .navigation();
    }

    /**
     * 移除钱包
     */
    public static void navigateRemoveWalletPage(String address){
        Bundle args = new Bundle();;
        args.putString(ADDRESS,address);
        ARouter.getInstance()
                .build(ModuleRouterTable.REMOVE_WALLET_PAGE)
                .with(args)
                .navigation();
    }


    /**
     * 实物资产列表
     */
    public static void navigatePhysicalListPage(HomeDataResultBean.NftBean nftBean,String currAddress){
        Bundle args = new Bundle();
        args.putParcelable(DATA,nftBean);
        args.putString(ADDRESS,currAddress);
        ARouter.getInstance()
                .build(ModuleRouterTable.PHYSICAL_LIST_PAGE)
                .with(args)
                .navigation();
    }

    /**
     * 实物资产详情
     */
    public static void navigatePhysicalDetailPage(long goodsId){
        Bundle args = new Bundle();
        args.putLong(DATA,goodsId);
        ARouter.getInstance()
                .build(ModuleRouterTable.PHYSICAL_DETAIL_PAGE)
                .with(args)
                .navigation();
    }

    /**
     * 实物资产详情
     */
    public static void navigatePhysicalDetailPage(long goodsId,boolean isHide){
        Bundle args = new Bundle();
        args.putLong(DATA,goodsId);
        args.putBoolean(IS_HIDE,isHide);
        ARouter.getInstance()
                .build(ModuleRouterTable.PHYSICAL_DETAIL_PAGE)
                .with(args)
                .navigation();
    }
    /**
     * 选择积分类型
     */
    public static void navigateSelectPointsTypePage(Activity activity,String address){
        Bundle args = new Bundle();
        args.putString(ADDRESS,address);
        ARouter.getInstance()
                .build(ModuleRouterTable.POINTS_TYPE_PAGE)
                .with(args)
                .navigation(activity,ModuleServiceUtils.REQ_SELECT_POINTS);
    }

    /**
     * 跳转积分兑换记录
     */
    public static void navigateSelectPointsTypePage(Parcelable parcelable){
        Bundle args = new Bundle();
        args.putParcelable(DATA,parcelable);
        ARouter.getInstance()
                .build(ModuleRouterTable.POINTS_EXCHANGE_RECORD)
                .with(args)
                .navigation();
    }
    /**
     * 跳转积分兑换记录
     */
    public static void navigateLoginPage(boolean isSwitchUser){
        Bundle args = new Bundle();
        args.putBoolean(IS_SWITCH_USER,isSwitchUser);
        ARouter.getInstance()
                .build(ModuleRouterTable.POINTS_LOGIN_PAGE)
                .with(args)
                .navigation();
    }
    /**
     * 跳转积分兑换记录
     */
    public static void navigatePhysicalRecordListPage(String address){
        Bundle args = new Bundle();
        args.putString(ADDRESS,address);
        ARouter.getInstance()
                .build(ModuleRouterTable.PHYSICAL_RECORD_LIST_PAGE)
                .with(args)
                .navigation();
    }



    /**
     * 跳转区块详情
     */
    public static void navigateBlockDetailPage(int height){
        Bundle args = new Bundle();
        args.putInt(HEIGHT,height);
        ARouter.getInstance()
                .build(ModuleRouterTable.BLOCK_DETAIL_PAGE)
                .with(args)
                .navigation();
    }


    /**
     * 跳转交易详情
     */
    public static void navigateTradeDetailPage(String hash){
        Bundle args = new Bundle();
        args.putString(HASH,hash);
        ARouter.getInstance()
                .build(ModuleRouterTable.TRADE_DETAIL_PAGE)
                .with(args)
                .navigation();
    }



    /**
     * 跳转地址详情
     */
    public static void navigateAddressDetailPage(String address){
        Bundle args = new Bundle();
        args.putString(ADDRESS,address);
        ARouter.getInstance()
                .build(ModuleRouterTable.ADDRESS_DETAIL_PAGE)
                .with(args)
                .navigation();
    }




    /**
     * 跳转地址积分更多
     */
    public static void navigateAddressDetailScoreMorePage(String address){
        Bundle args = new Bundle();
        args.putString(ADDRESS,address);
        ARouter.getInstance()
                .build(ModuleRouterTable.ADDRESS_DETAIL_SCORE_PAGE)
                .with(args)
                .navigation();
    }

    /**
     * 跳转地址NFT更多
     */
    public static void navigateAddressDetailNFTMorePage(String address){
        Bundle args = new Bundle();
        args.putString(ADDRESS,address);
        ARouter.getInstance()
                .build(ModuleRouterTable.ADDRESS_DETAIL_NFT_PAGE)
                .with(args)
                .navigation();
    }
}
