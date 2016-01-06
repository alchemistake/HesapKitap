package com.alchemistake.hesapkitap.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by Alchemistake on 26/08/15.
 */
public class DataSource {

    // Database fields
    private SQLiteDatabase database;
    private Helper dbHelper;
    private final String[] allColumns = { Helper.COLUMN_ID,
            Helper.COLUMN_DAY,
            Helper.COLUMN_MONTH,
            Helper.COLUMN_YEAR,
            Helper.COLUMN_NAME,
            Helper.COLUMN_INCOME,
            Helper.COLUMN_OUTCOME};

    public DataSource(Context context) {
        dbHelper = new Helper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Movement createMovement(int day, int month, int year, String name, double income, double outcome){
        ContentValues values = new ContentValues();

        values.put(Helper.COLUMN_DAY, day);
        values.put(Helper.COLUMN_MONTH, month);
        values.put(Helper.COLUMN_YEAR, year);
        values.put(Helper.COLUMN_NAME, name);
        values.put(Helper.COLUMN_INCOME, income);
        values.put(Helper.COLUMN_OUTCOME, outcome);

        long insertId = database.insert(Helper.TABLE_NAME, null, values);
        Cursor cursor = database.query(Helper.TABLE_NAME, allColumns, Helper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Movement movement = cursorToMovement(cursor);
        cursor.close();
        return movement;
    }

    public void deleteMovement(Movement movement) {
        long id = movement.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(Helper.TABLE_NAME, Helper.COLUMN_ID
                + " = " + id, null);
    }

    public Movement getMovement(int id){
        Cursor cursor = database.query(Helper.TABLE_NAME, allColumns, Helper.COLUMN_ID + " = " + id, null,
                null, null, null);
        cursor.moveToFirst();
        Movement movement = cursorToMovement(cursor);
        cursor.close();
        return movement;
    }

    public List<Movement> getAllMovement() {
        List<Movement> comments = new ArrayList<Movement>();

        Cursor cursor = database.query(Helper.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            comments.add(cursorToMovement(cursor));
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    public List<Movement> getMonthlyMovements(int month, int year){

        List<Movement> comments = new ArrayList<Movement>();

        Cursor cursor = database.query(Helper.TABLE_NAME, allColumns, Helper.COLUMN_MONTH + " = " +
                        month + " and " + Helper.COLUMN_YEAR + " = " + year, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            comments.add(cursorToMovement(cursor));
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    public void updateMovement(int id, int day, int month, int year, String name, double income, double outcome){
        ContentValues values = new ContentValues();

        values.put(Helper.COLUMN_DAY, day);
        values.put(Helper.COLUMN_MONTH, month);
        values.put(Helper.COLUMN_YEAR, year);
        values.put(Helper.COLUMN_NAME, name);
        values.put(Helper.COLUMN_INCOME, income);
        values.put(Helper.COLUMN_OUTCOME, outcome);

        database.update(Helper.TABLE_NAME, values, Helper.COLUMN_ID + " = " + id, null);
    }

    private Movement cursorToMovement(Cursor cursor) {
        return new Movement(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),cursor.getInt(3),cursor.getString(4),cursor.getDouble(5),cursor.getDouble(6));
    }
}
