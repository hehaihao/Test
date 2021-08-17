package com.xm6leefun.block_browser.trade.mvp;

import com.xm6leefun.block_browser.api.BlockBrowserApiService;
import com.xm6leefun.block_browser.block.bean.BlockBrowserBlockBean;
import com.xm6leefun.block_browser.block.bean.BlockBrowserBlockListBean;
import com.xm6leefun.block_browser.trade.bean.BlockBrowserTradeBean;
import com.xm6leefun.block_browser.trade.bean.BlockBrowserTradeListBean;
import com.xm6leefun.block_browser.trade.bean.BlockBrowserTradeTypeBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * @Description:
 * @Author: cyh
 * @CreateDate: 2021/4/15
 */
public class BlockBrowserTradeListModelImp implements BlockBrowserTradeListContract.IModel{


    private BlockBrowserApiService apiService;

    public static final int LIMIT = 5;

    public static final int PAGE = 1;

    public BlockBrowserTradeListModelImp(BlockBrowserApiService apiService) {
        this.apiService = apiService;
    }


    @Override
    public Observable<?> getList(int type,long limit,long page) {
        return  apiService.getBlockBrowserTradeList(type,limit,page);

    }

    @Override
    public Observable<?> getTypes() {
        return Observable.create((ObservableOnSubscribe<List<BlockBrowserTradeTypeBean>>) emitter -> {

            List<BlockBrowserTradeTypeBean> listBeans = new ArrayList<>();

            BlockBrowserTradeTypeBean bean1 = new BlockBrowserTradeTypeBean(0,"全部",true);
            BlockBrowserTradeTypeBean bean2 = new BlockBrowserTradeTypeBean(1,"积分兑换",false);
            BlockBrowserTradeTypeBean bean3 = new BlockBrowserTradeTypeBean(2,"NFT资产",false);

            listBeans.add(bean1);
            listBeans.add(bean2);
            listBeans.add(bean3);

            emitter.onNext(listBeans);
            emitter.onComplete();
        });
    }

    @Override
    public Observable<?> search(String search) {
        return apiService.getBlockBrowserSearchDetail(search,LIMIT,PAGE);
    }
}
