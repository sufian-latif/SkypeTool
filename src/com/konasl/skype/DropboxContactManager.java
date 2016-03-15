package com.konasl.skype;

import com.dropbox.core.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/**
 * Created by Sufian Latif on 11/10/2015.
 */
public class DropboxContactManager implements ContactManager {

    private static final String DROPBOX_APP_KEY = "98ifa8i10unxn26";
    private static final String DROPBOX_APP_SECRET = "ed4r1kt8v44ufyz";
    private static final String DROPBOX_ACCESS_TOKEN = "bo84Kr7LwjAAAAAAAAAAEdpv06NEA_o3V-tYV4jrxRgU6Hjto3h5dMDRsFbyc_Yr";
    private static final String CONTACT_LIST_FILE_PATH = "/Skype_ID_list.txt";
    DbxClient dbxClient;

    public DropboxContactManager() throws IOException, DbxException {
            dbxClient = authDropbox();
    }

    public DbxClient authDropbox() throws IOException, DbxException {
        DbxAppInfo dbxAppInfo = new DbxAppInfo(DROPBOX_APP_KEY, DROPBOX_APP_SECRET);
        DbxRequestConfig dbxRequestConfig = new DbxRequestConfig("SkypeTool/1.0", Locale.getDefault().toString());
        dbxClient = new DbxClient(dbxRequestConfig, DROPBOX_ACCESS_TOKEN);
        //System.out.println("Dropbox Account Name: " + dbxClient.getAccountInfo().displayName);

        return dbxClient;
    }

    @Override
    public ArrayList<String> loadContacts() throws IOException, DbxException {
        ArrayList<String> contacts = new ArrayList<String>();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DbxEntry.File contactsFile = dbxClient.getFile(CONTACT_LIST_FILE_PATH, null, baos);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(bais));
        while ((line = reader.readLine()) != null) contacts.add(line.trim());
        bais.close();
        baos.close();

        return contacts;
    }

    @Override
    public void updateContacts(ArrayList<String> contacts) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(baos));
            Collections.sort(contacts);
            for (String name : contacts) writer.write(name + "\n");
            writer.flush();
            byte[] bytes = baos.toByteArray();

            String rev = dbxClient.getMetadata(CONTACT_LIST_FILE_PATH).asFile().rev;
            //System.out.println(rev);

            DbxEntry.File contactsFile = dbxClient.uploadFile(CONTACT_LIST_FILE_PATH,
                        DbxWriteMode.update(rev), bytes.length, new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DbxException e) {
            e.printStackTrace();
        }
    }
}
