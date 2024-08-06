package com.example.ipocalculation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;

public class SqlDB extends SQLiteOpenHelper {

    public static final String IpoDB = "IpoCalculation";
    public static final String tbl_History = "SaveHistory";

    public static final int dbVersionForInit = 1;
    public static final String id = "ID";
    public static final String purchasePrice = "PurchasePrice";
    public static final String sellPrice = "SellPrice";
    public static final String dateTime = "DateTime";
    public static final String sellQuantity = "Quantity";

    public static final int DatabaseVersion = dbVersionForInit;

    public SqlDB(@Nullable Context context) {
        super(context, IpoDB, null, DatabaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        CreateTableHistory(db);
    }

    private void CreateTableHistory(SQLiteDatabase db) {
        String query = "CREATE TABLE " + tbl_History + " ( "
                + id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + purchasePrice + " REAL, "
                + sellPrice + " REAL, "
                + sellQuantity + " INTEGER, "
                + dateTime + " TEXT ) ";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion && oldVersion < dbVersionForInit) {
            db.execSQL(" DROP TABLE IF EXISTS " + tbl_History);
            onCreate(db);
        }
    }

    public void SaveData(int purchaseprice, int sellprice, int sellquantity, String datetime) {
        SQLiteDatabase sdb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(purchasePrice, purchaseprice);
        cv.put(sellPrice, sellprice);
        cv.put(sellQuantity, sellquantity);
        cv.put(dateTime, datetime);
        sdb.insert(tbl_History, null, cv);
    }

//    public ArrayList<Integer> GetData(){
//        SQLiteDatabase sdb = this.getReadableDatabase();
//        Cursor cursor = sdb.rawQuery("Select * From " + tbl_History,null);
//        while (cursor.moveToNext()){
//            int purchaseprice = cursor.getColumnIndex(cursor.getColumnName(1));
//            int sellprice = cursor.getColumnIndex(cursor.getColumnName(2));
//            int quantity = cursor.getColumnIndex(cursor.getColumnName(3));
//            String datetime = cursor.getColumnName(4);
//        }
//    }
}