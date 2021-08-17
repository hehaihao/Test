package com.xm6leefun.common.utils;

import java.util.ArrayList;

public class SignUtils {

    public static final String SIGN_KEY = "outsideSign";

    /**
     * 添加公共参数
     * @param paramList
     * @return
     */
    public static String getSignFormList(ArrayList<String> paramList){
        String replace = paramList.toString().replace("[", "{").replace("]", "}").replace(" ","");
        String s = MD5Utils.encryptMD5((MD5Utils.encryptMD5((replace)) + "xm6leefun"));
        return s;
    }
}
