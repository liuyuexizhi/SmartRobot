package bean;


import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by nitong on 2016/9/1.
 */
public class ChatMessage {
    private String msg;
    private Bitmap img;
    private Type type;
    private Date date;

    public enum Type {
        INCOMING, OUTCOMING
    }

    public ChatMessage() {

    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public ChatMessage(String msg, Type type, Date date) {
        this.msg = msg;
        this.type = type;
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
