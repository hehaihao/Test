package com.xm6leefun.wallet_module.words_confirm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xm6leefun.common.app.activity.BaseToolbarPresenterActivity;
import com.xm6leefun.common.refresh_view.itemdecora.ItemGridDecoration;
import com.xm6leefun.common.refresh_view.itemdecora.SpacesItemDecoration;
import com.xm6leefun.common.utils.ActivityUtil;
import com.xm6leefun.common.utils.DoubleClickUtils;
import com.xm6leefun.common.utils.LogUtil;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.common.widget.toast.ToastUtil;
import com.xm6leefun.common.utils.status_bar.StatusBarUtil;
import com.xm6leefun.export_module.busbean.RefreshWalletDataBusBean;
import com.xm6leefun.export_module.router.ModuleRouterTable;
import com.xm6leefun.wallet_module.R;
import com.xm6leefun.wallet_module.R2;
import com.xm6leefun.wallet_module.backup_tips.mvp.CusCreateWalletBean;
import com.xm6leefun.common.wallet.KeysInfo;
import com.xm6leefun.wallet_module.words_confirm.adapter.MnemonicChosenAdapter;
import com.xm6leefun.wallet_module.words_confirm.mvp.WordsConfirmContract;
import com.xm6leefun.wallet_module.words_confirm.mvp.WordsConfirmPresenter;
import com.xm6leefun.wallet_module.words_make.adapter.MnemonicAdapter;
import com.xm6leefun.wallet_module.words_make.bean.CusMnemonic;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Description: 确认助记词
 * @Author: hhh
 * @CreateDate: 2021/3/26 16:58
 */
public class WordsConfirmActivity extends BaseToolbarPresenterActivity<WordsConfirmPresenter> implements WordsConfirmContract.IView {
    public static final String CUS_DATA = "cus_data";

    @BindView(R2.id.words_confirm_rv)
    RecyclerView mRecyclerView;
    @BindView(R2.id.words_confirm_rv_random)
    RecyclerView mRecyclerViewRandom;

    private KeysInfo keysInfo;
    private String pwd;
    private MnemonicAdapter mMnemonicAdapter;
    private MnemonicChosenAdapter mMnemonicAdapterRandom;
    private CusCreateWalletBean cusCreateWallet = new CusCreateWalletBean();
    private StringBuilder stringBuilder = new StringBuilder();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_words_confirm_layout;
    }

    @Override
    protected void initView() {
        topBarTvTitle.setText(R.string.words_confirm_title);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        addOnClickListeners(R.id.words_confirm_tv_confirm);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            cusCreateWallet = bundle.getParcelable(CUS_DATA);
            dealWalletData(cusCreateWallet);
        }
    }

    /**
     * 处理数据
     * @param cusCreateWallet
     */
    private void dealWalletData(CusCreateWalletBean cusCreateWallet) {
        if (cusCreateWallet != null){
            keysInfo = cusCreateWallet.getKeysInfo();
            pwd = cusCreateWallet.getPwd();
            if (keysInfo.getWords() != null) {
                initWords();
                wordsListCorrect = keysInfo.getWords();
                for (String str : keysInfo.getWords()){
                    stringBuilder.append(str).append(" ");
                }
            }
        }
    }

    private List<CusMnemonic> wordsRandomList = new ArrayList<>();  // 下方乱序助记词列表
    private List<CusMnemonic> wordsList = new ArrayList<>();  // 上方输入的正确顺序的助记词列表
    private List<String> wordsListCorrect = new ArrayList<>();  // 记录正确顺序的助记词列表
    private int count = -1;
    private int formerPosition = -1;
    private boolean isCorrect = true;
    private void initWords() {
        // 打乱助记词
        wordsRandomList = new ArrayList<>();
        wordsList = new ArrayList<>();
        List<String> words = keysInfo.getWords();
        for (int i = 0; i < words.size(); i++){
            CusMnemonic cusMnemonic = new CusMnemonic(words.get(i), false, false, 0);
            wordsRandomList.add(cusMnemonic);
            CusMnemonic cusMnemonic4 = new CusMnemonic("", false, false, 0);
            wordsList.add(cusMnemonic4);
        }

        Collections.shuffle(wordsRandomList);
        for (int i = 0; i < wordsRandomList.size(); i++){  // 下方列表乱序后，标上序号
            if (wordsRandomList.get(i) != null){
                wordsRandomList.get(i).setPosition(i);
            }
        }

        // 下方乱序列表
        if (mMnemonicAdapterRandom == null) {
            mMnemonicAdapterRandom = new MnemonicChosenAdapter(WordsConfirmActivity.this, R.layout.item_words_chosen, wordsRandomList/*, false*/);
            mRecyclerViewRandom.setHasFixedSize(true);
            mRecyclerViewRandom.setNestedScrollingEnabled(false);
            mRecyclerViewRandom.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL));
            mMnemonicAdapterRandom.bindToRecyclerView(mRecyclerViewRandom);
            mRecyclerViewRandom.setAdapter(mMnemonicAdapterRandom);
        }

        // 上方正序列表
        if (mMnemonicAdapter == null) {
            mMnemonicAdapter = new MnemonicAdapter(WordsConfirmActivity.this, R.layout.item_words, wordsList, true);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setNestedScrollingEnabled(false);
            mRecyclerView.setLayoutManager(new GridLayoutManager(WordsConfirmActivity.this, 3));
            mRecyclerView.addItemDecoration(new ItemGridDecoration(this, 1,R.color.color_dbdbdb));
            mMnemonicAdapter.bindToRecyclerView(mRecyclerView);
            mRecyclerView.setAdapter(mMnemonicAdapter);
            mRecyclerView.setItemAnimator(null);
        }

        // 上方正序列表点击
        mMnemonicAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            // 全部顺序正确，不可点击
            if (count == 11){
                return;
            }
            isCorrect = true;
            if (view.getId() == R.id.item_lin_mnemonic_main || view.getId() == R.id.item_tv_mnemonic) {
                if (StrUtils.isEmpty(wordsList.get(position).getWord())) {  // 用户点击的位置没有助记词
                    return;
                }
                count--;
                // 点击上方列表则将下方列表显示，
                mRecyclerViewRandom.setVisibility(View.VISIBLE);
                LogUtil.i("---------------count==" + count);

                // 移除当前位置，添加一个空的词
                CusMnemonic cusMnemonic = new CusMnemonic("", false, false, 11);

                // 设置下方乱序列表为非选中
                CusMnemonic mnemonicRd = wordsRandomList.get(wordsList.get(position).getPosition());
                CusMnemonic mnemonic = new CusMnemonic(mnemonicRd.getWord(), false, false, mnemonicRd.getPosition());
                mMnemonicAdapterRandom.setData(wordsList.get(position).getPosition(), mnemonic);
                wordsRandomList.set(wordsList.get(position).getPosition(), mnemonic);

                mMnemonicAdapter.remove(position);
                mMnemonicAdapter.addData(cusMnemonic);
                wordsList.remove(position);
                wordsList.add(cusMnemonic);

                LogUtil.i(wordsList.size() + "===wordsList================wordsRandomList===" + mnemonic.getPosition());

                // 判断取消选择后，顺序是否一致
                for (int i = position; i <= count; i++) {
                    if (!wordsList.get(i).getWord().equals(wordsListCorrect.get(i))){
                        isCorrect = false;
                        LogUtil.i("顺序错误==" + i);
                        CusMnemonic cusMnemonicError = new CusMnemonic(wordsList.get(i).getWord(), true, false, wordsList.get(i).getPosition());
                        mMnemonicAdapter.setData(i, cusMnemonicError);
                        wordsList.set(i, cusMnemonicError);

                        mMnemonicAdapterRandom.setData(wordsList.get(i).getPosition(), cusMnemonicError);
                        wordsRandomList.set(wordsList.get(i).getPosition(), cusMnemonicError);
                    }
                }

            }
        });

        // 乱序列表
        mMnemonicAdapterRandom.setOnItemChildClickListener((adapter, view, position) -> {
            // 全部顺序正确，不可点击
            if (count == 11){
                return;
            }
            if (formerPosition == position){
                isCorrect = true;
            }
            if (formerPosition != position && !isCorrect){
                return;
            }
            formerPosition = position;
            if (view.getId() == R.id.item_tv_mnemonic) {
                int po = 0;
                // 上方列表取消选中，移除对应位置的object
                for (int i = 0; i < mMnemonicAdapter.getData().size(); i++) {
                    if (mMnemonicAdapter.getData().get(i).getPosition() == position && mMnemonicAdapter.getData().get(i).isChecked()) {
                        po = i;
                        break;
                    }
                }
                CusMnemonic cusMnemonicRandom = wordsRandomList.get(position);
                if (!cusMnemonicRandom.isChecked()) {  // 未选中状态，点击选中
                    mMnemonicAdapterRandom.getData().get(position).setChecked(true);
                    mMnemonicAdapter.getData().get(po).setChecked(true);
                    count++;
                    LogUtil.i("==========count==" + count);
                    // 判断选择的单词是否正确
                    if (!cusMnemonicRandom.getWord().equals(wordsListCorrect.get(count))) {  // 顺序不正确
                        isCorrect = false;
                        LogUtil.i("顺序错误==" + count);
                        CusMnemonic cusMnemonicRd = new CusMnemonic(cusMnemonicRandom.getWord(), true, false, cusMnemonicRandom.getPosition());
                        mMnemonicAdapterRandom.setData(position, cusMnemonicRd);
                        wordsRandomList.set(position, cusMnemonicRd);

                        CusMnemonic cusMnemonic = new CusMnemonic(cusMnemonicRandom.getWord(), true, false, position);
                        mMnemonicAdapter.setData(count, cusMnemonic);
                        wordsList.set(count, cusMnemonic);

                        LogUtil.i("==========wordsList.getCount()==" + wordsList.get(count));

                    } else {  // 顺序正确
                        CusMnemonic cusMnemonicRd = new CusMnemonic(cusMnemonicRandom.getWord(), true, true, cusMnemonicRandom.getPosition());
                        mMnemonicAdapterRandom.setData(position, cusMnemonicRd);
                        wordsRandomList.set(position, cusMnemonicRd);
                        mMnemonicAdapter.setData(count, new CusMnemonic(cusMnemonicRandom.getWord(), true, true, position));
                        wordsList.set(count, new CusMnemonic(cusMnemonicRandom.getWord(), true, true, position));
                        isCorrect = true;
                        LogUtil.i("顺序正确==" + count);

                        // 全部顺序正确则显示可复制助记词的布局
                        if (count == 11){
                            mRecyclerViewRandom.setVisibility(View.GONE);
                        } else {
                            mRecyclerViewRandom.setVisibility(View.VISIBLE);
                        }
                    }

                } else {  // 用户点击取消选择
                    count--;
                    LogUtil.i("==========count==" + count);
                    CusMnemonic cusMnemonicRd = new CusMnemonic(cusMnemonicRandom.getWord(), false, false, cusMnemonicRandom.getPosition());
                    mMnemonicAdapterRandom.setData(position, cusMnemonicRd);
                    wordsRandomList.set(position, cusMnemonicRd);

                    CusMnemonic cusMnemonicBlank = new CusMnemonic("", false, false, 11);

                    // 上方列表取消选中，移除对应位置的对象
                    mMnemonicAdapter.remove(po);
                    mMnemonicAdapter.addData(cusMnemonicBlank);
                    wordsList.remove(po);
                    wordsList.add(cusMnemonicBlank);

                    // 判断取消选择后，顺序是否一致
                    for (int i = position; i <= count; i++) {
                        if (!wordsList.get(i).getWord().equals(wordsListCorrect.get(i))){
                            isCorrect = false;
                            LogUtil.i("顺序错误==" + i);

                            CusMnemonic cusMnemonic = new CusMnemonic(cusMnemonicRandom.getWord(), false, false, cusMnemonicRandom.getPosition());
                            mMnemonicAdapter.setData(position, cusMnemonic);
                            wordsList.set(position, cusMnemonic);

                            CusMnemonic mnemonic = new CusMnemonic(cusMnemonicRandom.getWord(), false, false, cusMnemonicRandom.getPosition());
                            mMnemonicAdapterRandom.setData(position, mnemonic);
                            wordsRandomList.set(position, mnemonic);

                        }
                    }
                }
            }
            LogUtil.i(wordsList.size() + "===wordsList===================");
        });

    }

    private void dataCheck() {
        if (!isCorrect){
            ToastUtil.showCenterToast(getResources().getString(R.string.words_confirm_mnemonic_is_error));
            return;
        }
        // 判断数据是否为空，是否有误
        List<CusMnemonic> wordsList = mMnemonicAdapter.getData();
        if (StrUtils.isEmpty(wordsList.get(11).getWord())) {
            ToastUtil.showCenterToast(getResources().getString(R.string.words_confirm_mnemonic_is_null));
            return;
        }
        //验证
        presenter.verify(cusCreateWallet,keysInfo,pwd,wordsList,cusCreateWallet.getWhere() != 2 ? getString(R.string.words_confirm_creating) : getString(R.string.please_wait));
    }

    private Disposable disposable;

    @Override
    protected WordsConfirmPresenter createPresenter() {
        return new WordsConfirmPresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.words_confirm_tv_confirm) {
            if (DoubleClickUtils.isNoDoubleClick()) {
                dataCheck();
            }
        }
    }

    @Override
    public void verifySuccess() {
        svProgressHUD.showSuccessWithStatus(getResources().getString(R.string.words_confirm_verify_pass));
        disposable = Observable.timer(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    //延时0.5秒
                    // todo 延时跳转界面
                    EventBus.getDefault().post(new RefreshWalletDataBusBean());//刷新首页
                    ARouter.getInstance().build(ModuleRouterTable.HOME_INDEX_PAGE).navigation();
                    ActivityUtil.finishActivity(this);
                });
    }

    @Override
    public void verifyFail(String msg) {
        ToastUtil.showCenterToast(msg);
    }
}
