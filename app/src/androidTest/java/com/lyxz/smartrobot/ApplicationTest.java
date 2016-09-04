package com.lyxz.smartrobot;

import android.app.Application;
import android.graphics.Bitmap;
import android.test.ApplicationTestCase;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

import Utils.HttpUtils;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testSendInfo() {
        String res = HttpUtils.doGet("给我讲个笑话");
        Log.e("TAG", res);
        res = HttpUtils.doGet("给我讲个鬼故事");
        Log.e("TAG", res);
        res = HttpUtils.doGet("你好");
        Log.e("TAG", res);
        res = HttpUtils.doGet("你真美");
        Log.e("TAG", res);
        URL url = null;
        try {
            url = new URL("http://www.dabaoku.com/sucaidatu/dongwu/chongwujingling/902438.JPG");
            Bitmap bitmap = HttpUtils.returnBitmap(url);
            Log.e("TAG", bitmap.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}