package com.xm6leefun.points_module.zwd_login.api;


import com.xm6leefun.common.base.BaseDataBean;
import com.xm6leefun.export_module.busbean.LoginResultBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import static com.xm6leefun.common.net.interceptor.ParamInterceptor.ENCRYPT_KEY;


/**
 * @Description:
 * @Author: hhh
 * @CreateDate: 2020/11/25 14:24
 */
public interface LoginApiService {
    /**
     * 发送验证码
     * @param mobile
     * @param type
     * @return
     */
    @POST("userInfo/getNoteCode")
    @FormUrlEncoded
    Observable<BaseDataBean<String>> getNoteCode(@Field("mobile") String mobile, @Field("type") String type);

    /**
     * 验证码登录
     * @param mobile
     * @param code
     * @return
     */
    @POST("userInfo/userLogin")
    @FormUrlEncoded
    Observable<BaseDataBean<LoginResultBean>> login(@Field("mobile") String mobile, @Field("code") String code);

    /**
     * 密码登录
     * @param mobile
     * @param password
     * @return
     */
    @POST("userInfo/userPassLogin")
    @FormUrlEncoded
    Observable<BaseDataBean<LoginResultBean>> pswLogin(@Field(ENCRYPT_KEY) boolean encrypt, @Field("mobile") String mobile, @Field("password") String password);

    /**
     * 微信登录
     * @param openId
     * @param nickName
     * @param headPortrait
     * @return
     */
    @POST("userInfo/wechatLogin")
    @FormUrlEncoded
    Observable<BaseDataBean<LoginResultBean>> wxLogin(@Field("openId") String openId, @Field("nickName") String nickName, @Field("headPortrait") String headPortrait, @Field("unionid") String unionid);
}
