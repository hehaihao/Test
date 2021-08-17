package com.xm6leefun.wallet_module.home_wallet.mvp;

/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2021/4/23 9:24
 */
public class PhysicalListBean {
    private long id;
    private String nft_img;
    private String nft_name;
    private String nft_details;
    private String contract_address;
    private String nft_txid;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNft_img() {
        return nft_img;
    }

    public void setNft_img(String nft_img) {
        this.nft_img = nft_img;
    }

    public String getNft_name() {
        return nft_name;
    }

    public void setNft_name(String nft_name) {
        this.nft_name = nft_name;
    }

    public String getNft_details() {
        return nft_details;
    }

    public void setNft_details(String nft_details) {
        this.nft_details = nft_details;
    }

    public String getContract_address() {
        return contract_address;
    }

    public void setContract_address(String contract_address) {
        this.contract_address = contract_address;
    }

    public String getNft_txid() {
        return nft_txid;
    }

    public void setNft_txid(String nft_txid) {
        this.nft_txid = nft_txid;
    }
}
