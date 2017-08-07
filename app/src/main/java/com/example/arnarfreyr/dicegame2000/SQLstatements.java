package com.example.arnarfreyr.dicegame2000;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnarfreyr on 27.6.2017.
 */

public class SQLstatements {

    private SQLHandler dbConnection = null;
    private SQLiteDatabase db = null;

    public SQLstatements(Context context) {
        dbConnection = new SQLHandler(context);

    }

    private void setWrite() {
        db = dbConnection.getWritableDatabase();
    }

    private void setRead() {
        db = dbConnection.getWritableDatabase();
    }

    public void addHighScore(UserData userData) {
        setWrite();

        ContentValues values = new ContentValues();
        values.put(SQLConstants.Entries.HIGHSCORES_USER, userData.getName());
        values.put(SQLConstants.Entries.HIGHSCORE_SCORE, userData.getScore());
        values.put(SQLConstants.Entries.HIGHSCORE_DATETIME, userData.getDate());

        db.insert(SQLConstants.Entries.TABLE_NAME, null, values);
    }

    public void addRound(Integer roundBet, Integer roundScore, Integer gameNr) {
        setWrite();

        ContentValues values = new ContentValues();
        values.put(SQLConstants.Entries.ROUND_BET, roundBet);
        values.put(SQLConstants.Entries.ROUND_SCORE, roundScore);
        values.put(SQLConstants.Entries.ROUND_FK_GAME, gameNr);

        db.insert(SQLConstants.Entries.TABLE_ROUNDS, null, values);
    }

    public void addGame( Integer finished ) {
        setWrite();

        ContentValues values = new ContentValues();
        values.put(SQLConstants.Entries.GAME_FINISHED, finished);

        db.insert(SQLConstants.Entries.TABLE_GAMES, null, values);
    }

    public void addGame( Integer gameNr, Integer finished ) {
        setWrite();

        ContentValues values = new ContentValues();
        values.put(SQLConstants.Entries.GAME_FINISHED, finished);

        Log.d("CHECKED FINISHED --> ", finished.toString());
        Log.d("---GAME NR --> ", gameNr.toString());
        String[] args = {
            String.valueOf(finished),
            String.valueOf(gameNr)
        };
        db.rawQuery("UPDATE games SET finished = ? WHERE _id = ?", args);
        //return db.update(SQLConstants.Entries.TABLE_GAMES, values, where, whereArgs);
    }

    public Integer getGameId(Integer finished) {
        setRead();

        String[] columns = {
            SQLConstants.Entries._ID
        };

        String where = " finished=?";

        String[] whereArgs = {
                finished.toString()
        };

        String limit = "1";

        Cursor cursor = db.query(
                SQLConstants.Entries.TABLE_GAMES,
                columns,
                where,
                whereArgs,
                null,
                null,
                "_id DESC",
                limit
        );
        Integer id = -1;
        Integer count = cursor.getCount();
        Log.d("CURSOR COUNT -> ", count.toString());
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            id = cursor.getInt(cursor.getColumnIndexOrThrow(SQLConstants.Entries._ID));
        }
        Log.d("ID --> ", id.toString());
        cursor.close();
        return id;
    }

    public Boolean getGameFinished() {
        setRead();

        String[] columns = {
                SQLConstants.Entries._ID,
                SQLConstants.Entries.GAME_FINISHED
        };

        String limit = "1";

        Cursor cursor = db.query(
                SQLConstants.Entries.TABLE_GAMES,
                columns,
                null,
                null,
                null,
                null,
                "_id DESC",
                limit
        );
        Boolean finished = true;
        while(cursor.moveToNext()) {
            finished = cursor.getInt(cursor.getColumnIndexOrThrow(SQLConstants.Entries.GAME_FINISHED)) != 0;
        }

        cursor.close();
        return finished;
    }

    /*public void addRoll( Integer gameNr ) {
        setWrite();

        ContentValues values = new ContentValues();
        values.put(SQLConstants.Entries.ROLLS_NR, rollNr);
        values.put(SQLConstants.Entries.ROLL_SCORE, rollScore);
        values.put(SQLConstants.Entries.ROLL_FK_GAME, gameNr);

        db.insert(SQLConstants.Entries.TABLE_ROLLS, null, values);
    }

    public ArrayList<Integer> getRolls( Integer gameNr ) {
        setRead();

        String[] columns = {
                SQLConstants.Entries._ID,
                SQLConstants.Entries.ROLLS_NR,
                SQLConstants.Entries.ROLL_SCORE,
        };

        String where = SQLConstants.Entries.ROLL_FK_GAME + " = ?";

        String[] whereArgs = {
                gameNr.toString()
        };

        Cursor cursor = db.query(
                SQLConstants.Entries.TABLE_ROLLS,
                columns,
                where,
                whereArgs,
                null,
                null,
                null,
                null
        );
        ArrayList<Integer> rolls = new ArrayList<>();
        while(cursor.moveToNext()) {
            rolls.add(cursor.getInt(cursor.getColumnIndexOrThrow(SQLConstants.Entries.ROLL_SCORE)));
        }

        return rolls;
    }*/

    public ArrayList<Integer> getRoundBets(Integer gameNr) {
        setRead();

        String[] columns = {
                SQLConstants.Entries._ID,
                SQLConstants.Entries.ROUND_BET,
        };

        String where = SQLConstants.Entries.ROUND_FK_GAME + " = ?";

        String[] whereArgs = {
                gameNr.toString()
        };

        Cursor cursor = db.query(
                SQLConstants.Entries.TABLE_ROUNDS,
                columns,
                where,
                whereArgs,
                null,
                null,
                null,
                null
        );
        ArrayList<Integer> rounds = new ArrayList<>();
        while(cursor.moveToNext()) {
            rounds.add(cursor.getInt(cursor.getColumnIndexOrThrow(SQLConstants.Entries.ROUND_BET)));
        }

        cursor.close();
        return rounds;
    }

    public ArrayList<Integer> getRoundScore(Integer gameNr) {
        setRead();

        String[] columns = {
                SQLConstants.Entries._ID,
                SQLConstants.Entries.ROUND_SCORE,
        };

        String where = "fkGame = ?";

        String[] whereArgs = {
                gameNr.toString()
        };

        Cursor cursor = db.query(
                SQLConstants.Entries.TABLE_ROUNDS,
                columns,
                where,
                whereArgs,
                null,
                null,
                null,
                null
        );
        ArrayList<Integer> rounds = new ArrayList<>();
        while(cursor.moveToNext()) {
            rounds.add(cursor.getInt(cursor.getColumnIndexOrThrow(SQLConstants.Entries.ROUND_SCORE)));
        }

        cursor.close();
        return rounds;
    }

    public ArrayList<UserData> getAllHighScores() {
        setRead();

        String[] columns = {
          SQLConstants.Entries._ID,
          SQLConstants.Entries.HIGHSCORES_USER,
          SQLConstants.Entries.HIGHSCORE_SCORE,
          SQLConstants.Entries.HIGHSCORE_DATETIME
        };

        String order = SQLConstants.Entries.HIGHSCORE_SCORE + " DESC";
        String limit = "10";

        Cursor cursor = db.query(
                SQLConstants.Entries.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                order,
                limit
        );

        ArrayList<UserData> items = new ArrayList<UserData>();
        while(cursor.moveToNext()) {
            UserData user = new UserData();
            user.setName(cursor.getString(cursor.getColumnIndexOrThrow(SQLConstants.Entries.HIGHSCORES_USER)));
            user.setScore(cursor.getInt(cursor.getColumnIndexOrThrow(SQLConstants.Entries.HIGHSCORE_SCORE)));
            user.setDate(cursor.getString(cursor.getColumnIndexOrThrow(SQLConstants.Entries.HIGHSCORE_DATETIME)));

            items.add(user);
        }

        cursor.close();

        return items;
    }
}
