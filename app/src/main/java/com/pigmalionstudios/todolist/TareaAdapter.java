package com.pigmalionstudios.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;

import java.util.ArrayList;
import java.util.List;


 /**
 * Created by pigmalionstudios on 4/7/15.
 */
public class TareaAdapter extends ArrayAdapter<Tarea> {
//     List<Tarea> tareas;
     private Context context;
     private ArrayList datos;
     public TareaAdapter(Context context, ArrayList datos) {
         super(context, R.layout.listitem_tarea, datos);
         // Guardamos los parámetros en variables de clase.
         this.context = context;
         this.datos = datos;
     }
     @Override
     public int getCount() {
         return datos.size();
     }

     @Override
     public Tarea getItem(int position) {
         return (Tarea)datos.get(position);
     }

     @Override
     public long getItemId(int position) {
         return position;
     }

     @Override
     public View getView(int position, View convertView, ViewGroup parent) {

         LayoutInflater inflater = LayoutInflater.from(context);
         View item = inflater.inflate(R.layout.listitem_tarea, null);

         ImageView imagen = (ImageView) item.findViewById(R.id.imgAlarm);
         if (!datos.isEmpty()) {
             if (!((Tarea) datos.get(position)).hasAlarm()) {
                 imagen.setVisibility(View.GONE);
             }
             TextView txtNombre = (TextView) item.findViewById(R.id.txtTarea);
             txtNombre.setText(((Tarea) datos.get(position)).getNombre());

             // Devolvemos la vista para que se muestre en el ListView.

         }
         return item;
     }
}
