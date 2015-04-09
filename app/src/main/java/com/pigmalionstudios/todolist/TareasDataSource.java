package com.pigmalionstudios.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pigmalionstudios on 4/9/15.
 */
public class TareasDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    public boolean esPendiente;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_TAREA};

    public TareasDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Tarea createTarea(String nombre, boolean estaHecha) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TAREA, nombre);
        values.put(MySQLiteHelper.COLUMN_HECHA, (estaHecha)?"0":"1");
        long insertId = database.insert(MySQLiteHelper.TABLE_TAREAS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_TAREAS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Tarea newTarea = cursorToTarea(cursor);
        cursor.close();
        return newTarea;
    }

    public void deleteComment(Tarea tarea) {
        long id = tarea.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_TAREAS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public ArrayList getAllTareas(boolean estaHecha) {
        ArrayList tareas = new ArrayList<Tarea>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_TAREAS,
                allColumns, MySQLiteHelper.COLUMN_HECHA + " = " + ((estaHecha)?"0":"1"), null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Tarea tarea = cursorToTarea(cursor);
            tareas.add(tarea);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return tareas;
    }

    private Tarea cursorToTarea(Cursor cursor) {
        Tarea tarea = new Tarea("", null, null, false);
        tarea.setId(cursor.getLong(0));
        tarea.setNombre(cursor.getString(1));
        return tarea;
    }
}

