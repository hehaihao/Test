package com.xm6leefun.export_module.router;

/**
 * @Description:
 * 组件路由表
 * 即所有可以从外部跳转的页面的路由信息
 * @Author: hhh
 * @CreateDate: 2021/3/25 14:52
 */
public interface ModuleRouterTable {
    /**
     * 首页
     */
   String HOME_INDEX_PAGE = "/home/HomeActivity";
    /**
     * 首页钱包fragment
     */
   String HOME_WALLET_FRAGMENT = "/wallet/HomeWalletFragment";
    /**
     * 首页实物资产fragment
     */
   String HOME_PHYSICAL_FRAGMENT = "/physical/PhysicalFragment";

    /**
     * 我的
     */
   String HOME_ME_FRAGMENT = "/setting/MeFragment";
    /**
     * 首页钱包管理
     */
   String HOME_WALLET_MANAGE = "/wallet/WalletManageActivity";
    /**
     * 首页钱包设置
     */
   String HOME_WALLET_SETTING = "/wallet/WalletSettingActivity";
    /**
     * 首次进入钱包
     */
   String WALLET_FIRST_PAGE = "/wallet/FirstEntryActivity";

    /**
     * 积分模块发送积分界面
     */
   String SEND_POINTS_PAGE = "/points/SendPointsActivity";
    /**
     * 积分模块接收积分界面
     */
   String RECEIVE_POINTS_PAGE = "/points/ReceiveQrCodeActivity";
    /**
     * 扫一扫
     */
   String SCAN_PAGE = "/scan/ScanActivity";

    /**
     * 地址列表
     */
   String ADDRESS_LIST_PAGE = "/setting/AddressListActivity";
    /**
     * 交易列表
     */
   String HOME_TRANSACTION_PAGE = "/points/TransactionActivity";
    /**
     * 交易详情
     */
   String TRANSACTION_DETAIL_PAGE = "/points/TransactionDetailActivity";
    /**
     * 编辑和添加地址
     */
   String EDIT_ADDRESS_PAGE = "/setting/AddressEditActivity";
    /**
     * 首页积分授权界面
     */
   String HOME_POINTS_EXCHANGE_AUTH_FRAGMENT = "/points/PointsExchangeAuthFragment";
    /**
     * 首页积分兑换界面
     */
   String HOME_POINTS_EXCHANGE_FRAGMENT = "/points/PointsExchangeFragment";

    /**
     * 积分兑换记录
     */
    String POINTS_EXCHANGE_RECORD = "/points/PointsExchangeRecordActivity";
    /**
     * 系统设置
     */
    String SYSTEM_SETTING = "/syssetting/SystemSettingActivity";
    /**
     * 节点设置
     */
    String NODE_SETTING = "/nodesetting/NodeSettingActivity";
    /**
     * 区块浏览器
     */
    String BLOCK_BROWSER_PAGE = "/blockBrowser/BlockBrowserHomeActivity";

    /**
     * 添加积分资产首页
     */
    String POINTS_PROPERTY_PAGE= "/wallet/AddPropertyActivity";

    /**
     * 发送资产
     */
    String SEND_PROPERTY_PAGE= "/physical/SendPropertyActivity";

    /**
     * 新建钱包
     */
    String CREATE_WALLET_PAGE = "/wallet/CreateWalletActivity";
    /**
     * 移除钱包
     */
    String REMOVE_WALLET_PAGE = "/wallet/RemoveWalletActivity";

    /**
     * 积分模块接收积分界面
     */
    String RECEIVE_PROPERTY_PAGE = "/physical/ReceivePropertyActivity";

    /**
     * 实物资产列表
     */
    String PHYSICAL_LIST_PAGE = "/physical/PhysicalListActivity";
    /**
     * 实物资产详情
     */
    String PHYSICAL_DETAIL_PAGE = "/physical/PhysicalDetailActivity";
    /**
     * 实物资产转移记录
     */
    String PHYSICAL_RECORD_LIST_PAGE = "/physical/RecordListActivity";
    /**
     * 选择积分类型
     */
    String POINTS_TYPE_PAGE = "/points/PointsTypeActivity";
    /**
     * 真唯度登录
     */
    String POINTS_LOGIN_PAGE = "/points/LoginActivity";


    /**
     * 区块详情
     */
    String BLOCK_DETAIL_PAGE = "/blockBrowser/BlockBrowserBlockDetailActivity";


    /**
     * 交易详情
     */
    String TRADE_DETAIL_PAGE = "/blockBrowser/BlockBrowserTradeDetailActivity";


    /**
     * 地址详情
     */
    String ADDRESS_DETAIL_PAGE = "/blockBrowser/BlockBrowserAddressDetailActivity";


    /**
     * 地址详情积分更多
     */
    String ADDRESS_DETAIL_SCORE_PAGE = "/blockBrowser/BlockBrowserScoreListActivity";



    /**
     * 地址详情更多
     */
    String ADDRESS_DETAIL_NFT_PAGE = "/blockBrowser/BlockBrowserPropertyListActivity";
}
