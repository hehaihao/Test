package com.xm6leefun.physical_module.certificate;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.xm6leefun.common.app.activity.BaseComActivity;
import com.xm6leefun.common.utils.StrUtils;
import com.xm6leefun.common.utils.status_bar.StatusBarUtil;
import com.xm6leefun.physical_module.R;
import com.xm6leefun.physical_module.R2;

import butterknife.BindView;

/**
 * @Description:鉴定证书
 * @Author: hhh
 * @CreateDate: 2021/4/13 17:13
 */
public class CertificateActivity extends BaseComActivity {
    public static final String PDF_URL = "pdf_url";
    @BindView(R2.id.pdf_view)
    WebView pdf_view;
    private String pdfUrl = "";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_certificate_layout;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setLightModeNotFull(this);
        WebSettings webSettings = pdf_view.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setDisplayZoomControls(false);//不显示缩放按钮

        Bundle args = getIntent().getExtras();
        if(args != null)
            pdfUrl = args.getString(PDF_URL,"");
        if(StrUtils.isEmpty(pdfUrl))pdfUrl = "https://weecot.oss-cn-shenzhen.aliyuncs.com/nft/goods/EPSON001.PDF";
        pdf_view.loadUrl("file:///android_asset/index.html?" + pdfUrl);

    }

    @Override
    protected void initData() {
//        displayFromAsset();
    }
}
