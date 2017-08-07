package com.example.arnarfreyr.dicegame2000;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by arnarfreyr on 27.6.2017.
 */

public class SQLHandler extends SQLiteOpenHelper {

    public static final int DB_VERSION = 9;
    public static final String DB_NAME = "dices.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SQLConstants.Entries.TABLE_NAME + " (" +
            SQLConstants.Entries._ID + " INTEGER PRIMARY KEY," +
            SQLConstants.Entries.HIGHSCORES_USER + " TEXT," +
            SQLConstants.Entries.HIGHSCORE_SCORE + " INTEGER," +
            SQLConstants.Entries.HIGHSCORE_DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)";

    private static final String SQL_CREATE_GAME =
            "CREATE TABLE " + SQLConstants.Entries.TABLE_GAMES + " (" +
                    SQLConstants.Entries._ID + " INTEGER PRIMARY KEY," +
                    SQLConstants.Entries.GAME_FINISHED + " INTEGER DEFAULT 0)";

    private static final String SQL_CREATE_ROUNDS =
            "CREATE TABLE " + SQLConstants.Entries.TABLE_ROUNDS + " (" +
                    SQLConstants.Entries._ID + " INTEGER PRIMARY KEY," +
                    SQLConstants.Entries.ROUND_BET + " INTEGER NOT NULL," +
                    SQLConstants.Entries.ROUND_SCORE + " INTEGER NOT NULL," +
                    SQLConstants.Entries.ROUND_FK_GAME + " INTEGER NOT NULL);";

    private static final String SQL_DELETE_USER =
            "DROP TABLE IF EXISTS " +
                    SQLConstants.Entries.TABLE_NAME + ";";

    private static final String SQL_DELETE_GAMES =
            "DROP TABLE IF EXISTS " +
                    SQLConstants.Entries.TABLE_GAMES + ";";

    private static final String SQL_DELETE_ROUNDS =
            "DROP TABLE IF EXISTS " +
                    SQLConstants.Entries.TABLE_ROUNDS + ";";

    public SQLHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_GAME);
        db.execSQL(SQL_CREATE_ROUNDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_USER);
        db.execSQL(SQL_DELETE_GAMES);
        db.execSQL(SQL_DELETE_ROUNDS);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
