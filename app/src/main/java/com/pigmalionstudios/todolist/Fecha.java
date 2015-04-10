package com.pigmalionstudios.todolist;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by pigmalionstudios on 4/10/15.
 */
public class Fecha implements Parcelable {
    private int dia;
    private int mes;
    private int año;
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Fecha createFromParcel(Parcel parcel) {
            return new Fecha(parcel);
        }
        public Fecha[] newArray(int size) {
            return new Fecha[size];
        }
    };
    public void mostrarFecha(){
        Log.i("FECHA", "La fecha es:" + Integer.toString(this.getDia())+ "/"+Integer.toString(this.getMes())+ "/"+Integer.toString(this.getAño()) );
    }
    public int getDia() {return dia;}

    public void setDia(int dia) {this.dia = dia;}

    public int getMes() {return mes;}

    public void setMes(int mes) {this.mes = mes;}

    public int getAño() {return año;}

    public void setAño(int año) {this.año = año;}

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        int[] aux = {getDia(), getMes(), getAño()};
        dest.writeIntArray(aux);
    }

    public void readFromParcel(Parcel in) {
        int[] aux = new int[3];
        in.readIntArray(aux);
        this.setDia(aux[0]);
        this.setMes(aux[1]);
        this.setAño(aux[2]);
    }

    public Fecha() {
    }
    public Fecha(int dia, int mes, int año){
        this.dia = dia;
        this.mes = mes;
        this.año = año;
    }
    public Fecha(Parcel parcel) {
        readFromParcel(parcel);
    }
}