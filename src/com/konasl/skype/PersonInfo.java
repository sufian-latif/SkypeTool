package com.konasl.skype;

import com.skype.SkypeException;
import com.skype.User;

import javax.print.attribute.standard.MediaSize;

/**
 * Created by Sufian Latif on 11/5/2015.
 */
public class PersonInfo {
    private String name;
    private String handle;
    private String phone;
    private boolean isFriend;

    private final static String NA = "Not Available";

    public PersonInfo(String handle) {
        this.handle = handle;
        User user = User.getInstance(handle);
        try {
            name = user.getFullName();
            if(name.trim().equals("")) name = handle;
            phone = user.getMobilePhoneNumber();
            if(phone.trim().equals("")) phone = NA;
            isFriend = user.getBuddyStatus() == User.BuddyStatus.ADDED;
        } catch (SkypeException se) {
            name = handle;
            phone = NA;
            isFriend = false;
        }
    }

    public String getName() {
        return name;
    }

    public String getHandle() {
        return handle;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public String toString() {
        return handle + " | " + name + " | " + phone + " | " + "friend: " + isFriend;
    }
}
