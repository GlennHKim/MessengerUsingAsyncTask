package com.multicampus.anddbsample.vo;

/**
 * Created by student on 2016-06-28.
 */
public class Bear {
    public static String NAME = "name";
    private String id;
    private String name;

    public Bear(String name){
        this.name = name;
    }

    public String getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
