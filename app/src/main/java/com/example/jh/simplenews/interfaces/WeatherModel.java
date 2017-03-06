package com.example.jh.simplenews.interfaces;

import android.content.Context;

import com.example.jh.simplenews.Impl.WeatherModelImpl;

/**
 * 作者：jinhui on 2017/2/21
 * 邮箱：1004260403@qq.com
 */

public interface WeatherModel {
    void loadWeatherData(String cityName, WeatherModelImpl.LoadWeatherListener listener);
    void loadLocation(Context context, WeatherModelImpl.LoadLocationListener listener);
}
