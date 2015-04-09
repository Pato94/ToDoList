package com.pigmalionstudios.todolist;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by pigmalionstudios on 4/6/15.
 */
public class Tarea implements Parcelable{

    String nombre;
    Date fechaAlarma;
    Date fechaVencimiento;
    boolean hecha;
    long id;

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setId(long id){
        this.id = id;
    }
    public long getId(){
        return id;
    }
    public boolean isDone(){
        return hecha;
    }
    public Tarea hacete(){
        this.hecha = true;
        return this;
    }

    public int describeContents(){
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(nombre);/*
        dest.writeLongArray(new long[]{fechaAlarma.getTime(), fechaVencimiento.getTime(), id});*/
    }
    public Tarea(Parcel parcel){
        readFromParcel(parcel);
    }
    public static final Parcelable.Creator<Tarea> CREATOR
            = new Parcelable.Creator<Tarea>() {
        public Tarea createFromParcel(Parcel in) {
            return new Tarea(in);
        }

        public Tarea[] newArray(int size) {
            return new Tarea[size];
        }
    };
    public void readFromParcel(Parcel parcel){
        nombre = parcel.readString();/*
        long[] temp = new long[3];
        parcel.readLongArray(temp);
        fechaAlarma = new Date(temp[0]);
        fechaVencimiento = new Date(temp[1]);
        id = temp[2];*/
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
