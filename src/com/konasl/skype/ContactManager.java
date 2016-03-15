package com.konasl.skype;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Sufian Latif on 11/10/2015.
 */
public interface ContactManager {
    public abstract ArrayList<String> loadContacts() throws Exception;
    public abstract void updateContacts(ArrayList<String> contacts);
}
