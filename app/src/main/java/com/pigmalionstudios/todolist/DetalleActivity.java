package com.pigmalionstudios.todolist;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class DetalleActivity extends ActionBarActivity {
    public static final int CREAR = 71;
    public static final int MASTERCARD = 3;
    public int razonRecibida;
    public String nombre;
    public Menu menu;
    public EditText editTextNombre;
    public String stringInicialNombre;
    public boolean algoCambio(){
        //La idea es comparar el estado actual de los views con su estado inicial
        boolean resultado = true;
        resultado = resultado && (editTextNombre.getText().toString().equals(stringInicialNombre));
        return !resultado;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);
        Intent intent = getIntent();
        editTextNombre = (EditText) findViewById(R.id.editText);

//        editTextNombre.setSelection(editTextNombre.getText().length());
        if (intent != null) {
            razonRecibida = intent.getIntExtra("razon", -1);
            stringInicialNombre = nombre = intent.getStringExtra("nombre");
            if (razonRecibida == MASTERCARD) {

                setTitle("Tarea: " + nombre);
                //En realidad aqui además obtendrias los otros datos. TODO
                TextView tv1 = (TextView) findViewById(R.id.textView);
                editTextNombre.setText(nombre);
            } else {
                setTitle("Nueva tarea");
                editTextNombre.setText(nombre);
                //Aqui no deberia permitir mostrar Los items de la action bar TODO
            }
        }
        //Aca deberia guardar todos los estados iniciales:
        editTextNombre.requestFocus();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
        if(this.razonRecibida==CREAR) {
            menu.findItem(R.id.action_delete).setVisible(false);
            menu.findItem(R.id.action_complete).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        int id = item.getItemId();
        if(algoCambio() && id!=R.id.action_edit && razonRecibida == MASTERCARD){
          Toast.makeText(getApplicationContext(), "Debe guardar los cambios antes de llevar a cabo esta accion", Toast.LENGTH_SHORT).show();
        } else {
        switch (id) {
            case R.id.action_delete: {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("razon", MainActivity.BORRAR);
                intent.putExtra("nombre", nombre);
                setResult(RESULT_OK, intent);
                finish();
            }
            case R.id.action_edit: {
                if (isEmpty(editTextNombre)) {
                    Toast.makeText(getApplicationContext(), "Debe ingresar un nombre para la tarea", Toast.LENGTH_SHORT).show();
                    //editTextNombre.setHighlightColor(Color.RED);
                    //Si podes darle rojo al nombre joya.
                } else {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("razon", MainActivity.AGREGAR);
                    intent.putExtra("esAgregar", (razonRecibida == CREAR) ? "si" : "no");//Paja aprender como pasar booleans TODO
                    intent.putExtra("nombre", editTextNombre.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
            }
            case R.id.action_complete:{

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("razon", MainActivity.COMPLETAR);
                intent.putExtra("nombre", editTextNombre.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        }}
        return super.onOptionsItemSelected(item);
    }
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}
