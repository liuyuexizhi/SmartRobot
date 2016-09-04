package com.lyxz.smartrobot;

import android.test.InstrumentationTestCase;
import android.util.Log;

import Utils.HttpUtils;

/**
 * Created by nitong on 2016/9/1.
 */
public class TestHttpUtils extends InstrumentationTestCase {
    public void testSendInfo()
    {
        String res = HttpUtils.doGet("给我讲个笑话");
        Log.e("TAG", res);
        res = HttpUtils.doGet("给我讲个鬼故事");
        Log.e("TAG", res);
        res = HttpUtils.doGet("你好");
        Log.e("TAG", res);
        res = HttpUtils.doGet("你真美");
        Log.e("TAG", res);
    }
}
