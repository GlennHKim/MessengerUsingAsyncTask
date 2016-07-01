package com.multicampus.anddbsample.view;

import com.multicampus.anddbsample.vo.Contact;

import java.io.Serializable;

/**
 * Created by student on 2016-06-29.
 */
public class ContactItem implements Serializable{
    private Contact contact;

    public ContactItem(Contact contact) {
        this.contact = contact;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
