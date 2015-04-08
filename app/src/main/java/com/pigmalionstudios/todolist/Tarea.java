package com.pigmalionstudios.todolist;

import java.util.Date;

/**
 * Created by pigmalionstudios on 4/6/15.
 */
public class Tarea {

    String nombre;
    Date fechaAlarma;
    Date fechaVencimiento;
    boolean hecha;

    public boolean isDone(){
        return hecha;
    }
    public Tarea hacete(){
        this.hecha = true;
        return this;
    }

    public Tarea(String nom, Date fechaAl, Date fechaVen, boolean estaHecha){
        this.nombre = nom;
        this.fechaAlarma = fechaAl;
        this.fechaVencimiento = fechaVen;
        this.hecha = estaHecha;
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
