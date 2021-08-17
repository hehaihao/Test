package com.xm6leefun.physical_module.physical_detail.bean;

import java.util.List;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/13
 */
public class PhysicalDetailBean {


    /**
     * nftInfo : {"nft_img":"https://www.baidu.com/img/flexible/logo/pc/result.png","nft_name":"三星堆文明","nft_details":"品种:面具,颜色:金色,材质:黄金,重量:1KG","nft_txid":"039a27e604966a5a9aa3241779df52632bab78473e04ab625431697482b09278","contract_address":"9a627a9cbec6f4c25a61c614db7a1a149eed9272","owner_address":"5d28665b60e17cf2ffd0525e9e87933218ed5a48"}
     * transfer : [{"originator_address":"5d28665b60e17cf2ffd0525e9e87933218ed5a48","re_address":"37b6c917185f776a4b79607f292b229ece2d55ec","type":2,"create_time":"2021-04-13 04:12:36"},{"originator_address":"5d28665b60e17cf2ffd0525e9e87933218ed5a48","re_address":"37b6c917185f776a4b79607f292b229ece2d55ec","type":2,"create_time":"2021-04-12 17:05:56"}]
     */

    private NftInfoBean nftInfo;
    private List<TransferBean> transfer;

    public NftInfoBean getNftInfo() {
        return nftInfo;
    }

    public void setNftInfo(NftInfoBean nftInfo) {
        this.nftInfo = nftInfo;
    }

    public List<TransferBean> getTransfer() {
        return transfer;
    }

    public void setTransfer(List<TransferBean> transfer) {
        this.transfer = transfer;
    }

    public static class NftInfoBean {
        /**
         * nft_img : https://www.baidu.com/img/flexible/logo/pc/result.png
         * nft_name : 三星堆文明
         * nft_details : 品种:面具,颜色:金色,材质:黄金,重量:1KG
         * nft_txid : 039a27e604966a5a9aa3241779df52632bab78473e04ab625431697482b09278
         * contract_address : 9a627a9cbec6f4c25a61c614db7a1a149eed9272
         * owner_address : 5d28665b60e17cf2ffd0525e9e87933218ed5a48
         */

        private List<String> nft_img;
        private String nft_name;
        private List<String> nft_details;
        private String nft_txid;
        private String contract_address;
        private String owner_address;
        private String nft_introduce;
        private String nft_certificate;

        public List<String> getNft_img() {
            return nft_img;
        }

        public void setNft_img(List<String> nft_img) {
            this.nft_img = nft_img;
        }

        public String getNft_name() {
            return nft_name;
        }

        public void setNft_name(String nft_name) {
            this.nft_name = nft_name;
        }

        public List<String> getNft_details() {
            return nft_details;
        }

        public void setNft_details(List<String> nft_details) {
            this.nft_details = nft_details;
        }

        public String getNft_txid() {
            return nft_txid;
        }

        public void setNft_txid(String nft_txid) {
            this.nft_txid = nft_txid;
        }

        public String getContract_address() {
            return contract_address;
        }

        public void setContract_address(String contract_address) {
            this.contract_address = contract_address;
        }

        public String getOwner_address() {
            return owner_address;
        }

        public void setOwner_address(String owner_address) {
            this.owner_address = owner_address;
        }

        public String getNft_introduce() {
            return nft_introduce;
        }

        public void setNft_introduce(String nft_introduce) {
            this.nft_introduce = nft_introduce;
        }

        public String getNft_certificate() {
            return nft_certificate;
        }

        public void setNft_certificate(String nft_certificate) {
            this.nft_certificate = nft_certificate;
        }
    }

    public static class TransferBean {
        /**
         * originator_address : 5d28665b60e17cf2ffd0525e9e87933218ed5a48
         * re_address : 37b6c917185f776a4b79607f292b229ece2d55ec
         * type : 2
         * create_time : 2021-04-13 04:12:36
         */

        private String originator_address;
        private String re_address;
        private int type;
        private int status;
        private String create_time;
        private String typeName;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getOriginator_address() {
            return originator_address;
        }

        public void setOriginator_address(String originator_address) {
            this.originator_address = originator_address;
        }

        public String getRe_address() {
            return re_address;
        }

        public void setRe_address(String re_address) {
            this.re_address = re_address;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
