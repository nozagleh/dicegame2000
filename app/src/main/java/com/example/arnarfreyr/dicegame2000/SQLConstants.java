package com.example.arnarfreyr.dicegame2000;

import android.provider.BaseColumns;

/**
 * Created by arnarfreyr on 27.6.2017.
 */

public final class SQLConstants {

    private SQLConstants() {}

    public static class Entries implements BaseColumns {
        public static final String TABLE_NAME = "highscores";
        public static final String HIGHSCORES_USER = "user";
        public static final String HIGHSCORE_SCORE = "score";
        public static final String HIGHSCORE_DATETIME = "datetime";

        public static final String TABLE_GAMES = "games";
        public static final String GAME_FINISHED = "finished";

        public static final String TABLE_ROUNDS = "rounds";
        public static final String ROUND_SCORE = "roundScore";
        public static final String ROUND_BET = "roundBet";
        public static final String ROUND_FK_GAME = "fkGame";
    }
}
