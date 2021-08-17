package com.xm6leefun.common.net.interceptor;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.xm6leefun.common.utils.AppAllKey;
import com.xm6leefun.common.utils.SharePreferenceUtil;
import com.xm6leefun.common.utils.SignUtils;
import com.xm6leefun.common.utils.encode.Base64Utils;
import com.xm6leefun.common.utils.encode.RSAUtils;

import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * @Description:真唯度参数拦截器，统一添加token入参，处理接口入参排序并添加outsideSign
 * @Author: hhh
 * @CreateDate: 2020/11/23 15:21
 */
public class ParamInterceptor implements Interceptor {
    public static final String ENCRYPT_KEY = "encrypt";
    private Gson gson = new Gson();

    @Override
    public Response intercept(Chain chain) throws IOException {
        //获取原始的originalRequest
        Request originalRequest = chain.request();
        //请求参数按照字母顺序排序
        Map<String, String> content = new TreeMap<>();
        Request.Builder oldBuilder = originalRequest.newBuilder();
        HttpUrl httpUrl = originalRequest.url();
        ArrayList<String> paramList = new ArrayList<>();
        String token = SharePreferenceUtil.getString(AppAllKey.TOKEN);
        if(!TextUtils.isEmpty(token)){
            content.put("token",token);
        }
        try {
            //请求入参排序
            if ("GET".equals(originalRequest.method())) {
                HttpUrl.Builder builder = httpUrl.newBuilder();
                int size = httpUrl.querySize();
                for (int i = 0; i < size; i++) {
                    builder.removeAllQueryParameters(httpUrl.queryParameterName(i));
                    content.put(httpUrl.queryParameterName(i), httpUrl.queryParameterValue(i));
                }
                for (String key : content.keySet()) {
                    builder.addQueryParameter(key, content.get(key));
                    paramList.add(key + "=" + content.get(key));
                }
                //添加公共参数
                builder.addQueryParameter(SignUtils.SIGN_KEY, SignUtils.getSignFormList(paramList));
                HttpUrl newHttpUrl = builder.build();
                originalRequest = oldBuilder.url(newHttpUrl).build();
            } else if ("POST".equals(originalRequest.method())) {
                RequestBody body = originalRequest.body();
                if (body instanceof FormBody) {
                    FormBody formBody = (FormBody) body;
                    FormBody.Builder builder = new FormBody.Builder();
                    for (int i = 0; i < formBody.size(); i++) {
                        content.put(formBody.name(i), formBody.value(i));
                    }
                    String encrypt = content.get(ENCRYPT_KEY);//需要进行签名加密的接口需要传入encrypt字段
                    if (TextUtils.isEmpty(encrypt) || "false".equals(encrypt)) {
                        for (String key : content.keySet()) {
                            builder.add(key, content.get(key));
                            paramList.add(key + "=" + content.get(key));
                        }
                    } else {//将字段转为json进行rsa
                        try {
                            content.remove(ENCRYPT_KEY);
                            for (String key : content.keySet()) {
                                paramList.add(key + "=" + content.get(key));
                            }
                            String json = gson.toJson(content);
                            PublicKey publicKey = RSAUtils.loadPublicKey(RSAUtils.PUBLIC_KEY);
                            byte[] bytes = RSAUtils.encryptData(json.getBytes(), publicKey);
                            String encode = Base64Utils.encode(bytes);
                            builder.add(ENCRYPT_KEY, encode);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    //添加公共参数
                    builder.add(SignUtils.SIGN_KEY, SignUtils.getSignFormList(paramList));
                    originalRequest = oldBuilder.url(httpUrl).post(builder.build()).build();
                } else if (body instanceof MultipartBody) {
                    MultipartBody requestBody = (MultipartBody) body;
                    MultipartBody.Builder multipartBodybuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                    for (int i = 0; i < requestBody.size(); i++) {
                        MultipartBody.Part part = requestBody.part(i);
                        String normalParamKey;
                        String normalParamValue;
                        normalParamValue = getParamContent(requestBody.part(i).body());
                        Headers headers = part.headers();
                        for (String name : headers.names()) {
                            String headerContent = headers.get(name);
                            if (!TextUtils.isEmpty(headerContent)) {
                                String[] normalParamKeyContainer = headerContent.split("name=\"");
                                if (normalParamKeyContainer.length == 2) {
                                    normalParamKey = normalParamKeyContainer[1].split("\"")[0];
                                    content.put(normalParamKey, normalParamValue);
                                    break;
                                } else {
                                    multipartBodybuilder.addPart(part);
                                }
                            }
                        }
                    }
                    for (String key : content.keySet()) {
                        MultipartBody.Part part = MultipartBody.Part.createFormData(key, content.get(key));
                        multipartBodybuilder.addPart(part);
                        paramList.add(key + "=" + content.get(key));
                    }
                    //添加公共参数
                    multipartBodybuilder.addPart(MultipartBody.Part.createFormData(SignUtils.SIGN_KEY, SignUtils.getSignFormList(paramList)));
                    originalRequest = oldBuilder.url(httpUrl).post(multipartBodybuilder.build()).build();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return  chain.proceed(originalRequest);
    }

    /**
     * 获取常规post请求参数
     */
    private String getParamContent(RequestBody body) throws IOException {
        Buffer buffer = new Buffer();
        body.writeTo(buffer);
        return buffer.readUtf8();
    }
}
