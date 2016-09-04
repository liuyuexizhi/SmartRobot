package Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import bean.ChatMessage;
import bean.Result;

/**
 * Created by nitong on 2016/9/1.
 */
public class HttpUtils {

    private static String URL = "http://www.tuling123.com/openapi/api";
    private static String API_KEY = "df639af05c0f4703ba278bca66848048";


    /**
     * 发送一个消息，返回数据
     *
     * @param msg
     * @return
     */
    public static ChatMessage sendMessage(String msg) {

        ChatMessage chatMessage = new ChatMessage();
        String jsonRes = doGet(msg);
        Gson gson = new Gson();
        Result result = null;

        try {
            result = gson.fromJson(jsonRes, Result.class);
            chatMessage.setMsg(result.getText());
            if (result.getUrl() != null) {
                chatMessage.setImg(returnBitmap(result.getUrl()));
            }
        } catch (Exception e) {
            chatMessage.setMsg("服务器繁忙，请稍候再试!!!");
        }
        chatMessage.setDate(new Date());
        chatMessage.setType(ChatMessage.Type.INCOMING);
        return chatMessage;
    }

    public static String doGet(String msg) {
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        String result = null;
        String url = setParams(msg);
        try {
            java.net.URL urlNet = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlNet.openConnection();
            conn.setReadTimeout(5 * 1000);
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestMethod("POST");

            baos = new ByteArrayOutputStream();
            is = conn.getInputStream();
            int len = -1;
            byte[] buf = new byte[1024];
            while ((len = is.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            baos.flush();
            result = new String(baos.toByteArray());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String setParams(String msg) {
        String url = "";
        try {
            url = URL + "?key=" + API_KEY + "&info="
                    + URLEncoder.encode(msg, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 根据图片的url获得Bitmap对象
     *
     * @param fileUrl
     * @return
     */
    public static Bitmap returnBitmap(URL fileUrl) {

        Bitmap bitmap = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) fileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;

    }
}


