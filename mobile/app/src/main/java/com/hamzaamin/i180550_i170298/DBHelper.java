package com.hamzaamin.i180550_i170298;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    String CREATE_CONTACTS_TABLE="CREATE TABLE " + MyContactContract.Contact.TABLENAME + "("+MyContactContract.Contact._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            MyContactContract.Contact._NAME+" TEXT NOT NULL,"+
            MyContactContract.Contact._PHNO+" TEXT,"+
            MyContactContract.Contact._IMAGE+ " TEXT);";

    String CREATE_CHAT_TABLE="CREATE TABLE " + MyContactContract.Chat.TABLENAME + "("+MyContactContract.Chat._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+MyContactContract.Chat._RECV_PHNO+" TEXT NOT NULL,"+
            MyContactContract.Chat._Receiver+" TEXT NOT NULL,"+
            MyContactContract.Chat._MESSAGE+ " TEXT);";

    String DELETE_CONTACTS_TABLE = "DROP TABLE IF EXISTS " + MyContactContract.Contact.TABLENAME;
    String DELETE_CHAT_TABLE = "DROP TABLE IF EXISTS " + MyContactContract.Chat.TABLENAME;

    public DBHelper(@Nullable Context context) {
        super(context, MyContactContract.DB_NAME, null, MyContactContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
        sqLiteDatabase.execSQL(CREATE_CHAT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DELETE_CONTACTS_TABLE);
        sqLiteDatabase.execSQL(DELETE_CHAT_TABLE);
        onCreate(sqLiteDatabase);
    }
}
