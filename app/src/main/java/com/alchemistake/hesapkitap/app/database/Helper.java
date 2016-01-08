package com.alchemistake.hesapkitap.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Helper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "movements";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DAY = "day";
    public static final String COLUMN_MONTH = "month";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_INCOME = "income";
    public static final String COLUMN_OUTCOME = "outcome";

    public static final String DATABASE_NAME = "commments.db";
    private static final int DATABASE_VERSION = 2;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_DAY + " integer, " +
            COLUMN_MONTH + " integer, " +
            COLUMN_YEAR + " integer, " +
            COLUMN_NAME + " text not null, " +
            COLUMN_INCOME + " double, " +
            COLUMN_OUTCOME + " double" +
            ");";

    public Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("create table temp as select * from " + TABLE_NAME + ";");
        db.execSQL("drop table " + TABLE_NAME + ";");
        onCreate(db);
        db.execSQL("insert into " + TABLE_NAME + " select * from temp;");
        db.execSQL("drop table temp;");
    }

}
