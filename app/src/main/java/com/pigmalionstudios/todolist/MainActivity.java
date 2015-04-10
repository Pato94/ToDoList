package com.pigmalionstudios.todolist;


import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.drive.Drive;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    public static final int BORRAR = 5;
    public static final int AGREGAR = 3;
    public static final int COMPLETAR = 6;
    public TareasDataSource datasource;
    public ArrayList tareasPendientes;
    GoogleApiClient mGoogleApiClient;

    private boolean mSignInClicked;

    /**
     * True if we are in the process of resolving a ConnectionResult
     */
    private boolean mIntentInProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datasource= new TareasDataSource(this);
        datasource.open();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Drive.API)
                .addScope(Drive.SCOPE_FILE)
                .build();
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
    /**
     * True if the sign-in button was clicked.  When true, we know to resolve all
     * issues preventing sign-in without waiting.
     */
    private boolean mSignInClicked;

    /**
     * True if we are in the process of resolving a ConnectionResult
     */
    private boolean mIntentInProgress;

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!mIntentInProgress) {
            if (mSignInClicked && result.hasResolution()) {
                // The user has already clicked 'sign-in' so we attempt to resolve all
                // errors until the user is signed in, or they cancel.
                try {
                    result.startResolutionForResult(this, RC_SIGN_IN);
                    mIntentInProgress = true;
                } catch (IntentSender.SendIntentException e) {
                    // The intent was canceled before it was sent.  Return to the default
                    // state and attempt to connect to get an updated ConnectionResult.
                    mIntentInProgress = false;
                    mGoogleApiClient.connect();
                }
            }
        }
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
        if (id==R.id.action_login){
            mSignInClicked = true;
            mGoogleApiClient.connect();
        }
        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
    private void crearNuevaTarea(String nombre){
        Intent intent = new Intent(this, DetalleActivity.class);
        intent.putExtra("nombre", nombre);
        intent.putExtra("razon", DetalleActivity.CREAR);
        startActivityForResult(intent, 73);
    }

    public void onConnected(Bundle connectionHint) {
        mSignInClicked = false;
        Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//Estaria bueno que le de uso al request code
        if (requestCode != 73) {
            if (resultCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnected()) {
                mGoogleApiClient.reconnect();
            }
        } else {
            if (RESULT_OK == resultCode) {
                ListaFragment fragmentPendientes = (ListaFragment) (getSupportFragmentManager().findFragmentByTag("Pendientes"));
                ListaFragment fragmentHechas = (ListaFragment) (getSupportFragmentManager().findFragmentByTag("Hechas"));

                int razon = data.getIntExtra("razon", -1);
                if (razon == BORRAR) {

                    //String nombre = data.getStringExtra("nombre");
                    Tarea tarea = data.getParcelableExtra("tarea");
//                String tag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
                    fragmentPendientes.borrarPorNombre(tarea.getNombre());
                }
                if (razon == AGREGAR) {
                    String nombre = data.getStringExtra("nombre");
                    boolean esAgregar = data.getStringExtra("esAgregar").equals("si");
                    Fecha fechaAlarma = data.getParcelableExtra("fechaAlarma");
                    Fecha fechaLimite = data.getParcelableExtra("fechaLimite");
                    fechaAlarma.mostrarFecha();
                    fechaLimite.mostrarFecha();
                    //Tirar null null es OBVIAMENTE una burrada TODO
                    if (!fragmentPendientes.agregarALista(new Tarea(nombre, fechaAlarma, fechaLimite, false), esAgregar)) {
                        crearNuevaTarea(nombre);//Para que llame a la instancia otra vez
                    }
                    ;
                }
                if (razon == COMPLETAR) {
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
}
