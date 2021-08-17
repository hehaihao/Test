package com.xm6leefun.common.net;

import com.google.gson.TypeAdapter;
import com.xm6leefun.common.base.BaseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @Description:唯物链重写ResponseBodyConverter对json预处理
 * @Author: hhh
 * @CreateDate: 2020/9/15
 */
public class BaseZwdResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final TypeAdapter<T> adapter;

    BaseZwdResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String jsonString = value.string();
        try {
            JSONObject object = new JSONObject(jsonString);
            int code = object.getInt("code");
            if (200 != code) {
                String data;
                //错误信息
                data = object.getString("msg");
                //异常处理
                throw new BaseException(code, data);
            }
            //正确返回整个json
            return adapter.fromJson(jsonString);

        } catch (JSONException e) {
            e.printStackTrace();
            //数据解析异常即json格式有变动
            throw new BaseException(BaseException.PARSE_ERR_CODE,BaseException.PARSE_ERROR_MSG);
        } finally {
            value.close();
        }
    }
}