package com.xm6leefun.common.utils;

/**
 * 文件描述：常量d
 */
public class ConstantValue {

    // 主网钱包私钥上传前进行AES加密的key
    public static final String PRIVATE_KEY_AES_PWD = "xm6leefun@mding_888";

    public static final String QR_CODE_HEAD = "poketok=";
    public static final String QR_CODE_WALLET_OC_FLAG = "qrCodeWalletOC//";  // 含有该字段表明是主网钱包的二维码
    public static final String QR_CODE_PRIVATE_KEY_FLAG = "privateKey=";  // 二维码私钥连接字段
    public static final String QR_CODE_KEYSTORE_FLAG = "keystore=";  // 二维码keystore连接字段
    public static final String QR_CODE_ADDRESS_FLAG = "address=";  // 二维码地址连接字段
    public static final String QR_CODE_VALUE_FLAG = "value=";  // 二维码数目连接字段

    public static final String CURRENT_NODE = "current_node";  //当前选中的节点key
}
