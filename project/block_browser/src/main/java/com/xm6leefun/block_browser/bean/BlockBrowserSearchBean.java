package com.xm6leefun.block_browser.bean;




import com.xm6leefun.common.base.PageInfoBean;

import java.util.List;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/5/6
 */
public class BlockBrowserSearchBean {


    /**
     * 区块高度信息
     * blockHeightInfo : {"blockHeight":1,"headHash":"a7830a7bf347353cbfe7ae9a3d1469ec30ac9004dccd6ae4a8a5ff9844c8edef","merkleRoot":"474c042d58295ee33c106c3fe3e9cb79cf7ec2d7fd716a9928884f4a68569615","version":1,"tradingNum":1,"tradingTotal":"0","tradingSize":1315,"tradingCoin":"only","tradingCost":"0","createdTime":"2021-04-11 01:22:56","nextHeight":2,"prevHeight":0}
     */
    private BlockHeightInfoBean blockHeightInfo;
    public static class BlockHeightInfoBean {
        /**
         * blockHeight : 1
         * headHash : a7830a7bf347353cbfe7ae9a3d1469ec30ac9004dccd6ae4a8a5ff9844c8edef
         * merkleRoot : 474c042d58295ee33c106c3fe3e9cb79cf7ec2d7fd716a9928884f4a68569615
         * version : 1
         * tradingNum : 1
         * tradingTotal : 0
         * tradingSize : 1315
         * tradingCoin : only
         * tradingCost : 0
         * createdTime : 2021-04-11 01:22:56
         * nextHeight : 2
         * prevHeight : 0
         */

        private int blockHeight;
        private String headHash;
        private String merkleRoot;
        private int version;
        private int tradingNum;
        private String tradingTotal;
        private int tradingSize;
        private String tradingCoin;
        private String tradingCost;
        private String createdTime;
        private int nextHeight;
        private int prevHeight;

        public int getBlockHeight() {
            return blockHeight;
        }

        public void setBlockHeight(int blockHeight) {
            this.blockHeight = blockHeight;
        }

        public String getHeadHash() {
            return headHash;
        }

        public void setHeadHash(String headHash) {
            this.headHash = headHash;
        }

        public String getMerkleRoot() {
            return merkleRoot;
        }

        public void setMerkleRoot(String merkleRoot) {
            this.merkleRoot = merkleRoot;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public int getTradingNum() {
            return tradingNum;
        }

        public void setTradingNum(int tradingNum) {
            this.tradingNum = tradingNum;
        }

        public String getTradingTotal() {
            return tradingTotal;
        }

        public void setTradingTotal(String tradingTotal) {
            this.tradingTotal = tradingTotal;
        }

        public int getTradingSize() {
            return tradingSize;
        }

        public void setTradingSize(int tradingSize) {
            this.tradingSize = tradingSize;
        }

        public String getTradingCoin() {
            return tradingCoin;
        }

        public void setTradingCoin(String tradingCoin) {
            this.tradingCoin = tradingCoin;
        }

        public String getTradingCost() {
            return tradingCost;
        }

        public void setTradingCost(String tradingCost) {
            this.tradingCost = tradingCost;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public int getNextHeight() {
            return nextHeight;
        }

        public void setNextHeight(int nextHeight) {
            this.nextHeight = nextHeight;
        }

        public int getPrevHeight() {
            return prevHeight;
        }

        public void setPrevHeight(int prevHeight) {
            this.prevHeight = prevHeight;
        }
    }
    public BlockHeightInfoBean getBlockHeightInfo() {
        return blockHeightInfo;
    }
    public void setBlockHeightInfo(BlockHeightInfoBean blockHeightInfo) {
        this.blockHeightInfo = blockHeightInfo;
    }




    /**
     * 交易详细
     * actionHashInfo : {"actionId":"608535b7d564563a8b73e27a","action":"474c042d58295ee33c106c3fe3e9cb79cf7ec2d7fd716a9928884f4a68569615","blockHeight":1,"actionType":1,"actionName":"普通交易","actionState":1,"coin":"ONLY","cost":"0","actionTotal":"0","createdTime":"2021-03-23 14:51:00","sendAddress":"oc89cafe4937db20221c8ccd1ed1573638a27f661e","receiveAddressList":[{"receiveAddress":"oc89cafe4937db20221c8ccd1ed1573638a27f661e","value":"300000"}]}
     */
    private ActionHashInfoBean actionHashInfo;
    public static class ActionHashInfoBean {
        /**
         * actionId : 608535b7d564563a8b73e27a
         * action : 474c042d58295ee33c106c3fe3e9cb79cf7ec2d7fd716a9928884f4a68569615
         * blockHeight : 1
         * actionType : 1
         * actionName : 普通交易
         * actionState : 1
         * coin : ONLY
         * cost : 0
         * actionTotal : 0
         * createdTime : 2021-03-23 14:51:00
         * sendAddress : oc89cafe4937db20221c8ccd1ed1573638a27f661e
         * receiveAddressList : [{"receiveAddress":"oc89cafe4937db20221c8ccd1ed1573638a27f661e","value":"300000"}]
         */

        private String actionId;
        private String action;
        private int blockHeight;
        private int actionType;
        private String actionName;
        private int actionState;
        private String coin;
        private String cost;
        private String actionTotal;
        private String createdTime;
        private String sendAddress;
        private List<ReceiveAddressListBean> receiveAddressList;

        public String getActionId() {
            return actionId;
        }

        public void setActionId(String actionId) {
            this.actionId = actionId;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public int getBlockHeight() {
            return blockHeight;
        }

        public void setBlockHeight(int blockHeight) {
            this.blockHeight = blockHeight;
        }

        public int getActionType() {
            return actionType;
        }

        public void setActionType(int actionType) {
            this.actionType = actionType;
        }

        public String getActionName() {
            return actionName;
        }

        public void setActionName(String actionName) {
            this.actionName = actionName;
        }

        public int getActionState() {
            return actionState;
        }

        public void setActionState(int actionState) {
            this.actionState = actionState;
        }

        public String getCoin() {
            return coin;
        }

        public void setCoin(String coin) {
            this.coin = coin;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getActionTotal() {
            return actionTotal;
        }

        public void setActionTotal(String actionTotal) {
            this.actionTotal = actionTotal;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public String getSendAddress() {
            return sendAddress;
        }

        public void setSendAddress(String sendAddress) {
            this.sendAddress = sendAddress;
        }

        public List<ReceiveAddressListBean> getReceiveAddressList() {
            return receiveAddressList;
        }

        public void setReceiveAddressList(List<ReceiveAddressListBean> receiveAddressList) {
            this.receiveAddressList = receiveAddressList;
        }

        public static class ReceiveAddressListBean {
            /**
             * receiveAddress : oc89cafe4937db20221c8ccd1ed1573638a27f661e
             * value : 300000
             */

            private String receiveAddress;
            private String value;

            public String getReceiveAddress() {
                return receiveAddress;
            }

            public void setReceiveAddress(String receiveAddress) {
                this.receiveAddress = receiveAddress;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
    public ActionHashInfoBean getActionHashInfo() {
        return actionHashInfo;
    }
    public void setActionHashInfo(ActionHashInfoBean actionHashInfo) {
        this.actionHashInfo = actionHashInfo;
    }




    /**
     * 地址信息
     * blockAddressInfo : {"address":"oc89cafe4937db20221c8ccd1ed1573638a27f661e","assetsList":[{"subChainName":"子链币","contractAddress":"","subChainCoin":"300000"}]}
     */
    private BlockAddressInfoBean blockAddressInfo;
    public static class BlockAddressInfoBean {
        /**
         * address : oc89cafe4937db20221c8ccd1ed1573638a27f661e
         * assetsList : [{"subChainName":"子链币","contractAddress":"","subChainCoin":"300000"}]
         */

        private String address;
        private List<AssetsListBean> assetsList;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public List<AssetsListBean> getAssetsList() {
            return assetsList;
        }

        public void setAssetsList(List<AssetsListBean> assetsList) {
            this.assetsList = assetsList;
        }

        public static class AssetsListBean {
            /**
             * subChainName : 子链币
             * contractAddress :
             * subChainCoin : 300000
             */

            private String subChainName;
            private String contractAddress;
            private String subChainCoin;

            public String getSubChainName() {
                return subChainName;
            }

            public void setSubChainName(String subChainName) {
                this.subChainName = subChainName;
            }

            public String getContractAddress() {
                return contractAddress;
            }

            public void setContractAddress(String contractAddress) {
                this.contractAddress = contractAddress;
            }

            public String getSubChainCoin() {
                return subChainCoin;
            }

            public void setSubChainCoin(String subChainCoin) {
                this.subChainCoin = subChainCoin;
            }
        }
    }
    public BlockAddressInfoBean getBlockAddressInfo() {
        return blockAddressInfo;
    }
    public void setBlockAddressInfo(BlockAddressInfoBean blockAddressInfo) {
        this.blockAddressInfo = blockAddressInfo;
    }



    /**
     *  "nftInfo": {}
     */
    public static class NftInfoBean {

        /**
         * id : 51
         * txid : 51296da3e0637eab29bb83e35921fd3a09b63306e6636e66ab409c0e6d8bb3da
         * createTime :
         * nftName : 苹果2
         * nftImg : ["https://weecot.oss-cn-shenzhen.aliyuncs.com/nft/goods/3565346538303161393362633833613033366662386662613066643332616436.jpg"]
         * nftToken : f122cf3cba5e34cce4a04c1a24cf746550a27e2eed50dadeda64a31f128d29e0
         * nftIntroduce : null
         * contractAddress : 9a627a9cbec6f4c25a61c614db7a1a149eed9271
         * originatorAddress : 0accfe6be06d2d2ba49d727ac43ed458fe0d325a
         * reAddress :
         */

        private long id;
        private String txid;
        private String createTime;
        private String nftName;
        private String nftToken;
        private String nftIntroduce;
        private String contractAddress;
        private String originatorAddress;
        private String reAddress;
        private List<String> nftImg;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getTxid() {
            return txid;
        }

        public void setTxid(String txid) {
            this.txid = txid;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getNftName() {
            return nftName;
        }

        public void setNftName(String nftName) {
            this.nftName = nftName;
        }

        public String getNftToken() {
            return nftToken;
        }

        public void setNftToken(String nftToken) {
            this.nftToken = nftToken;
        }

        public String getNftIntroduce() {
            return nftIntroduce;
        }

        public void setNftIntroduce(String nftIntroduce) {
            this.nftIntroduce = nftIntroduce;
        }

        public String getContractAddress() {
            return contractAddress;
        }

        public void setContractAddress(String contractAddress) {
            this.contractAddress = contractAddress;
        }

        public String getOriginatorAddress() {
            return originatorAddress;
        }

        public void setOriginatorAddress(String originatorAddress) {
            this.originatorAddress = originatorAddress;
        }

        public String getReAddress() {
            return reAddress;
        }

        public void setReAddress(String reAddress) {
            this.reAddress = reAddress;
        }

        public List<String> getNftImg() {
            return nftImg;
        }

        public void setNftImg(List<String> nftImg) {
            this.nftImg = nftImg;
        }
    }
    private NftInfoBean nftInfo;
    public NftInfoBean getNftInfo() {
        return nftInfo;
    }
    public void setNftInfo(NftInfoBean nftInfo) {
        this.nftInfo = nftInfo;
    }





    /**
     *  地址详情和区块详情都在此字段下
     *  为地址详情时 blockAddressList
     *  为区块详情时 blockActionList
     */
    private BlockActionInfoBean blockActionInfo;
    public static class BlockActionInfoBean {

        /**
         * 地址列表
         */
        private List<BlockAddressListBean> blockAddressList;
        public static class BlockAddressListBean {
            /**
             * actionId : 608535b79eec25613b40ce22
             * action : 474c042d58295ee33c106c3fe3e9cb79cf7ec2d7fd716a9928884f4a68569615
             * mainAction :
             * contractAddress :
             * token :
             * actionType : 2
             * blockHeight : 1
             * blockCoin : only
             * blockLockTime : 0
             * createdTime : 2021-03-23 14:51:00
             * sendAddress : 89cafe4937db20221c8ccd1ed1573638a27f661e
             * actionTotal : 0
             * receiveList : {"haveInclude":[],"notInclude":[{"receiveAddress":"89cafe4937db20221c8ccd1ed1573638a27f661e","receiveValue":30000000000000}]}
             * nftInfo : {}
             */

            private String actionId;
            private String action;
            private String mainAction;
            private String contractAddress;
            private String token;
            private int actionType;
            private int blockHeight;
            private String blockCoin;
            private int blockLockTime;
            private String createdTime;
            private String sendAddress;
            private int actionTotal;
            private ReceiveListBean receiveList;

            private NftInfoBean nftInfo;

            public NftInfoBean getNftInfo() {
                return nftInfo;
            }

            public void setNftInfo(NftInfoBean nftInfo) {
                this.nftInfo = nftInfo;
            }

            public String getActionId() {
                return actionId;
            }

            public void setActionId(String actionId) {
                this.actionId = actionId;
            }

            public String getAction() {
                return action;
            }

            public void setAction(String action) {
                this.action = action;
            }

            public String getMainAction() {
                return mainAction;
            }

            public void setMainAction(String mainAction) {
                this.mainAction = mainAction;
            }

            public String getContractAddress() {
                return contractAddress;
            }

            public void setContractAddress(String contractAddress) {
                this.contractAddress = contractAddress;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public int getActionType() {
                return actionType;
            }

            public void setActionType(int actionType) {
                this.actionType = actionType;
            }

            public int getBlockHeight() {
                return blockHeight;
            }

            public void setBlockHeight(int blockHeight) {
                this.blockHeight = blockHeight;
            }

            public String getBlockCoin() {
                return blockCoin;
            }

            public void setBlockCoin(String blockCoin) {
                this.blockCoin = blockCoin;
            }

            public int getBlockLockTime() {
                return blockLockTime;
            }

            public void setBlockLockTime(int blockLockTime) {
                this.blockLockTime = blockLockTime;
            }

            public String getCreatedTime() {
                return createdTime;
            }

            public void setCreatedTime(String createdTime) {
                this.createdTime = createdTime;
            }

            public String getSendAddress() {
                return sendAddress;
            }

            public void setSendAddress(String sendAddress) {
                this.sendAddress = sendAddress;
            }

            public int getActionTotal() {
                return actionTotal;
            }

            public void setActionTotal(int actionTotal) {
                this.actionTotal = actionTotal;
            }

            public ReceiveListBean getReceiveList() {
                return receiveList;
            }

            public void setReceiveList(ReceiveListBean receiveList) {
                this.receiveList = receiveList;
            }



            public static class ReceiveListBean {

                private List<NotIncludeBean> notInclude;

                private List<HaveIncludeBean> haveInclude;

                public static class HaveIncludeBean {

                }

                public static class NotIncludeBean {
                    private String receiveAddress;
                    private long receiveValue;

                    public String getReceiveAddress() {
                        return receiveAddress;
                    }

                    public void setReceiveAddress(String receiveAddress) {
                        this.receiveAddress = receiveAddress;
                    }

                    public long getReceiveValue() {
                        return receiveValue;
                    }

                    public void setReceiveValue(long receiveValue) {
                        this.receiveValue = receiveValue;
                    }
                }
            }

        }

        public List<BlockAddressListBean> getBlockAddressList() {
            return blockAddressList;
        }
        public void setBlockAddressList(List<BlockAddressListBean> blockAddressList) {
            this.blockAddressList = blockAddressList;
        }

        /**
         * 地址分页信息
         */
        private PageInfoBean blockAddressPage;
        public PageInfoBean getBlockAddressPage() {
            return blockAddressPage;
        }
        public void setBlockAddressPage(PageInfoBean blockAddressPage) {
            this.blockAddressPage = blockAddressPage;
        }

        /**
         * 区块列表
         */
        private List<BlockActionListBean> blockActionList;
        public static class BlockActionListBean {
            /**
             * actionId : 608535b7d564563a8b73e27a
             * action : 474c042d58295ee33c106c3fe3e9cb79cf7ec2d7fd716a9928884f4a68569615
             * actionCoin : ONLY
             * actionTotal : 0
             * actionType : 1
             * actionName : 普通交易
             * createdTime : 2021-03-23 14:51:00
             * cost : 0
             * sendAddress : oc89cafe4937db20221c8ccd1ed1573638a27f661e
             * receiveAddress : ococ89cafe4937db20221c8ccd1ed1573638a27f661e
             */

            private String actionId;
            private String action;
            private String actionCoin;
            private String actionTotal;
            private int actionType;
            private String actionName;
            private String createdTime;
            private int cost;
            private String sendAddress;
            private String receiveAddress;

            public String getActionId() {
                return actionId;
            }

            public void setActionId(String actionId) {
                this.actionId = actionId;
            }

            public String getAction() {
                return action;
            }

            public void setAction(String action) {
                this.action = action;
            }

            public String getActionCoin() {
                return actionCoin;
            }

            public void setActionCoin(String actionCoin) {
                this.actionCoin = actionCoin;
            }

            public String getActionTotal() {
                return actionTotal;
            }

            public void setActionTotal(String actionTotal) {
                this.actionTotal = actionTotal;
            }

            public int getActionType() {
                return actionType;
            }

            public void setActionType(int actionType) {
                this.actionType = actionType;
            }

            public String getActionName() {
                return actionName;
            }

            public void setActionName(String actionName) {
                this.actionName = actionName;
            }

            public String getCreatedTime() {
                return createdTime;
            }

            public void setCreatedTime(String createdTime) {
                this.createdTime = createdTime;
            }

            public int getCost() {
                return cost;
            }

            public void setCost(int cost) {
                this.cost = cost;
            }

            public String getSendAddress() {
                return sendAddress;
            }

            public void setSendAddress(String sendAddress) {
                this.sendAddress = sendAddress;
            }

            public String getReceiveAddress() {
                return receiveAddress;
            }

            public void setReceiveAddress(String receiveAddress) {
                this.receiveAddress = receiveAddress;
            }
        }
        public List<BlockActionListBean> getBlockActionList() {
            return blockActionList;
        }
        public void setBlockActionList(List<BlockActionListBean> blockActionList) {
            this.blockActionList = blockActionList;
        }

        /**
         * 区块分页信息
         */
        private PageInfoBean blockActionPage;
        public PageInfoBean getBlockActionPage() {
            return blockActionPage;
        }
        public void setBlockActionPage(PageInfoBean blockActionPage) {
            this.blockActionPage = blockActionPage;
        }


    }
    public BlockActionInfoBean getBlockActionInfo() {
        return blockActionInfo;
    }
    public void setBlockActionInfo(BlockActionInfoBean blockActionInfo) {
        this.blockActionInfo = blockActionInfo;
    }



    private int searchType;
    private int DetailType;

    public int getSearchType() {
        return searchType;
    }
    public void setSearchType(int searchType) {
        this.searchType = searchType;
    }
    public int getDetailType() {
        return DetailType;
    }

    public void setDetailType(int DetailType) {
        this.DetailType = DetailType;
    }

}
