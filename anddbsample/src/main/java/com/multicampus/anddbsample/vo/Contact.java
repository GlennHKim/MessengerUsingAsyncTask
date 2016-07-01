package com.multicampus.anddbsample.vo;

import java.io.Serializable;

/**
 * Created by student on 2016-06-28.
 */
public class Contact implements Serializable{
    // table name
    public static final String TABLE = "Contact";

    // column names
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_TELNUM = "telnum";
    public static final String KEY_DESC = "desc";
    public static final String KEY_ADDRESS = "address";

    // property
    public String id;
    public String name;
    public String telNum;
    public String desc;
    public String address;

    public Contact(String id, String name, String telNum, String desc, String address) {
        this.id = id;
        this.name = name;
        this.telNum = telNum;
        this.desc = desc;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTelNum() {
        return telNum;
    }

    public String getDesc() {
        return desc;
    }

    public String getAddress() {
        return address;
    }
}

