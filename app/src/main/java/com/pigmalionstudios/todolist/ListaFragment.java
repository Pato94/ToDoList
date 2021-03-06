package com.pigmalionstudios.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by pigmalionstudios on 4/6/15.
 */


public class ListaFragment extends Fragment {

    public static enum Tipo {PENDIENTES, HECHAS};
    public Tipo tipo;
    TareaAdapter adapter;
    public ArrayList listaElementos = new ArrayList<Tarea>();
    public TareasDataSource datasource;
    //public ListView lista;
    @InjectView(R.id.listView) ListView lst;
    public void setCosas(Tipo tip){
        this.tipo = tip;
    }

    public List<Tarea> obtenerDatos(){
        List<Tarea> tareas = new ArrayList<Tarea>();
        return tareas;
    }
    public boolean agregarALista(Tarea tarea, boolean esAgregar){

        if(encontrarPorNombre(tarea.getNombre())!=null && tipo == Tipo.PENDIENTES && esAgregar) {

            Toast.makeText(getView().getContext(), "Ya existe una tarea con ese nombre en la lista", Toast.LENGTH_SHORT).show();
            return false;

        }
        else {
            if(esAgregar) {
                //adapter.add(tarea);
                tarea.getFechaAlarm().mostrarFecha();
                tarea.getFechaVenc().mostrarFecha();
                adapter.add(datasource.createTarea(tarea.getNombre(), tarea.getFechaAlarm(), tarea.getFechaVenc(), tipo == Tipo.HECHAS));

            }
            else
                return true; //En realidad deberia modificar la tarea TODO
            return true;
        }
    }
    public Tarea removerDeLista(Tarea tarea){
        /*
        adapter.remove(tarea);
        return tarea;
        */
        datasource.deleteComment(tarea);
        adapter.remove(tarea);
        return tarea;
    }
    public Tarea encontrarPorNombre(String nombre){
        for(Tarea tarea : (ArrayList<Tarea>)adapter.datos){
            if(tarea.getNombre().equals(nombre)) return tarea;
        }
        return null;
    }
    public Tarea borrarPorNombre(String nombre){/*
        Tarea aux = encontrarPorNombre(nombre);
        if (aux !=null)
            return removerDeLista(encontrarPorNombre(nombre));
        else{

            Toast.makeText(getView().getContext(), "Me llamaron"
                    + nombre, Toast.LENGTH_SHORT).show();
        }
        return null;*/
        Tarea tarea = encontrarPorNombre(nombre);
        datasource.deleteComment(tarea);
        adapter.remove(tarea);
        return tarea;
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.lista_fragment, container, false);
        ButterKnife.inject(this, view);
        /*
        if(adapter==null) {
            adapter = new TareaAdapter(view.getContext(), obtenerDatosHardcodeados());
        }*/
        datasource = ((MainActivity)getActivity()).datasource;
        adapter = new TareaAdapter(view.getContext(), datasource.getAllTareas(tipo == Tipo.HECHAS));

        MainActivity principal = (MainActivity)getActivity();
        if(tipo==Tipo.HECHAS) {
            for(Tarea tarea: (ArrayList<Tarea>)principal.tareasPendientes){
                datasource.createTarea(tarea.getNombre(), tarea.getFechaAlarm(), tarea.getFechaVenc(), true);
            }
            adapter.addAll(principal.tareasPendientes);
            principal.tareasPendientes.clear();
        }
//        lst.setItemsCanFocus(true);
        lst.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lst.setAdapter(adapter);
        if(this.tipo==Tipo.PENDIENTES) {
            lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {
                    Intent intent = new Intent(getActivity(), DetalleActivity.class);
                intent.putExtra("nombre", ((TextView) v.findViewById(R.id.txtTarea)).getText().toString());
                    intent.putExtra("razon", DetalleActivity.MASTERCARD);
                    intent.putExtra("fechaAlarma", ((Tarea)adapter.getItem(posicion)).getFechaAlarm());
                    intent.putExtra("fechaLimite", ((Tarea)adapter.getItem(posicion)).getFechaVenc());
                    getActivity().startActivityForResult(intent, MainActivity.BORRAR);
                }
            });
        }
        return view;
    }
}
