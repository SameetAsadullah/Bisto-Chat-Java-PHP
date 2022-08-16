package com.hamzaamin.i180550_i170298;

import android.provider.BaseColumns;

public class MyContactContract {
    public static String DB_NAME = "myContacts.db";
    public static int DB_VERSION = 5;

    public static class Contact implements BaseColumns {
        public static String TABLENAME = "contactsTable";
        public static String _NAME = "name";
        public static String _PHNO = "phoneNo";
        public static String _IMAGE = "image";
    }

    public static class Chat implements BaseColumns {
        public static String TABLENAME = "chatTable";
        public static String _RECV_PHNO = "recv_phno";
        public static String _Receiver = "receiver";
        public static String _MESSAGE = "message";
    }
}
