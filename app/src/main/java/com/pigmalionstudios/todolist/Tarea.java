package com.pigmalionstudios.todolist;

import java.util.Date;

/**
 * Created by pigmalionstudios on 4/6/15.
 */
public class Tarea {

    String nombre;
    Date fechaAlarma;
    Date fechaVencimiento;

    public Tarea(String nom, Date fechaAl, Date fechaVen){
        this.nombre = nom;
        this.fechaAlarma = fechaAl;
        this.fechaVencimiento = fechaVen;
    }

    public boolean hasAlarm(){
        return (fechaAlarma!=null);
    }
    public Date getFechaAlarm(){
        return fechaAlarma;
    }
    public Date getFechaVenc(){
        return fechaVencimiento;
    }
    public String getNombre(){return nombre;}
    public String toString(){
        return nombre;
    }
}
