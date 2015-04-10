package com.pigmalionstudios.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by pigmalionstudios on 4/9/15.
 */
public class TareasDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    public boolean esPendiente;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_TAREA, MySQLiteHelper.COLUMN_DIA_ALARMA, MySQLiteHelper.COLUMN_MES_ALARMA, MySQLiteHelper.COLUMN_ANIO_ALARMA,
            MySQLiteHelper.COLUMN_DIA_LIMITE, MySQLiteHelper.COLUMN_MES_LIMITE, MySQLiteHelper.COLUMN_ANIO_LIMITE};

    public TareasDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Tarea createTarea(String nombre, Fecha fechaAlarma, Fecha fechaLimite, boolean estaHecha) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TAREA, nombre);
        values.put(MySQLiteHelper.COLUMN_HECHA, (estaHecha)?"0":"1");
        if(fechaAlarma!=null){
            values.put(MySQLiteHelper.COLUMN_DIA_ALARMA, Integer.toString(fechaAlarma.getDia()));
            values.put(MySQLiteHelper.COLUMN_MES_ALARMA, Integer.toString(fechaAlarma.getMes()));
            values.put(MySQLiteHelper.COLUMN_ANIO_ALARMA, Integer.toString(fechaAlarma.getAño()));
            fechaAlarma.mostrarFecha();
        }
        if(fechaLimite!=null){
            values.put(MySQLiteHelper.COLUMN_DIA_LIMITE, Integer.toString(fechaLimite.getDia()));
            values.put(MySQLiteHelper.COLUMN_MES_LIMITE, Integer.toString(fechaLimite.getMes()));
            values.put(MySQLiteHelper.COLUMN_ANIO_LIMITE, Integer.toString(fechaLimite.getAño()));
            Log.i("Valor fecha limite ", Integer.toString(fechaLimite.getDia()));
            fechaLimite.mostrarFecha();
        }
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
        Tarea tarea = new Tarea("", new Fecha(), new Fecha(), false);
        tarea.setId(cursor.getLong(0));
        tarea.setNombre(cursor.getString(1));

        tarea.setAlarma(cursor.getInt(2), cursor.getInt(3), cursor.getInt(4));
        tarea.setLimite(cursor.getInt(5), cursor.getInt(6), cursor.getInt(7));
        Log.i("cursorToTarea", "los datos de la fecha alarma son: " +Integer.toString(tarea.getFechaAlarm().getMes()));
        Log.i("cursorToTarea", "los datos de la fecha limite son: " +Integer.toString(tarea.getFechaVenc().getMes()));
        return tarea;
    }
}

