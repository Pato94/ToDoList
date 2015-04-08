package com.pigmalionstudios.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by pigmalionstudios on 4/6/15.
 */


public class ListaFragment extends Fragment {

    public static enum Tipo {PENDIENTES, HECHAS};
    public Tipo tipo;
    //public ListView lista;
    @InjectView(R.id.listView) ListView lst;
    public void setCosas(Tipo tip){
        this.tipo = tip;
    }

    public List<Tarea> obtenerDatos(){
        List<Tarea> tareas = new ArrayList<Tarea>();
        return tareas;
    }
    //Ya veremos mas adelante (?
    public ArrayList obtenerDatosHardcodeados(){
        Date time = new Date();

        ArrayList tareas = new ArrayList<Tarea>();
        if (this.tipo == Tipo.PENDIENTES){
            tareas.add(new Tarea("Tarea 1", null, null));
            tareas.add(new Tarea("Tarea 2", time, null));
            tareas.add(new Tarea("Tarea 3", time, null));
            tareas.add(new Tarea("Tarea 4", null, null));
            tareas.add(new Tarea("Tarea 5", time, null));
        } else {
            tareas.add(new Tarea("Tarea 6", null, null));
            tareas.add(new Tarea("Tarea 7", null, null));
            tareas.add(new Tarea("Tarea 8", null, null));
            tareas.add(new Tarea("Tarea 9", null, null));
            tareas.add(new Tarea("Tarea 10", null, null));
        }
        return tareas;
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.lista_fragment, container, false);
        ButterKnife.inject(this, view);
        TareaAdapter adapter = new TareaAdapter(view.getContext(), obtenerDatosHardcodeados());
//        lst.setItemsCanFocus(true);
        lst.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lst.setAdapter(adapter);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {
                //((TextView)v.findViewById(R.id.txtTarea)).setText("Bue");
                Intent intent = new Intent(getActivity(), MainActivity2.class);
//                intent.putExtra("nombre", ((TextView) v.findViewById(R.id.txtTarea)).getText().toString());
//                intent.putExtra("fechaAlarm", ((Tarea)lst.getItemAtPosition(posicion)).getFechaAlarm());
//                intent.putExtra("fechaVenc", ((Tarea)lst.getItemAtPosition(posicion)).getFechaVenc());
                getActivity().startActivity(intent);
            }
        });
        return view;
    }
}
