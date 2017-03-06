package com.example.jh.simplenews.Impl;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.text.TextUtils;

import com.example.jh.simplenews.beans.WeatherBean;
import com.example.jh.simplenews.commons.Urls;
import com.example.jh.simplenews.interfaces.WeatherModel;
import com.example.jh.simplenews.utils.LogUtils;
import com.example.jh.simplenews.utils.OkHttpUtils;
import com.example.jh.simplenews.utils.WeatherJsonUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 作者：jinhui on 2017/2/21
 * 邮箱：1004260403@qq.com
 */

public class WeatherModelImpl implements WeatherModel {
    private static final String TAG = "WeatherModelImpl";

    @Override
    public void loadWeatherData(String cityName, final LoadWeatherListener listener) {
        try {
            String url = Urls.WEATHER + URLEncoder.encode(cityName, "utf-8");
            OkHttpUtils.ResultCallback<String> callback = new OkHttpUtils.ResultCallback<String>() {
                @Override
                public void onSuccess(String response) {
                    List<WeatherBean> lists = WeatherJsonUtils.getWeatherInfo(response);
                    listener.onSuccess(lists);
                }

                @Override
                public void onFailure(Exception e) {
                    listener.onFailure("load weather data failure.", e);
                }
            };
            OkHttpUtils.get(url, callback);
        } catch (UnsupportedEncodingException e) {
            LogUtils.e(TAG, "url encode error.", e);
        }
    }

    @Override
    public void loadLocation(Context context, final LoadLocationListener listener) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                LogUtils.e(TAG, "location failure.");
                listener.onFailure("location failure.", null);
                return;
            }
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(location == null) {
            LogUtils.e(TAG, "location failure.");
            listener.onFailure("location failure.", null);
            return;
        }
        double latitude = location.getLatitude();     //经度
        LogUtils.e(TAG, "经度=" + latitude);
        double longitude = location.getLongitude(); //纬度
        LogUtils.e(TAG, "纬度=" + longitude);
        String url = getLocationURL(latitude, longitude);
        OkHttpUtils.ResultCallback<String> callback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                String city = WeatherJsonUtils.getCity(response);
                if(TextUtils.isEmpty(city)) {
                    LogUtils.e(TAG, "load location info failure.");
                    listener.onFailure("load location info failure.", null);
                } else {
                    listener.onSuccess(city);
                }
            }

            @Override
            public void onFailure(Exception e) {
                LogUtils.e(TAG, "load location info failure.", e);
                listener.onFailure("load location info failure.", e);
            }
        };
        OkHttpUtils.get(url, callback);
    }

    private String getLocationURL(double latitude, double longitude) {
        StringBuffer sb = new StringBuffer(Urls.INTERFACE_LOCATION);
        sb.append("?output=json").append("&referer=32D45CBEEC107315C553AD1131915D366EEF79B4");
        sb.append("&location=").append(latitude).append(",").append(longitude);
        LogUtils.d(TAG, sb.toString());
        return sb.toString();
    }


    public interface LoadWeatherListener {
        void onSuccess(List<WeatherBean> list);
        void onFailure(String msg, Exception e);
    }

    public interface LoadLocationListener {
        void onSuccess(String cityName);
        void onFailure(String msg, Exception e);
    }

}
