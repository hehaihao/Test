package com.xm6leefun.export_module.service;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.xm6leefun.export_module.busbean.LoginResultBean;

/**
 * @Description: 首页对外暴露的服务
 * @Author: hhh
 * @CreateDate: 2021/3/25 14:50
 */
public interface IHomeModuleService extends IProvider {
    /**
     * 获取首页中用户数据
     * @return
     */
    LoginResultBean.UserInfoBean getUserDataInHome();
}
