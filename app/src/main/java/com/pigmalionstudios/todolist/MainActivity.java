package com.pigmalionstudios.todolist;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    public static final int BORRAR = 5;
    public static final int AGREGAR = 3;
    public static final int COMPLETAR = 6;
    public TareasDataSource datasource;
    public ArrayList tareasPendientes;
    public ListaFragment fragmentoVivo;
    GoogleApiClient googleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datasource= new TareasDataSource(this);
        datasource.open();

        tareasPendientes = new ArrayList<Tarea>();
        ActionBar actionBar = getSupportActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//        actionBar.setDisplayShowTitleEnabled(false);

        /**CREAR TABS**/

        ActionBar.Tab tabPendientes = actionBar.newTab()
                 .setText(R.string.tag_pendientes)
                 .setTabListener(new TabsListener<ListaFragment>(
                        this, this.getString(R.string.tag_pendientes), ListaFragment.class));



        ActionBar.Tab tabHechas = actionBar.newTab()
                 .setText(R.string.tag_hechas)
                 .setTabListener(new TabsListener<ListaFragment>(
                this, this.getString(R.string.tag_hechas), ListaFragment.class));
        actionBar.addTab(tabPendientes);
        actionBar.addTab(tabHechas);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id==R.id.action_add){

            if (getSupportActionBar().getSelectedTab().getText().toString().equals(getString(R.string.tag_pendientes)))
                    crearNuevaTarea("");
        }
        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
    private void crearNuevaTarea(String nombre){
        Intent intent = new Intent(this, DetalleActivity.class);
        intent.putExtra("nombre", nombre);
        intent.putExtra("razon", DetalleActivity.CREAR);
        startActivityForResult(intent, 3);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {//Estaria bueno que le de uso al request code
        if (RESULT_OK == resultCode)
        {
            ListaFragment fragmentPendientes = (ListaFragment)(getSupportFragmentManager().findFragmentByTag("Pendientes"));
            ListaFragment  fragmentHechas = (ListaFragment)(getSupportFragmentManager().findFragmentByTag("Hechas"));

            int razon = data.getIntExtra("razon", -1);
            if (razon==BORRAR){

                //String nombre = data.getStringExtra("nombre");
                Tarea tarea = data.getParcelableExtra("tarea");
//                String tag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
                fragmentPendientes.borrarPorNombre(tarea.getNombre());
            }
            if (razon==AGREGAR){
                String nombre = data.getStringExtra("nombre");
                boolean esAgregar = data.getStringExtra("esAgregar").equals("si");
                Fecha fechaAlarma = data.getParcelableExtra("fechaAlarma");
                Fecha fechaLimite = data.getParcelableExtra("fechaLimite");
                fechaAlarma.mostrarFecha();
                fechaLimite.mostrarFecha();
                //Tirar null null es OBVIAMENTE una burrada TODO
                if(!fragmentPendientes.agregarALista(new Tarea(nombre, fechaAlarma, fechaLimite, false), esAgregar)){
                    crearNuevaTarea(nombre);//Para que llame a la instancia otra vez
                };
            }
            if (razon==COMPLETAR){
                String nombre = data.getStringExtra("nombre");
                //Fue un intento fallido por hacer que funcione
//                getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fragmentHechas, "Hechas33");
                //fragmentHechas.agregarALista(fragmentPendientes.borrarPorNombre(nombre), false);
                Tarea tarea = fragmentPendientes.borrarPorNombre(nombre);
//                fragmentHechas.agregarALista(tarea, true);
                tareasPendientes.add(tarea.hacete());
            }
        }
    }
}
