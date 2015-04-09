package com.pigmalionstudios.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pigmalionstudios on 4/9/15.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

        public static final String TABLE_TAREAS = "tareas";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_TAREA = "tarea";
        public static final String COLUMN_HECHA = "hecha";

        private static final String DATABASE_NAME = "tareas.db";
        private static final int DATABASE_VERSION = 2;

        // Database creation sql statement
        private static final String DATABASE_CREATE = "create table "
                + TABLE_TAREAS + "(" + COLUMN_ID
                + " integer primary key autoincrement, " + COLUMN_TAREA
                + " text not null, "+ COLUMN_HECHA +" integer);";

        public MySQLiteHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAREAS);
            onCreate(db);
        }

    }
