package com.xm6leefun.common.db.bean;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * 项目：DC
 * 文件描述：主钱包表
 * 作者：ljj
 * 创建时间：2020/9/16
 */
public class Wallet_Main extends RealmObject {

    /** 钱包地址 */
    @PrimaryKey
    private String address;
    /** 是否备份；0未备份，1已备份；默认0未备份 */
    private int isBackup;
    /** 钱包地址 */
    private String walletName;
    /** keyStore */
    private String keystore;
    /** 助记词列表Json */
    private String wordsJson;
    /** 明文密码 */
    private String plainPwd;
    /** 密码的备注信息 */
    private String promptInfo;
    /** 拥有的总资产 */
    private Long totalAssets;
    /** 是否被删除状态；0否  1是；默认0否 */
    private int deletedStatus;
    /** 是否被选中；默认否 */
    private boolean isChecked;
    /** 创建时间 */
    private Long created;
    /** 修改时间 */
    private Long modified;
    /** 预留字段 */
    private String others;
    /** 是否开通免密支付；默认否 */
    private boolean isQuickPay;
    /** 是否为身份钱包*/
    private boolean isIdWallet;
    /** 首页积分资产列表*/
    private RealmList<Points_Assets> points_assets;

    public boolean isIdWallet() {
        return isIdWallet;
    }

    public void setIdWallet(boolean idWallet) {
        isIdWallet = idWallet;
    }

    public boolean isQuickPay() {
        return isQuickPay;
    }

    public void setQuickPay(boolean quickPay) {
        isQuickPay = quickPay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIsBackup() {
        return isBackup;
    }

    public void setIsBackup(int isBackup) {
        this.isBackup = isBackup;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public String getKeystore() {
        return keystore;
    }

    public void setKeystore(String keystore) {
        this.keystore = keystore;
    }

    public String getWordsJson() {
        return wordsJson;
    }

    public void setWordsJson(String wordsJson) {
        this.wordsJson = wordsJson;
    }

    public String getPlainPwd() {
        return plainPwd;
    }

    public void setPlainPwd(String plainPwd) {
        this.plainPwd = plainPwd;
    }

    public String getPromptInfo() {
        return promptInfo;
    }

    public void setPromptInfo(String promptInfo) {
        this.promptInfo = promptInfo;
    }

    public Long getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(Long totalAssets) {
        this.totalAssets = totalAssets;
    }

    public int getDeletedStatus() {
        return deletedStatus;
    }

    public void setDeletedStatus(int deletedStatus) {
        this.deletedStatus = deletedStatus;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getModified() {
        return modified;
    }

    public void setModified(Long modified) {
        this.modified = modified;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public RealmList<Points_Assets> getPoints_assets() {
        return points_assets;
    }

    public void setPoints_assets(RealmList<Points_Assets> points_assets) {
        this.points_assets = points_assets;
    }
}
