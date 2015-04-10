package com.pigmalionstudios.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
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
    public DatePicker datePickerVencimiento;
    public DatePicker datePickerAlarma;

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
        datePickerVencimiento = (DatePicker) findViewById(R.id.datePickerVencimiento);
        datePickerAlarma = (DatePicker) findViewById(R.id.datePickerRecordatorio);
        TextView tv2 = (TextView) findViewById(R.id.textView2);
//        datePickerVencimiento.setMinDate(new Date().getTime()-1);
//        editTextNombre.setSelection(editTextNombre.getText().length());
        ;

        if (intent != null) {
            razonRecibida = intent.getIntExtra("razon", -1);
            stringInicialNombre = nombre = intent.getStringExtra("nombre");
            if (razonRecibida == MASTERCARD) {

                setTitle("Tarea: " + nombre);

                Fecha fechaVenc = intent.getParcelableExtra("fechaLimite");
                Fecha fechaAlarm = intent.getParcelableExtra("fechaAlarma");
                datePickerAlarma.init(fechaAlarm.getAño(), fechaAlarm.getMes(), fechaAlarm.getDia(), null);
                datePickerVencimiento.init(fechaVenc.getAño(), fechaVenc.getMes(), fechaVenc.getDia(), null);
/*
                if (parcel != null){
                    Fecha[] result = Arrays.copyOf(parcel, parcel.length, Fecha[].class);
                    tv2.setText(Integer.toString(result[0].getDia()));
                       // datePickerAlarma.init(result[0].getAño(), result[0].getMes(), result[0].getDia(), null);

                }*/
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
                Tarea tarea = new Tarea(nombre, null, null, false);
                intent.putExtra("razon", MainActivity.BORRAR);
                intent.putExtra("tarea", tarea);
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
                            intent.putExtra("fechaLimite", new Fecha(datePickerVencimiento.getDayOfMonth(), datePickerVencimiento.getMonth(), datePickerVencimiento.getYear()));
                            intent.putExtra("fechaAlarma", new Fecha(datePickerAlarma.getDayOfMonth(), datePickerAlarma.getMonth(), datePickerAlarma.getYear()));

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
