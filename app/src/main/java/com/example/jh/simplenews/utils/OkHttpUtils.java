package com.example.jh.simplenews.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;
import java.util.concurrent.TimeUnit;



/**
 * 作者：jinhui on 2017/2/21
 * 邮箱：1004260403@qq.com
 *
 * OkHttp网络连接封装工具类
 */

public class OkHttpUtils {

    private static final String TAG = "OkHttpUtils";
    private static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler handler;

    //构造方法
    public OkHttpUtils() {
        mOkHttpClient = new OkHttpClient();
        //连接超时、写入超时、读取超时
        mOkHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        mOkHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
        // cookie enabled
        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        handler = new Handler(Looper.getMainLooper());
    }

    public synchronized static OkHttpUtils getmInstance() {
        if (mInstance == null) {
            mInstance = new OkHttpUtils();
        }
        return mInstance;
    }

    // get请求
    private void getRequest(String url, ResultCallBack callback) {
        Log.e(TAG, "getRequest方法被执行---------------");
        Request request = new Request.Builder().url(url).build();
        deliveryResult(callback, request);
    }

    // post请求
    private void postRequest(String url, ResultCallBack callback, List<Param> params){
        Request request = buildPostRequest(url, params);
        deliveryResult(callback, request);
    }

    //建立post请求
    private Request buildPostRequest(String url, List<Param> params) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Param param:params) {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder().url(url).post(requestBody).build();
    }

    //提取结果
    private void deliveryResult(final ResultCallBack callback, Request request) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sendFailCallBack(callback, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try{
                    String str = response.body().string();
                    if(callback.mType == String.class){
                        sendSuccessCallBack(callback, str);
                    }else{
                        Object object = JsonUtils.deserialize(str, callback.mType);
                        sendSuccessCallBack(callback, object);
                    }
                }catch(Exception e){
                    LogUtils.e(TAG, "convert json failure", e);
                    sendFailCallBack(callback, e);
                }

            }
        });

    }

    // 发送成功回调方法
    private void sendSuccessCallBack(final ResultCallBack callback, final Object object) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(callback != null){
                    callback.OnSuccess(object);
                }
            }
        });
    }


    // 发送失败回调方法
    private void sendFailCallBack(final ResultCallBack callback, final Exception e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.OnFailure(e);
                }
            }
        });
    }


    /*******************对外接口****************/

    /**
     * get请求
     *
     * @param url      请求url
     * @param callback 请求回调
     */

    public static void get(String url, ResultCallBack callback) {
        getmInstance().getRequest(url, callback);
    }

    /**
     * post请求
     *
     * @param callback
     */
    public static void post(String url, ResultCallBack callback, List<Param> params) {
        getmInstance().postRequest(url, callback, params);
    }


    /**
     * http请求回调类,回调方法在UI线程中执行
     *
     * @param <T>
     */
    public static abstract class ResultCallBack<T> {
        // 反射  java.lang.reflect接口
        Type mType;

        public ResultCallBack() {
            mType = getSuperclassTypeParameter(getClass());
        }

        private Type getSuperclassTypeParameter(Class<? extends ResultCallBack> aClass) {
            Type superclass = aClass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter");     // 抛出运行时异常
            }
            ParameterizedType parameterizedType = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
        }

        /**
         * 请求成功回调
         *
         * @param response
         */
        public abstract void OnSuccess(T response);

        /**
         * 请求失败回调
         *
         * @param e
         */
        public abstract void OnFailure(Exception e);
    }

    /**
     * post请求参数类
     */
    public static class Param {
        String key;
        String value;

        public Param() {
        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }


}

