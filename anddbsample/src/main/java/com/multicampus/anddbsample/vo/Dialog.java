package com.multicampus.anddbsample.vo;

/**
 * Created by student on 2016-06-28.
 */
public class Dialog {
    // table name
    public static final String TABLE = "Contact";

    // column names
    public static final String KEY_SENDER = "sender";
    public static final String KEY_RECEIVER = "receiver";
    public static final String KEY_TEXT = "text";
    public static final String KEY_TIME = "time";

    // property
    public String sender;
    public String receiver;
    public String text;
    public String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Dialog(String sender, String receiver, String text, String time) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.time = time;
    }
}

