package com.konasl.skype.ui;

import com.konasl.skype.PersonInfo;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Created by Sufian Latif on 11/17/2015.
 */
public class ContactTableModel extends AbstractTableModel {
    private final String[] columnHeaders = { "Select", "Name", "Skype ID", "Phone" };
    private Object[][] contactData;

    public ContactTableModel(ArrayList<PersonInfo> contactList) {
        contactData = new Object[contactList.size()][];
        for (int i = 0; i < contactList.size(); i++) {
            PersonInfo pInfo = contactList.get(i);
            contactData[i] = new Object[] { false, pInfo.getName(), pInfo.getHandle(), pInfo.getPhone() };
        }
    }

    @Override
    public int getRowCount() {
        return contactData.length;
    }

    @Override
    public int getColumnCount() {
        return columnHeaders.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnHeaders[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex == 0 ? Boolean.class : String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return contactData[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        contactData[rowIndex][columnIndex] = aValue;
        fireTableCellUpdated(rowIndex, columnIndex);
    }
}
