package com.example.dohodrashod;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DohodRashodX.db"; // название бд
    private static final int SCHEMA = 19; // версия базы данных
    static final String TABLE = "Dohod"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DATA = "data";
    public static final String COLUMN_SUMMA = "summa";
    public static final String COLUMN_MONTH = "month";

    static final String TABLE2 = "Rashod"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID2 = "_id";
    public static final String COLUMN_NAME2 = "name";
    public static final String COLUMN_DATA2 = "data";
    public static final String COLUMN_SUMMA2 = "summa";
    public static final String COLUMN_MONTH2 = "month";

    public DatabaseHelper(Context context) {
        super( context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE Dohod (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + COLUMN_NAME + " TEXT ," + COLUMN_DATA + " TEXT, " + COLUMN_SUMMA + " TEXT," + COLUMN_MONTH + " TEXT);");
        db.execSQL("CREATE TABLE Rashod (" + COLUMN_ID2 + " INTEGER PRIMARY KEY AUTOINCREMENT ," + COLUMN_NAME2 + " TEXT ," + COLUMN_DATA2 + " TEXT, " + COLUMN_SUMMA2+ " TEXT," + COLUMN_MONTH2 + " TEXT);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE2);
        onCreate(db);
    }
}
