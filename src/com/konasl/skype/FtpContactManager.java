package com.konasl.skype;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Sufian Latif on 11/10/2015.
 */
public class FtpContactManager implements ContactManager {

    private String ftpAddr;
    private String filePath;
    private String username;
    private String password;

    FtpContactManager(String ftpAddr, String filePath, String username, String password) {
        this.ftpAddr = ftpAddr;
        this.filePath = filePath;
        this.username = username;
        this.password = password;
    }

    @Override
    public ArrayList<String> loadContacts() {
        FTPClient client = new FTPClient();
        InputStream iStream;
        ArrayList<String> contacts = new ArrayList<String>();
        try {
            client.connect(ftpAddr);
            //client.login(username, password);
            FTPFile[] files = client.listDirectories();
            for(FTPFile file : files) {
                System.out.println(file.getName());
            }
            iStream = client.retrieveFileStream(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(iStream));
            String contact;
            while((contact = reader.readLine()) != null) {
                contacts.add(contact.trim());
            }
            iStream.close();
            client.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return contacts;
    }

    @Override
    public void updateContacts(ArrayList<String> contacts) {

    }
}
